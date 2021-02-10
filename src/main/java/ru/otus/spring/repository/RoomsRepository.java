package ru.otus.spring.repository;

import ru.otus.spring.domain.Rooms;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {
}
