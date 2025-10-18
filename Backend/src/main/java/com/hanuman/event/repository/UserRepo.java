package com.hanuman.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanuman.event.entity.domain.User;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepo extends JpaRepository<User , Long> {

    boolean existsByKeyCloakId(UUID keyCloakId);
    Optional<User> findByKeyCloakId(UUID keyCloakId);
}
