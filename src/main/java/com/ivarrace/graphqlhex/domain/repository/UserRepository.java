package com.ivarrace.graphqlhex.domain.repository;

import com.ivarrace.graphqlhex.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
