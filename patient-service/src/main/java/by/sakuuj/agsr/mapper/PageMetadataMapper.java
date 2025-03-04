package by.sakuuj.agsr.mapper;

import by.sakuuj.agsr.dto.response.PageMetadata;
import by.sakuuj.agsr.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageMetadataMapper {

    @Named("toPageMetadata")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "pageNumber", source = "number")
    PageMetadata toPageMetadata(Page<PatientEntity> page);
}
