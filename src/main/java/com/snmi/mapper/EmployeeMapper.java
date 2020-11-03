package com.snmi.mapper;

import com.snmi.dto.EmployeeDTO;
import com.snmi.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "empId", source = "source.empId"),
            @Mapping(target = "projectId", source = "source.projectId"),
            @Mapping(target = "dateFrom", source = "source.dateFrom"),
            @Mapping(target = "dateTo", source = "source.dateTo")
    })
    EmployeeDTO toEmployeeDTO(Employee source);

}
