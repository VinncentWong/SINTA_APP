package com.sinta.sinta_app.app.agent;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinta.sinta_app.entity.agent.Agent;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM agent WHERE email = ?1 LIMIT 1")
    public Optional<Agent> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM agent WHERE username = ?1 LIMIT 1")
    public Optional<Agent> findByUsername(String username);
}
