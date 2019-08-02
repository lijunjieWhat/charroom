package com.etech.demo;

import com.etech.demo.chat.WebSocketChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class CharroomApplication {


    @GetMapping({"/","login.html"})
    public ModelAndView login(){
        return  new ModelAndView("index");
    }

    @GetMapping("/chat/{username}")
    public ModelAndView chat(@PathVariable String username, HttpServletRequest request) throws UnknownHostException {
        if(StringUtils.isEmpty(username)){
            username = "匿名用户";
        }
            ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("username", username);
        modelAndView.addObject("webSocketUrl", "ws://"+ InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/chat");
        return modelAndView;
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CharroomApplication.class, args);
    }

}
