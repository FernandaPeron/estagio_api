package com.estagio.estagio.repository;

import com.estagio.estagio.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Transactional
    List<Event> findAllByClientUserId(UUID clientId);

    @Transactional
    @Modifying
    @Query("delete from Event e where e.eventId = ?1")
    void deleteEventByEventId(UUID eventId);

}