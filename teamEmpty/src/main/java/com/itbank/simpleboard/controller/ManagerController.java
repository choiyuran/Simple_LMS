package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;


}
