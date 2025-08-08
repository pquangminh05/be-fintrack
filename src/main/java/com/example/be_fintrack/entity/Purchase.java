package com.example.be_fintrack.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String productName;

    private double price;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String store;           // Ví dụ: Shopee, Tiki, v.v.

    @Column(columnDefinition = "NVARCHAR(500)")
    private String note;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String productLink;

    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
