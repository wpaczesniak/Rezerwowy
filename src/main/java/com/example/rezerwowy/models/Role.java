package com.example.rezerwowy.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Role {
    @Id
    private Long id;

    @ManyToOne
    private Person person;
}
