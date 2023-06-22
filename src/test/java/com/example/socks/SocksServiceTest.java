package com.example.socks;

import com.example.socks.entity.SocksBatch;
import com.example.socks.enums.Operations;
import com.example.socks.repoistory.SocksRepository;
import com.example.socks.service.SocksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SocksServiceTest {
    SocksRepository socksRepository;
    SocksService socksService;

    @BeforeEach
    public void setUp() throws Exception {
        socksRepository = mock(SocksRepository.class);
        socksService = new SocksService(socksRepository);
    }

    @Test
    public void testIncomeNew() {
        SocksBatch batch = new SocksBatch(10, "red", 50);
        when(socksRepository.getByColorAndCottonPart("red", 50)).thenReturn(Optional.empty());
        when(socksRepository.save(batch)).thenReturn(batch);

        SocksBatch result = socksService.income(batch);
        assertEquals(batch, result);
    }

    @Test
    public void testIncomeExisting() {
        SocksBatch existing = new SocksBatch(10, "red", 50);
        SocksBatch incoming = new SocksBatch(5, "red", 50);

        when(socksRepository.getByColorAndCottonPart("red", 50)).thenReturn(Optional.of(existing));
        when(socksRepository.save(existing)).thenReturn(existing);

        SocksBatch result = socksService.income(incoming);
        assertEquals(15, result.getQuantity());
    }

    @Test
    public void testOutcome() {
        SocksBatch existing = new SocksBatch(10, "red", 50);
        SocksBatch outgoing = new SocksBatch(5, "red", 50);

        when(socksRepository.getByColorAndCottonPart("red", 50)).thenReturn(Optional.of(existing));
        when(socksRepository.save(existing)).thenReturn(existing);

        SocksBatch result = socksService.outcome(outgoing);
        assertEquals(5, result.getQuantity());
    }

    @Test
    public void testGetByEqual() {
        SocksBatch existing = new SocksBatch(10, "red", 50);

        when(socksRepository.getByColorAndCottonPart("red", 50)).thenReturn(Optional.of(existing));

        Integer result = socksService.get("red", Operations.equal, 50);
        assertEquals(10, result);
    }

    @Test
    public void testGetByLessThan() {
        SocksBatch batch1 = new SocksBatch(5, "red", 50);
        SocksBatch batch2 = new SocksBatch(10, "red", 30);

        when(socksRepository.getAllByColorAndCottonPartLessThan("red", 40)).thenReturn(List.of(batch1, batch2));

        Integer result = socksService.get("red", Operations.lessThan, 40);
        assertEquals(15, result);
    }

    @Test
    public void testGetByMoreThan() {
        SocksBatch batch1 = new SocksBatch(5, "red", 50);
        SocksBatch batch2 = new SocksBatch(10, "red", 60);

        when(socksRepository.getAllByColorAndCottonPartMoreThan("red", 40)).thenReturn(List.of(batch1, batch2));

        Integer result = socksService.get("red", Operations.moreThan, 40);
        assertEquals(15, result);
    }
}