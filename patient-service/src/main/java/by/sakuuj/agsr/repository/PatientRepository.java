package by.sakuuj.agsr.repository;

import by.sakuuj.agsr.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface PatientRepository extends Repository<PatientEntity, UUID> {

    Optional<PatientEntity> findById(UUID id);

    Page<PatientEntity> findAll(Pageable pageable);

    PatientEntity save(PatientEntity entity);

    void removeById(UUID id);
}
