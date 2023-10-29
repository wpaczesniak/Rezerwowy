package com.example.rezerwowy.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="personID")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surename")
    private String surename;

    @PrimaryKeyJoinColumn(name="teamID")
    @ManyToOne
    private Team team;

    @PrimaryKeyJoinColumn(name="roleID")
    @OneToMany
    private List<Role> roles = new LinkedList<>();
}
