package com.example.be_fintrack.controller;

import com.example.be_fintrack.dto.InvestmentDTO;
import com.example.be_fintrack.entity.Investment;
import com.example.be_fintrack.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.be_fintrack.entity.User;

import java.util.*;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin
public class InvestmentController {

    @Autowired
    private InvestmentService service;

    @PostMapping
    public ResponseEntity<Investment> create(@RequestBody Investment i) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(service.create(i, user));
    }

    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> getByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(service.getDTOByUser(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investment> update(@PathVariable Long id, @RequestBody Investment i) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(service.update(id, i, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
