package com.linsi_backend.linsi_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int dni;

    @Column
    private int file; //legajo

    @Column
    private String universityYear;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false, referencedColumnName = "id")
    private Area area;

    @Enumerated(EnumType.STRING)
    private RegistrationStatusType registrationStatusType;

    @PrePersist
    public void prePersist(){
        this.registrationStatusType = RegistrationStatusType.PENDING;
    }

}
