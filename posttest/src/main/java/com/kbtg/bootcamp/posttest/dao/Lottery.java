package com.kbtg.bootcamp.posttest.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lottery")
@Getter
@Setter
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "price")
    private int price;
}
