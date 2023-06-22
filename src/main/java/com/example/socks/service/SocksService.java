package com.example.socks.service;

import com.example.socks.entity.SocksBatch;
import com.example.socks.enums.Operations;
import com.example.socks.repoistory.SocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * Сервис-класс
 */
@Service
@RequiredArgsConstructor
public class SocksService {
    private final SocksRepository sockBatchRepository;

    public SocksBatch income(SocksBatch sockBatch) {
        SocksBatch existing = sockBatchRepository.getByColorAndCottonPart(
                sockBatch.getColor(),
                sockBatch.getCottonPart()
        ).orElse(null);

        if (existing == null) {
            return sockBatchRepository.save(sockBatch);
        }

        existing.setQuantity(existing.getQuantity() + sockBatch.getQuantity());

        return sockBatchRepository.save(existing);
    }

    public SocksBatch outcome(SocksBatch sockBatch) {
        SocksBatch existing = sockBatchRepository.getByColorAndCottonPart(
                sockBatch.getColor(),
                sockBatch.getCottonPart()
        ).orElse(null);

        if (existing == null) {
            return null;
        }

        if (existing.getQuantity() < sockBatch.getQuantity()) {
            return null;
        }

        existing.setQuantity(existing.getQuantity() - sockBatch.getQuantity());

        return sockBatchRepository.save(existing);
    }

    public Integer get(String color, Operations operation, Integer cottonPart) {
        switch (operation) {
            case equal -> {
                return sockBatchRepository.getByColorAndCottonPart(color, cottonPart).stream()
                        .reduce(new SocksBatch(0, "", 0),
                                (a, b) -> new SocksBatch(0, "", 0)
                        ).getQuantity();
            }
            case lessThan -> {
                return sockBatchRepository.getAllByColorAndCottonPartLessThan(color, cottonPart).stream()
                        .reduce(new SocksBatch(0, "", 0),
                                (a, b) -> new SocksBatch(0, "", 0)
                        ).getQuantity();
            }
            case moreThan -> {
                return sockBatchRepository.getAllByColorAndCottonPartMoreThan(color, cottonPart).stream()
                        .reduce(new SocksBatch(0, "", 0),
                                (a, b) -> new SocksBatch(0, "", 0)
                        ).getQuantity();
            }
        }

        return null;
    }
}