package com.pdv.papelaria.repository;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import org.springframework.stereotype.Component;

@Component
public class PushSubscriptionRepository {
    private PushSubscriptionDTO subscription;

    public void salvar(PushSubscriptionDTO dto) {
        this.subscription = dto;
    }

    public PushSubscriptionDTO buscar() {
        return this.subscription;
    }
}
