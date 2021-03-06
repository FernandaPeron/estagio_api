package com.estagio.estagio.repository;

import com.estagio.estagio.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    @Transactional
    Optional<Archive> findByArchiveName(String name);

    @Transactional
    Optional<Archive> findByArchiveId(UUID archiveId);

    @Transactional
    List<Archive> findAllByClientUserId(UUID userId);

    @Transactional
    List<Archive> findAllByClientUserIdAndType(UUID userId, String type);

    @Transactional
    @Modifying
    @Query("delete from Archive a where a.archiveId = ?1")
    void deleteArchiveByArchiveId(UUID archiveId);
}