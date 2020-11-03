package com.snmi.rest;

import com.snmi.dto.EmployeesListWrapperDTO;
import com.snmi.dto.LongestEmployeesCooperationDTO;
import com.snmi.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@Api("Employee API")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/retrieve-the-longest-cooperation")
    @ApiOperation(value = "Receives a list of employees and returns the pair of employees who have been working the longest on common projects.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval.",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Invalid list of employees.",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service.",
                    response = ResponseEntity.class)
    })
    public ResponseEntity<List<LongestEmployeesCooperationDTO>> getTheLongestEmployeesCooperation(
            @Valid @RequestBody EmployeesListWrapperDTO requestEmployees
    ) {
        return ResponseEntity.ok(employeeService.getTheLongestEmployeesCooperation(requestEmployees));
    }

}
