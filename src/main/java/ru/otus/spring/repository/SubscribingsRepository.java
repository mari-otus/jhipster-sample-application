package ru.otus.spring.repository;

import ru.otus.spring.domain.Subscribings;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subscribings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscribingsRepository extends JpaRepository<Subscribings, Long> {
}
