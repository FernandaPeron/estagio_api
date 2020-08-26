package com.estagio.estagio.controller;

import com.estagio.estagio.entity.Client;
import com.estagio.estagio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private ClientRepository _clientRepository;

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> GetById(@PathVariable(value = "id") UUID id)
    {
        Optional<Client> clientOptional = _clientRepository.findById(id);
        return clientOptional.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ResponseEntity<Client> GetByEmail(@RequestBody String email)
    {
        Optional<Client> clientOptional = _clientRepository.findByEmail(email);
        return clientOptional.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<Client> Post(@RequestBody Client client)
    {
        Optional<Client> clientOptional = _clientRepository.findByEmail(client.getEmail());
        if(clientOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Client savedClient = _clientRepository.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Client> Put(@PathVariable(value = "id") UUID id, @RequestBody Client newClient)
    {
        Optional<Client> oldClient = _clientRepository.findByUserId(id);
        if(oldClient.isPresent()){
            Client client = oldClient.get();
            client.setUserName(newClient.getUserName());
            _clientRepository.save(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Client> client = _clientRepository.findByUserId(id);
        if(client.isPresent()){
            _clientRepository.delete(client.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
};

