package com.estagio.estagio.service;

import com.estagio.estagio.entity.Archive;
import com.estagio.estagio.entity.Client;
import com.estagio.estagio.repository.ArchiveRepository;
import com.estagio.estagio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<String> upload(MultipartFile file, UUID clientId) {
        Optional<Client> client = clientRepository.findByUserId(clientId);
        if(!client.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Archive doc = new Archive();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Optional<Archive> archive = archiveRepository.findByArchiveName(fileName);
        if(archive.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        doc.setArchiveName(fileName);
        doc.setClient(client.get());
        try {
            doc.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        archiveRepository.save(doc);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();
        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> download(UUID archiveId) {
        Optional<Archive> document = archiveRepository.findByArchiveId(archiveId);
        if(!document.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String fileName = document.get().getArchiveName();
        return document.map(archive -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(archive.getFile())).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Archive>> getFilesFromUser(UUID id) {
        List<Archive> userFiles = archiveRepository.findAllByClientUserId(id);
        return new ResponseEntity<>(userFiles, HttpStatus.OK);
    }

    public ResponseEntity<String> delete(UUID archiveId) {
        archiveRepository.deleteArchiveByArchiveId(archiveId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
