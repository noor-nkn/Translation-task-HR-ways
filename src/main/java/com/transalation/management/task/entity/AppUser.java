package com.transalation.management.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppUser {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
}