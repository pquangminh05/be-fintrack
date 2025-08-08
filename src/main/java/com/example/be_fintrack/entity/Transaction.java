package com.example.be_fintrack.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String type;  // income / expense

    @Column(columnDefinition = "NVARCHAR(100)")
    private String category;

    private Double amount;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    private LocalDate date;

    // ✅ Thêm trường createdAt cho thống kê (nếu cần khác với date)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ Tự động set createdAt nếu null
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = date != null ? date : LocalDate.now();
        }
    }
}