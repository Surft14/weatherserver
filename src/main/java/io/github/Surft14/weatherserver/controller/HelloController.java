package io.github.Surft14.weatherserver.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Async("weatherExecutor")
@RestController
public class HelloController {
    @GetMapping("/")
    public CompletableFuture<String> hello() {
        return CompletableFuture.completedFuture("Hello from Weather Server. Author: Surft14!");
    }
}
