package io.github.Surft14.weatherserver.controller;


import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;


@RestController
@AllArgsConstructor
@RequestMapping("/img/v1/weatherimg")
public class ImgController {

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException{

        ClassPathResource imgFile = new ClassPathResource("img/" + filename);

        if (!imgFile.exists()) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imgFile);

    }
}
