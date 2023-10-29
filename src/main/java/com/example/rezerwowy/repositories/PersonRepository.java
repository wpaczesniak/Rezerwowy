package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person,Long> {
    public Optional<Person> findByPublicId(UUID publicId);
    public boolean existsByPublicId(UUID publicUuid);
    public void deleteByPublicId(UUID publicUuid);
}
