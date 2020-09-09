package com.estagio.estagio.controller;

import com.estagio.estagio.entity.Event;
import com.estagio.estagio.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEvents(@RequestParam String clientId) {
        return eventService.getByClientId(UUID.fromString(clientId));
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ResponseEntity<Event> postEvent(@RequestBody Event event,
                                           @RequestParam String clientId) {
        return eventService.save(event, UUID.fromString(clientId));
    }

    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable String eventId) {
        eventService.deleteEvent(UUID.fromString(eventId));
    }

};

