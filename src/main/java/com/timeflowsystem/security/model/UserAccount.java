package com.timeflowsystem.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

   // @Column(name = "role")
   // private String role;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    private boolean removed;

}
