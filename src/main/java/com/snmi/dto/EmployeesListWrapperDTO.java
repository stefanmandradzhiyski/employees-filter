package com.snmi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Class represents a request employee list wrapper.")
public class EmployeesListWrapperDTO {

    private static final int MIN_LIST_SIZE = 2;
    private static final int MAX_LIST_SIZE = 256;

    @NotNull(message = "Employees list must not be null.")
    @NotEmpty(message = "Employees list must no be empty.")
    @Size(min = MIN_LIST_SIZE, max = MAX_LIST_SIZE, message = "List size must be between 2 and 256.")
    @Valid
    @ApiModelProperty(notes = "Employees list", example = "{'empId' : '2', 'projectId' : '8', 'dateFrom' : '2016-06-08', 'dateTo' : '2019-04-15'}")
    private List<EmployeeDTO> employees = new ArrayList<>();

}
