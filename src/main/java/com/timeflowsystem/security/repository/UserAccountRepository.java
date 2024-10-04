package com.timeflowsystem.security.repository;

import com.timeflowsystem.security.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount,Long> {

    Optional<UserAccount> findByEmail(String email);

}
