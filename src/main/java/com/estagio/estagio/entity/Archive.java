package com.estagio.estagio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID archiveId;

    @Column
    private String archiveName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    private Client client;

    @Column
    @Lob
    private byte[] file;

    public UUID getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(UUID archiveId) {
        this.archiveId = archiveId;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}