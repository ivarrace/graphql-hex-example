package com.ivarrace.graphqlhex.domain.repository;

import com.ivarrace.graphqlhex.domain.model.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DummyRepository extends JpaRepository<Dummy, UUID> {
}
