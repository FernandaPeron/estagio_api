package com.estagio.estagio.controller;

import com.estagio.estagio.entity.Archive;
import com.estagio.estagio.entity.Client;
import com.estagio.estagio.repository.ArchiveRepository;
import com.estagio.estagio.repository.ClientRepository;
import com.estagio.estagio.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "files/upload", method = RequestMethod.POST)
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String clientId
    ) {
        return fileService.upload(file, UUID.fromString(clientId));
    }

    @RequestMapping(value = "files/download/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(
            @PathVariable String fileName,
            @RequestParam String clientId
    ) {
        return fileService.download(fileName, UUID.fromString(clientId));
    }

    @RequestMapping(value = "/files/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Archive>> getFilesFromUser(@PathVariable(value = "userId") String id) {
        return fileService.getFilesFromUser(UUID.fromString(id));
    }

}
