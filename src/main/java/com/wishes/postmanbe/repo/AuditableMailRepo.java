package com.wishes.postmanbe.repo;

import com.wishes.postmanbe.audit.AuditableMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditableMailRepo extends JpaRepository<AuditableMail, UUID> {
}
