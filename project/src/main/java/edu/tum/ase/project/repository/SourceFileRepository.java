package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, UUID> {}