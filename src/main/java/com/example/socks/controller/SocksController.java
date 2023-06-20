package com.example.socks.controller;

import com.example.socks.entity.SocksBatch;
import com.example.socks.enums.Operations;
import com.example.socks.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/socks")
@RequiredArgsConstructor
public class SocksController {
    private final SocksService sockBatchService;

    @PostMapping("income")
    public ResponseEntity<SocksBatch> income(@RequestBody SocksBatch sockBatch){
        return ResponseEntity.ok(sockBatchService.income(sockBatch));
    }

    @PostMapping("outcome")
    public ResponseEntity<SocksBatch> outcome(@RequestBody SocksBatch sockBatch){
        return ResponseEntity.ok(sockBatchService.outcome(sockBatch));
    }

    @GetMapping("")
    public ResponseEntity<Integer> get(@RequestParam String color, @RequestParam Operations operation, @RequestParam Integer cottonPart){
        return ResponseEntity.ok(sockBatchService.get(color, operation, cottonPart));
    }
}