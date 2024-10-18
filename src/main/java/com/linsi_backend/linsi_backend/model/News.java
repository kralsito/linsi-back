package com.linsi_backend.linsi_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column
    private Long imageId;

    @PrePersist
    public void prePersist() {
        this.publicationDate = LocalDateTime.now();
    }
}
