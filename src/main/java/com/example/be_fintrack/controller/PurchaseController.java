package com.example.be_fintrack.controller;

import com.example.be_fintrack.dto.PurchaseDTO;
import com.example.be_fintrack.entity.Purchase;
import com.example.be_fintrack.entity.User;
import com.example.be_fintrack.repository.UserRepository;
import com.example.be_fintrack.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody PurchaseDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Purchase p = new Purchase();
        p.setProductName(dto.getProductName());
        p.setPrice(dto.getPrice());
        p.setStore(dto.getStore());
        p.setNote(dto.getNote());
        p.setProductLink(dto.getProductLink());
        p.setPurchaseDate(LocalDate.parse(dto.getPurchaseDate()));
        p.setUser(user);

        return ResponseEntity.ok(service.create(p));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(service.getDTOByUser(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> update(@PathVariable Long id, @RequestBody PurchaseDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Purchase p = new Purchase();
        p.setProductName(dto.getProductName());
        p.setPrice(dto.getPrice());
        p.setStore(dto.getStore());
        p.setNote(dto.getNote());
        p.setProductLink(dto.getProductLink());
        p.setPurchaseDate(LocalDate.parse(dto.getPurchaseDate()));
        p.setUser(user);

        return ResponseEntity.ok(service.update(id, p, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
