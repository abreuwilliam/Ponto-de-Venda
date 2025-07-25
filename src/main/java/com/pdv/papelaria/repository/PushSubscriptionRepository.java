package com.pdv.papelaria.repository;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class PushSubscriptionRepository {
    private PushSubscriptionDTO subscription;

    public void salvar(PushSubscriptionDTO dto) {
        this.subscription = dto;
    }

    public PushSubscriptionDTO buscar() {
        return this.subscription;
    }
}
