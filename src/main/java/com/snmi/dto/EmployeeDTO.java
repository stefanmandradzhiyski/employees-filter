package com.snmi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.snmi.util.LocalDateHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Class represents an employee.")
public class EmployeeDTO {

    @NotNull(message = "Employee id can't be null.")
    @Min(value = 1, message = "Employee id must be greater than 0.")
    @ApiModelProperty(notes = "Employee id", example = "2")
    private int empId;

    @NotNull(message = "Project id can't be null.")
    @Min(value = 1, message = "Project id must be greater than 0.")
    @ApiModelProperty(notes = "Project id", example = "8")
    private int projectId;

    @NotNull(message = "Date from can't be null.")
    @ApiModelProperty(notes = "The date on which the employee started working on the project.", example = "2016-06-08")
    @JsonDeserialize(using = LocalDateHandler.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateFrom;

    @ApiModelProperty(notes = "The date on which the employee stopped working on the project.", example = "04-15-2019")
    @JsonDeserialize(using = LocalDateHandler.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateTo;

}
