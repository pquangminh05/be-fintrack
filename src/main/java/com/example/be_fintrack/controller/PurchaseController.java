package com.example.be_fintrack.controller;

import com.example.be_fintrack.entity.Purchase;
import com.example.be_fintrack.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody Purchase p) {
        return ResponseEntity.ok(service.create(p));
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> update(@PathVariable Long id, @RequestBody Purchase p) {
        return ResponseEntity.ok(service.update(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
