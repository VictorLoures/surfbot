package br.com.surfbot.surfbotbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/example")
public class EmailController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok().body("Hello World!");
    }
}
