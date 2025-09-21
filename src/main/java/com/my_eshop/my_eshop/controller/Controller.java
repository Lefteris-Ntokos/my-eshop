package com.my_eshop.my_eshop.controller;

import com.my_eshop.my_eshop.domain.Users;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @GetMapping ("/hi/{inputName}")
    public boolean hello (@PathVariable String inputName) {
        List<String> students = List.of("Pantelis", "Mateo");
        for (String name : students) {

            if (name.equals(inputName)) {
                return  true;
            }
        }
        return false;
    }

//    @GetMapping ("/hi")
//    public boolean hello (@RequestParam String inputName) {
//        List<String> students = List.of("Pantelis", "Mateo");
//        for (String name : students) {
//
//            if (name.equals(inputName)) {
//                return  true;
//            }
//        }
//        return false;
//    }

    @PostMapping("/create")
    public String createUser(@RequestBody Users users){

        return  "Created" + users.getClass();
    }

}
