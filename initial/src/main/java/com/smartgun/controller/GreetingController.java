package com.smartgun.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.smartgun.model.HelloMessage;
import com.smartgun.model.Greeting;
import com.smartgun.service.GreetingService;
import com.smartgun.shared.Data;

@Controller
public class GreetingController {
    // SEND DATA TO CLIENT
    private final GreetingService greetingService;

    GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // GET DATA FROM CLIENT
    @MessageMapping("/login")
    @SendTo("/topic/simulation")
    public void login() throws Exception {
        Data.isUser = true;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/simulation")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}