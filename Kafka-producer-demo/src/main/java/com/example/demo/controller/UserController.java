package com.example.demo.controller;

import com.example.demo.entity.Response;
import com.example.demo.service.UserLogService;
import com.example.demo.service.UserService;
import model.User;
import model.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/*@RestController
@RequestMapping("kafka")
public class UserController {

    @Autowired<!--  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
-->
    KafkaTemplate<String, User> kafkaTemplate;

    private static final String TOPIC = "testTopic";

    @GetMapping("/publish/{firstName}")
    public String post(@PathVariable("firstName")String firstName){

        kafkaTemplate.send(TOPIC,new User(firstName, "Das", 30));

        return "Published successfully";
    }
}*/


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserLogService userLogService;

    @PostMapping(value = "/add-user")
    public ResponseEntity<Response> addUser(@RequestBody User user){
        Response response = new Response(userService.addUser(user), LocalDate.now());
        return new ResponseEntity<Response>(response,HttpStatus.CREATED);
    }

    @PostMapping(value = "/user-activity")
    public ResponseEntity<Response> userActivity(@RequestBody UserActivity userActivity){
        Response response = new Response(userLogService.produceMessage(userActivity), LocalDate.now());
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}