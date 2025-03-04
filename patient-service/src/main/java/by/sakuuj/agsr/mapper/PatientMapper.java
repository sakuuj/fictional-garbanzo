package by.sakuuj.agsr.mapper;

import by.sakuuj.agsr.dto.request.PatientRequest;
import by.sakuuj.agsr.dto.response.PatientResponse;
import by.sakuuj.agsr.dto.response.PatientsPageResponse;
import by.sakuuj.agsr.entity.PatientEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(
        uses = PageMetadataMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PatientMapper {

    PatientResponse toResponse(PatientEntity entity);

    @Mapping(target = "patients", source = "content")
    @Mapping(target = "pageMetadata", source = "page", qualifiedByName = "toPageMetadata")
    PatientsPageResponse toPageResponse(Page<PatientEntity> page);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    PatientEntity toEntity(PatientRequest request);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget PatientEntity entity, PatientRequest updateRequest);
}
