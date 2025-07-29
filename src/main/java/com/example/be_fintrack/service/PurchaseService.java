package com.example.be_fintrack.service;

import com.example.be_fintrack.dto.PurchaseDTO;
import com.example.be_fintrack.entity.Purchase;
import com.example.be_fintrack.entity.Transaction;
import com.example.be_fintrack.entity.User;
import com.example.be_fintrack.repository.PurchaseRepository;
import com.example.be_fintrack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private PurchaseRepository repo;

    public Purchase create(Purchase p) {
        Purchase savedPurchase = repo.save(p);

        // Sau khi mua sắm được lưu, tạo giao dịch CHI gắn với user
        Transaction t = Transaction.builder()
                .type("CHI")
                .category("Mua sắm")
                .amount(p.getPrice())
                .description("Mua: " + p.getProductName())
                .date(p.getPurchaseDate())
                .user(p.getUser()) // Gán user cho transaction
                .build();

        transactionRepo.save(t);

        return savedPurchase;
    }

    public List<Purchase> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public List<PurchaseDTO> getDTOByUser(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private PurchaseDTO toDTO(Purchase p) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(p.getId());
        dto.setProductName(p.getProductName());
        dto.setPrice(p.getPrice());
        dto.setStore(p.getStore());
        dto.setNote(p.getNote());
        dto.setProductLink(p.getProductLink());
        dto.setPurchaseDate(p.getPurchaseDate().toString());
        if (p.getUser() != null) {
            dto.setUserId(p.getUser().getId());
            dto.setUsername(p.getUser().getUsername());
        }
        return dto;
    }

    public Purchase update(Long id, Purchase newP, User user) {
        Purchase p = repo.findById(id).orElseThrow();
        if (!p.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        p.setProductName(newP.getProductName());
        p.setPrice(newP.getPrice());
        p.setStore(newP.getStore());
        p.setNote(newP.getNote());
        p.setProductLink(newP.getProductLink());
        p.setPurchaseDate(newP.getPurchaseDate());
        p.setUser(user);
        return repo.save(p);
    }

    public void delete(Long id, User user) {
        Purchase p = repo.findById(id).orElseThrow();
        if (!p.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        repo.deleteById(id);
    }
}
