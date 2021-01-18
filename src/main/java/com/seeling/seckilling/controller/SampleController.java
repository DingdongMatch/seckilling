package com.seeling.seckilling.controller;

import com.seeling.seckilling.rabbitmq.MQSender;
import com.seeling.seckilling.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Match Fu
 * @date 2020/8/2 11:02 下午
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    MQSender sender;

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
        sender.send("Hello,World!");
        return Result.success("Hello,World!");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> mqTopic() {
        sender.sendTopic("Hello,World!");
        return Result.success("Hello,World!");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> mqFanout() {
        sender.sendFanout("Hello,World!");
        return Result.success("Hello,World!");
    }

    @RequestMapping("/mq/headers")
    @ResponseBody
    public Result<String> mqHeaders() {
        sender.sendHeaders("Hello,World!");
        return Result.success("Hello,World!");
    }
}
