package com.example.be_fintrack.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // ✅ đặt lại tên bảng tránh dùng từ khóa SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String fullName;
}
