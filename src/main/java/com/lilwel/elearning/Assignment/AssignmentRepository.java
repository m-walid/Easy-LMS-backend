package com.lilwel.elearning.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
}
