package com.seeling.seckilling.controller;

import com.seeling.seckilling.domain.User;
import com.seeling.seckilling.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Match Fu
 * @date 2020/7/2 7:58 下午
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(User user) {
        return Result.success(user);
    }
}
