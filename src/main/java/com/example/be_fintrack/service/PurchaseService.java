package com.example.be_fintrack.service;

import com.example.be_fintrack.entity.Purchase;
import com.example.be_fintrack.entity.Transaction;
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

        // ✅ Sau khi mua sắm được lưu, tạo giao dịch CHI tự động
        Transaction t = Transaction.builder()
                .type("expense") // ✅ Sửa từ "CHI" thành "expense"
                .category("Mua sắm")
                .amount(p.getPrice())
                .description("Mua: " + p.getProductName())
                .date(p.getPurchaseDate())
                .user(p.getUser()) // ✅ Gán user cho transaction
                .build();

        transactionRepo.save(t);

        return savedPurchase;
    }

    public List<Purchase> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public Purchase update(Long id, Purchase newP) {
        Purchase p = repo.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy purchase với id: " + id)
        );

        // ✅ Lưu giá cũ để cập nhật transaction tương ứng
        double oldPrice = p.getPrice();

        p.setProductName(newP.getProductName());
        p.setPrice(newP.getPrice());
        p.setStore(newP.getStore());
        p.setNote(newP.getNote());
        p.setProductLink(newP.getProductLink());
        p.setPurchaseDate(newP.getPurchaseDate());

        Purchase savedPurchase = repo.save(p);

        // ✅ Tìm và cập nhật transaction tương ứng (nếu có)
        List<Transaction> relatedTransactions = transactionRepo.findByUserIdAndDescriptionContaining(
                p.getUser().getId(),
                "Mua: " + p.getProductName()
        );

        if (!relatedTransactions.isEmpty()) {
            Transaction transaction = relatedTransactions.get(0);
            transaction.setAmount(newP.getPrice());
            transaction.setDescription("Mua: " + newP.getProductName());
            transaction.setDate(newP.getPurchaseDate());
            transactionRepo.save(transaction);
        }

        return savedPurchase;
    }

    public void delete(Long id) {
        Purchase purchase = repo.findById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy purchase với id: " + id)
        );

        // ✅ Xóa transaction tương ứng trước khi xóa purchase
        List<Transaction> relatedTransactions = transactionRepo.findByUserIdAndDescriptionContaining(
                purchase.getUser().getId(),
                "Mua: " + purchase.getProductName()
        );

        for (Transaction transaction : relatedTransactions) {
            transactionRepo.delete(transaction);
        }

        repo.deleteById(id);
    }
}