package com.timeflowsystem.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
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

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    private boolean removed;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return id == that.id && removed == that.removed && Objects.equals(customerId, that.customerId) && Objects.equals(email, that.email) && Objects.equals(pwd, that.pwd) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, email, pwd, authorities, removed);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", authorities=" + authorities +
                ", removed=" + removed +
                '}';
    }
}
