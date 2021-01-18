package com.seeling.seckilling.service;

import com.seeling.seckilling.dao.UserDao;
import com.seeling.seckilling.domain.User;
import com.seeling.seckilling.exception.GlobalException;
import com.seeling.seckilling.redis.RedisService;
import com.seeling.seckilling.redis.UserKey;
import com.seeling.seckilling.result.CodeMsg;
import com.seeling.seckilling.util.MD5Util;
import com.seeling.seckilling.util.UUIDUtil;
import com.seeling.seckilling.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Match Fu
 * @date 2020/5/17 4:58 下午
 */
@Service
public class UserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    public User getById(Long id) {
        // 取缓存
        User user = redisService.get(UserKey.getById,""+id,User.class);
        if (user != null) {
            return user;
        }
        // 取数据库
        user= userDao.getById(id);
        // 更新缓存
        if (user != null) {
            redisService.set(UserKey.getById,""+id,user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        // 取User
        User user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIT);
        }
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        userDao.update(toBeUpdate);
        // 处理缓存
        redisService.delete(UserKey.getById,""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token,token,user);
        return true;
    }

    public User getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token,token,User.class);
        if (user != null) {
            addCookie(response,token,user);
        }
        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIT);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String slatDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, slatDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
