package ru.otus.spring.repository;

import ru.otus.spring.domain.Bookings;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bookings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {
}
