package com.sinta.sinta_app.app.trip;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinta.sinta_app.entity.trip.Trip;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE agent_id = ?1 AND id = ?2")
    Optional<Trip> find(Long idAgent, Long idTrip);

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE agent_id = ?1")
    Optional<List<Trip>> find(Long idAgent);
}
