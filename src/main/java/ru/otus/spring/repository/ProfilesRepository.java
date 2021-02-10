package ru.otus.spring.repository;

import ru.otus.spring.domain.Profiles;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Profiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {
}
