package com.example.be_fintrack.service;

import com.example.be_fintrack.dto.InvestmentDTO;
import com.example.be_fintrack.entity.Investment;
import com.example.be_fintrack.entity.User;
import com.example.be_fintrack.repository.InvestmentRepository;
import com.example.be_fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repo;

    @Autowired
    private UserRepository userRepo;

    public Investment create(Investment i, User user) {
        i.setUser(user);
        return repo.save(i);
    }

    public List<InvestmentDTO> getDTOByUser(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Investment update(Long id, Investment updated, User user) {
        Investment i = repo.findById(id).orElseThrow();
        if (!i.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        i.setAssetName(updated.getAssetName());
        i.setAssetType(updated.getAssetType());
        i.setQuantity(updated.getQuantity());
        i.setPurchasePrice(updated.getPurchasePrice());
        i.setCurrentPrice(updated.getCurrentPrice());
        i.setPurchaseDate(updated.getPurchaseDate());
        return repo.save(i);
    }

    public void delete(Long id, User user) {
        Investment i = repo.findById(id).orElseThrow();
        if (!i.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        repo.deleteById(id);
    }

    private InvestmentDTO toDTO(Investment inv) {
        InvestmentDTO dto = new InvestmentDTO();
        dto.setId(inv.getId());
        dto.setAssetType(inv.getAssetType());
        dto.setAssetName(inv.getAssetName());
        dto.setQuantity(inv.getQuantity());
        dto.setPurchasePrice(inv.getPurchasePrice());
        dto.setCurrentPrice(inv.getCurrentPrice());
        dto.setPurchaseDate(inv.getPurchaseDate());
        dto.setProfitOrLoss(inv.getProfitOrLoss());

        if (inv.getUser() != null) {
            dto.setUserId(inv.getUser().getId());
            dto.setUsername(inv.getUser().getUsername());
        }

        return dto;
    }
}
