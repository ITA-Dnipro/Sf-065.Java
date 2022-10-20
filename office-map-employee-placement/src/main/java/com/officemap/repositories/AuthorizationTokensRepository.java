package com.officemap.repositories;

import com.officemap.authorization.AuthorizationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationTokensRepository extends JpaRepository<AuthorizationToken, Long> {
}
