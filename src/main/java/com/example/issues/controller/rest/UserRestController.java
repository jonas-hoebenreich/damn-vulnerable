package com.example.issues.controller.rest;

import com.example.issues.model.User;
import com.example.issues.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/all")
    public List<User> allUsers(){

        return userService.findAll();

    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteUser(@RequestParam("id") String id){
        try{
            userService.delete(Long.parseLong(id));
            return "success";
        } catch (Exception e) {
            return "User " + id + " not found";
        }
    }


}
