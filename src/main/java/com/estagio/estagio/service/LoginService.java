package com.estagio.estagio.service;

import com.estagio.estagio.entity.Client;
import com.estagio.estagio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private ClientRepository _clientRepository;

    public ResponseEntity<Client> login(Client client) {
        Optional<Client> clientOptional = _clientRepository.findByEmail(client.getEmail());
        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!client.getPassword().equals(clientOptional.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(clientOptional.get(), HttpStatus.OK);
    }

}
