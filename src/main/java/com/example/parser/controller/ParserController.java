package com.example.parser.controller;

import com.example.parser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {
    private final ParserService parserService;
    public ParserController(@Autowired ParserService parserService){
        this.parserService = parserService;
    }

    @GetMapping(path = "/start")
    public void start() throws Exception {
        parserService.parser();
    }

    //Implement FIND, DELETE, UPDATE
}
