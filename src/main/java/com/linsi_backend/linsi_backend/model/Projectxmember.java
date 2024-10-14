package com.linsi_backend.linsi_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Projectxmember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
