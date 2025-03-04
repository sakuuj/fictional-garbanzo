package by.sakuuj.agsr.service.impl;

import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.dto.response.PatientsPageResponse;
import by.sakuuj.agsr.entity.PatientEntity;
import by.sakuuj.agsr.exceptions.EntityNotFoundException;
import by.sakuuj.agsr.mapper.PatientMapper;
import by.sakuuj.agsr.repository.PatientRepository;
import by.sakuuj.agsr.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;

    private final TransactionTemplate transactionTemplate;

    @Override
    @Transactional(readOnly = true)
    public PatientsPageResponse getAll(Pageable pageable) {

        Sort sort = Sort.by(Sort.Order.asc("id"));

        Page<PatientEntity> patientsPage = patientRepository.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort)
        );
        return patientMapper.toPageResponse(patientsPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getById(UUID id) {

        return patientRepository.findById(id)
                .map(patientMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public PatientResponse create(PatientRequest createRequest) {

        PatientEntity entityToSave = patientMapper.toEntity(createRequest);

        PatientEntity savedEntity = patientRepository.save(entityToSave);
        return patientMapper.toResponse(savedEntity);
    }

    @Override
    public PatientResponse update(UUID id, PatientRequest updateRequest) {

        PatientEntity updatedEntity = transactionTemplate.execute(status -> {

            PatientEntity entityToUpdate = patientRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            patientMapper.updateEntity(entityToUpdate, updateRequest);

            return entityToUpdate;
        });

        return patientMapper.toResponse(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {

        patientRepository.removeById(id);
    }
}
