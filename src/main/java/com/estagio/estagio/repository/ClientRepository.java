package com.estagio.estagio.repository;

import com.estagio.estagio.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Transactional
    Optional<Client> findByEmail(String emailAddress);

    @Transactional
    Optional<Client> findByUserId(UUID id);

}