package org.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}