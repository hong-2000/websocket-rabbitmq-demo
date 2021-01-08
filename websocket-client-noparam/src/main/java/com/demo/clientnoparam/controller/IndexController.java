package com.demo.clientnoparam.controller;

import com.demo.clientnoparam.service.WebSocketService;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description 发送消息controller层
 * @create 2020/11/17 15:04
 */
@RestController
@RequestMapping("/websocket")
public class IndexController {

    private final WebSocketService webSocketService;
    /**
     * 构造器注入 @Autowired
     * @param webSocketService
     */
    public IndexController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * 群发
     * @param message
     * @return
     */
    @GetMapping("/sendMessage")
    public String sendMessage(String message){
        webSocketService.groupSending(message);
        return message;
    }

    /**
     * 指定发送
     * @param message
     * @return
     */
    @GetMapping("/sendMessageByName")
    public String sendMessage(
            @RequestParam(name = "name")
            @Length(message = "length of name", min = 2,max = 20)
            @NonNull String name,
            @NonNull String message){
        webSocketService.appointSending(name, message);
        return message;
    }

    /**
     * 指定发送
     * @param message
     * @return
     */
    @GetMapping("/sendMessageByName/{name}")
    public String sendMessageByName(
            @PathVariable(name = "name")
            @Length(message = "length of name", min = 2,max = 20)
            @NonNull String name,
            @NonNull String message){
        webSocketService.appointSending(name, message);
        return message;
    }
}
