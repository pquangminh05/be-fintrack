package com.example.be_fintrack.service;

import com.example.be_fintrack.entity.Reminder;
import com.example.be_fintrack.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository repo;

    public Reminder create(Reminder r) {
        return repo.save(r);
    }

    public List<Reminder> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public Reminder update(Long id, Reminder updated) {
        Reminder r = repo.findById(id).orElseThrow();
        r.setTitle(updated.getTitle());
        r.setContent(updated.getContent());
        r.setRemindDate(updated.getRemindDate());
        r.setDone(updated.isDone());
        return repo.save(r);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
