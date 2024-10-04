package com.timeflowsystem.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long customerId;
    private String email;
    private String pwd;

    @Column(name = "role")
    private String role;

    private boolean removed;

}
