package com.estagio.estagio.service;

import com.estagio.estagio.entity.Client;
import com.estagio.estagio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Client> getById(UUID id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Client> getByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Client> postClient(Client client) {
        Optional<Client> clientOptional = clientRepository.findByEmail(client.getEmail());
        if(clientOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Client savedClient = clientRepository.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.OK);
    }

    public ResponseEntity<Client> putClient(UUID id, Client newClient) {
        Optional<Client> oldClient = clientRepository.findByUserId(id);
        if(oldClient.isPresent()){
            Client client = oldClient.get();
            client.setUserName(newClient.getUserName());
            clientRepository.save(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> deleteClient(UUID id) {
        Optional<Client> client = clientRepository.findByUserId(id);
        if(client.isPresent()){
            clientRepository.delete(client.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
