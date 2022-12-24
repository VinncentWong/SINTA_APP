package com.sinta.sinta_app.app.portofolio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinta.sinta_app.entity.portofolio.Portofolio;

@Repository
public interface PortofolioRepository extends CrudRepository<Portofolio, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM portofolio WHERE agent_id=  ?1 AND id = ?2 LIMIT 1")
    Optional<Portofolio> find(Long idAgent, Long idPortofolio);

    @Query(nativeQuery = true, value = "SELECT * FROM portofolio WHERE agent_id = ?1")
    List<Portofolio> find(Long idAgent);
}
