package com.estagio.estagio.service;

import com.estagio.estagio.entity.Client;
import com.estagio.estagio.entity.Event;
import com.estagio.estagio.repository.ClientRepository;
import com.estagio.estagio.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<List<Event>> getByClientId(UUID id) {
        List<Event> events = eventRepository.findAllByClientUserId(id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    public ResponseEntity<Event> save(Event event, UUID clientId) {
        Optional<Client> client = clientRepository.findByUserId(clientId);
        if (!client.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        event.setClient(client.get());
        Event savedEvent = eventRepository.save(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.OK);
    }

    public void deleteEvent(UUID eventId) {
        eventRepository.deleteEventByEventId(eventId);
    }

    public ResponseEntity<Event> toggleEventCompleted(UUID id, Boolean completed) {
        Optional<Event> originalEvent = eventRepository.findById(id);
        if (!originalEvent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Event event = originalEvent.get();
        event.setCompleted(completed);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.OK);
    }
}
