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

    public Reminder create(Reminder r) {
        return reminderRepository.save(r);
    }

    // ✅ Cập nhật để chỉ lấy reminder của user đó
    public List<Reminder> getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));
        return reminderRepository.findByUserId(user.getId());
    }

    // ✅ Thêm phương thức lấy theo userId
    public List<Reminder> getByUserId(Long userId) {
        return reminderRepository.findByUserId(userId);
    }

    public Reminder update(Long id, Reminder updated) {
        Reminder r = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy reminder với id: " + id));

        r.setTitle(updated.getTitle());
        r.setContent(updated.getContent());
        r.setRemindDate(updated.getRemindDate());
        r.setDone(updated.isDone());
        return reminderRepository.save(r);
    }

    public void delete(Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy reminder với id: " + id);
        }
        reminderRepository.deleteById(id);
    }
}