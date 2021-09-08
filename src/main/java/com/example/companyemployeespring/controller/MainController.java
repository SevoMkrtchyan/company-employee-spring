package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/403")
    public String accessDeniedPage(@AuthenticationPrincipal CurrentUser currentUser) {
        log.info("Attempt to open page where access is denied, user id = {} and user email is {}"
                , currentUser.getEmployee().getId(), currentUser.getEmployee().getEmail());
        return "403";
    }

}