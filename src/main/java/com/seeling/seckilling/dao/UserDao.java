package com.seeling.seckilling.dao;

import com.seeling.seckilling.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Match Fu
 * @date 2020/5/17 4:54 下午
 */
@Mapper
public interface UserDao {
    /**
     * 通过ID获取用户信息
     * @param id 用户id
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(@Param("id") Long id);

    /**
     * 修改密码
     * @param user 用户对象
     */
    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void update(User user);
}
