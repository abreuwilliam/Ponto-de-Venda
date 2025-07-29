package com.pdv.papelaria.controller;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import com.pdv.papelaria.repository.PushSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class PushSubscriptionController {

    @Autowired
    private PushSubscriptionRepository repository;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody PushSubscriptionDTO dto) {
        if (dto.getKeys() == null || dto.getKeys().getP256dh() == null || dto.getKeys().getAuth() == null) {
            return ResponseEntity.badRequest().build();
        }

        repository.salvar(dto); // ✅ usa diretamente o DTO
        return ResponseEntity.ok().build();
    }
}
