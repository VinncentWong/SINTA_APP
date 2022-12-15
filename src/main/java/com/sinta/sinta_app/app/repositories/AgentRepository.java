package com.sinta.sinta_app.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sinta.sinta_app.entity.agent.Agent;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long>{}
