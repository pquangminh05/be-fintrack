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

    public Optional<Transaction> getById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction update(Long id, Transaction t) {
        t.setId(id);
        return transactionRepository.save(t);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getByType(String type) {
        return transactionRepository.findByType(type);
    }
}
