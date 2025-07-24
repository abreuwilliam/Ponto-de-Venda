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
        repository.salvar(dto);
        return ResponseEntity.ok().build();
    }
}
