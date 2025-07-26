package com.pdv.papelaria.repository;

import com.pdv.papelaria.dto.PushSubscriptionDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PushSubscriptionRepository {

    private final List<PushSubscriptionDTO> subscriptions = new ArrayList<>();

    public void salvar(PushSubscriptionDTO dto) {
        // Evita duplicações pelo endpoint
        boolean jaExiste = subscriptions.stream()
                .anyMatch(s -> s.getEndpoint().equals(dto.getEndpoint()));

        if (!jaExiste) {
            subscriptions.add(dto);
            System.out.println("📬 Nova subscription salva: " + dto.getEndpoint());
        } else {
            System.out.println("📫 Subscription já existe: " + dto.getEndpoint());
        }
    }

    public List<PushSubscriptionDTO> listarTodas() {
        return subscriptions;
    }
}
