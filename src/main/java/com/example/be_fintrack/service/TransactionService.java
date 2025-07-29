package com.example.be_fintrack.service;

import com.example.be_fintrack.entity.Transaction;
import com.example.be_fintrack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction create(Transaction t) {
        return transactionRepository.save(t);
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getById(Long id, Long userId) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent() && transaction.get().getUser().getId().equals(userId)) {
            return transaction;
        }
        return Optional.empty();
    }

    public Transaction update(Long id, Transaction t, Long userId) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!existing.getUser().getId().equals(userId)) {
            throw new RuntimeException("Permission denied");
        }
        // Cập nhật từng trường
        existing.setAmount(t.getAmount());
        existing.setType(t.getType());
        existing.setDate(t.getDate());
        existing.setDescription(t.getDescription());
        existing.setCategory(t.getCategory());

        return transactionRepository.save(existing);
    }


    public void delete(Long id, Long userId) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!existing.getUser().getId().equals(userId)) {
            throw new RuntimeException("Permission denied");
        }
        transactionRepository.deleteById(id);
    }


    public List<Transaction> getByType(String type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
