package com.example.be_fintrack.repository;

import com.example.be_fintrack.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(String type);
    List<Transaction> findByUserId(Long userId);

    // ✅ Thêm phương thức tìm theo type và userId
    List<Transaction> findByTypeAndUserId(String type, Long userId);

    // ✅ Thêm phương thức tìm theo userId và description chứa text
    List<Transaction> findByUserIdAndDescriptionContaining(Long userId, String description);
}