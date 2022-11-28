package com.justin.springbootHandBook.annotation.Transactional.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "team", schema = "handbook")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
