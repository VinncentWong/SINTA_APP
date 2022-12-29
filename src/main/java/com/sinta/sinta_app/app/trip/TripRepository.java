package com.sinta.sinta_app.app.trip;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinta.sinta_app.entity.trip.KategoriTrip;
import com.sinta.sinta_app.entity.trip.Trip;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE agent_id = ?1 AND id = ?2")
    Optional<Trip> find(Long idAgent, Long idTrip);

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE agent_id = ?1")
    List<Trip> find(Long idAgent);

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE kategori_trip = ?1")
    List<Trip> findByCategory(int kategori);

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE need_requirement = ?1")
    List<Trip> find(boolean notNeedRequirement);

    @Query(nativeQuery = true, value = "SELECT * FROM trip WHERE id IN (SELECT id_trip FROM harga WHERE harga <= ?1) AND kategori_trip = ?2")
    List<Trip> find(Long maxPrice, int kategori);
}
