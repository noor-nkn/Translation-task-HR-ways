package com.transalation.management.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String translationKey;
    private String content; // message content
    private String tag; //mobile, web
    
    @ManyToOne
    @JoinColumn(name = "language_id") 
    @JsonBackReference
    private Language language;
}