
package com.example.OrbitOnboarding.mapper;

import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.entity.TrainingModule;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface TrainingModuleMapper {

    TrainingModule toEntity(TrainingModuleRequest request);

    @Mapping(source="createdBy.username",target="createdBy")
    TrainingModuleResponse toResponse(TrainingModule module);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "estimatedHour", source = "estimatedHour")//
    void updateEntityFromRequest(TrainingModuleRequest request, @MappingTarget TrainingModule module); //



}


