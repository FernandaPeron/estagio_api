package com.estagio.estagio.controller;

import com.estagio.estagio.entity.Client;
import com.estagio.estagio.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> GetById(@PathVariable(value = "id") String id) {
        return clientService.getById(UUID.fromString(id));
    }

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ResponseEntity<Client> GetByEmail(@RequestBody String email) {
        return clientService.getByEmail(email);
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public ResponseEntity<Client> postClient(@RequestBody Client client) {
        return clientService.postClient(client);
    }

    @RequestMapping(value = "/client/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<Client> putClient(
            @PathVariable(value = "id") String id,
            @RequestBody Client newClient
    ) {
        return clientService.putClient(UUID.fromString(id), newClient);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteClient(@PathVariable(value = "id") String id) {
        return clientService.deleteClient(UUID.fromString(id));
    }
};

