package com.example.socks.repoistory;

import com.example.socks.entity.SocksBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
/**
 * interface SocksRepository
 */
public interface SocksRepository extends JpaRepository<SocksBatch, Integer> {
    Optional<SocksBatch> getByColorAndCottonPart(String color, Integer cottonPart);

    @Query("SELECT sb FROM SocksBatch sb WHERE sb.color = :color AND sb.cottonPart < :cottonPart")
    List<SocksBatch> getAllByColorAndCottonPartLessThan(String color, Integer cottonPart);

    @Query("SELECT sb FROM SocksBatch sb WHERE sb.color = :color AND sb.cottonPart > :cottonPart")
    List<SocksBatch> getAllByColorAndCottonPartMoreThan(String color, Integer cottonPart);
}