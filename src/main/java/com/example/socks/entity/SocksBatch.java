package com.example.socks.entity;


import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "sock_batches")
public class SocksBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String color;
    private Integer cottonPart;
    private Integer quantity;

    public SocksBatch(int i, String red, int i1) {
        this.cottonPart = i;
        this.color = red;
        this.quantity = i1;

    }
}