package com.dack.spring.ws.api.controller;

import com.dack.spring.ws.Infrastructure.entities.User;
import com.dack.spring.ws.Infrastructure.repo.Authenication;
import com.dack.spring.ws.Infrastructure.repo.FileHandle;
import com.dack.spring.ws.api.model.FileModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class FileController {
    String folder = "src/main/java/com/dack/spring/ws/infrastructure/files/";

    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public ResponseEntity<FileModel> upload(@RequestParam MultipartFile file, @RequestParam String username) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filename = dateTime.format(formatter).toString() + extension;

        Path path = Paths.get(folder + filename);
        Files.write(path, file.getBytes());
        String url = "http://localhost:8080/files/";
        FileModel fileModel = new FileModel(file.getOriginalFilename(), url + filename + "/" + file.getOriginalFilename(), file.getSize());
        return ResponseEntity.ok(fileModel);
    }

    @GetMapping(value = "/files/{fileId}/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable String fileId,@PathVariable String fileName) throws IOException {
        Path path = Paths.get(folder + fileId);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }
}
