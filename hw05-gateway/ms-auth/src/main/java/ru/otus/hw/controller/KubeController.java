package ru.otus.hw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("hw")
public class KubeController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping(name = "/", produces = "application/json")
    public String getVersion() {
        return String.format("Hello, nice to meet you. Image from dockerHub. applicationId=%s", this.applicationContext.getApplicationName());
    }

    @GetMapping("/listHeaders")
    public ResponseEntity<String> listAllHeaders(
            @RequestHeader Map<String, String> headers) {
        return new ResponseEntity<String>(
                String.format("Listed %d headers. Data in headers %s", headers.size(), headers), HttpStatus.OK);
    }

}
