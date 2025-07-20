package com.transalation.management.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transalation.management.task.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
