package com.example.be_fintrack.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String assetType;     // cổ phiếu, vàng, crypto...

    @Column(columnDefinition = "NVARCHAR(255)")
    private String assetName;     // BTC, vàng SJC, FPT...

    private double quantity;
    private double purchasePrice;
    private double currentPrice;


    private LocalDate purchaseDate;

    @ManyToOne
    private User user;

    public double getProfitOrLoss() {
        return (currentPrice - purchasePrice) * quantity;
    }
}
