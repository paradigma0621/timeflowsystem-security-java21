package com.timeflowsystem.security.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_account")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

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
