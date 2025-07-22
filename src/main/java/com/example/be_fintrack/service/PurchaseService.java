package com.example.be_fintrack.service;

import com.example.be_fintrack.entity.Purchase;
import com.example.be_fintrack.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repo;

    public Purchase create(Purchase p) {
        return repo.save(p);
    }

    public List<Purchase> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public Purchase update(Long id, Purchase newP) {
        Purchase p = repo.findById(id).orElseThrow();
        p.setProductName(newP.getProductName());
        p.setPrice(newP.getPrice());
        p.setStore(newP.getStore());
        p.setNote(newP.getNote());
        p.setProductLink(newP.getProductLink());
        p.setPurchaseDate(newP.getPurchaseDate());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
