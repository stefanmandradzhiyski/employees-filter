package com.snmi.mapper;

import com.snmi.dto.LongestEmployeesCooperationDTO;
import com.snmi.model.EmployeesCollaboration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {EmployeeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LongestEmployeesCooperationMapper {

    @Mappings({
            @Mapping(target = "firstEmployee", source = "employeesCollaboration.firstEmployee"),
            @Mapping(target = "secondEmployee", source = "employeesCollaboration.secondEmployee"),
            @Mapping(target = "projectId", source = "employeesCollaboration.projectId"),
            @Mapping(target = "collaborationDuration", source = "employeesCollaboration.collaborationDuration")
    })
    LongestEmployeesCooperationDTO toLongestEmployeesCooperationDTO(EmployeesCollaboration employeesCollaboration);

}
