package com.snmi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Class represents the longest employees cooperation at same project.")
public class LongestEmployeesCooperationDTO {

    @ApiModelProperty(notes = "First employee")
    private EmployeeDTO firstEmployee;

    @ApiModelProperty(notes = "Second employee")
    private EmployeeDTO secondEmployee;

    @ApiModelProperty(notes = "The project id they worked on together.")
    private int projectId;

    @ApiModelProperty(notes = "Duration of their collaboration work.")
    private int collaborationDuration;

}
