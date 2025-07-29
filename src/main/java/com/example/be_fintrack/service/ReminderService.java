package com.example.be_fintrack.service;

import com.example.be_fintrack.entity.Reminder;
import com.example.be_fintrack.entity.User;
import com.example.be_fintrack.repository.ReminderRepository;
import com.example.be_fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    public Reminder create(Reminder r, User user) {
        r.setUser(user);
        return reminderRepository.save(r);
    }

    public List<Reminder> getByUser(Long userId) {
        return reminderRepository.findByUserId(userId);
    }

    public Reminder update(Long id, Reminder updated, User user) {
        Reminder r = reminderRepository.findById(id).orElseThrow();
        if (!r.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        r.setTitle(updated.getTitle());
        r.setContent(updated.getContent());
        r.setRemindDate(updated.getRemindDate());
        r.setDone(updated.isDone());
        return reminderRepository.save(r);
    }

    public void delete(Long id, User user) {
        Reminder r = reminderRepository.findById(id).orElseThrow();
        if (!r.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Permission denied");
        }
        reminderRepository.deleteById(id);
    }
}
