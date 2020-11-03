package com.snmi.service;

import com.snmi.dto.EmployeeDTO;
import com.snmi.dto.EmployeesListWrapperDTO;
import com.snmi.dto.LongestEmployeesCooperationDTO;
import com.snmi.mapper.LongestEmployeesCooperationMapper;
import com.snmi.model.Employee;
import com.snmi.model.EmployeesCollaboration;
import com.snmi.util.CapacityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private static final String MISSING_EMPLOYEE_COLLABORATION = "There aren't any employees collaborations";

    private final LongestEmployeesCooperationMapper longestEmployeesCooperationMapper;

    public EmployeeService(LongestEmployeesCooperationMapper longestEmployeesCooperationMapper) {
        this.longestEmployeesCooperationMapper = longestEmployeesCooperationMapper;
    }

    /**
     * This method retrieves a pair of employees who have worked together for the longest time on common projects.
     *
     * Criteria:
     *  List of valid employees with minimum size of 2 and maximum of 256.
     *
     * Additional uses:
     *  {@link #toEmployee(EmployeeDTO)} map a passed employeeDTO to employee;
     *  {@link #didEmployeesWorkTogether(Employee, Employee)} check if these two employees have worked together;
     *  {@link #getCooperationDays(Employee, Employee)} calculate the duration of the collaboration work between the two employees.
     *
     * @param requestEmployees list of valid employees
     * @return List of LongestEmployeesCooperationDTO that contains:
     * - pair of employees;
     * - the identifier of their common project;
     * - the duration of their collaboration work.
     */
    public List<LongestEmployeesCooperationDTO> getTheLongestEmployeesCooperation(EmployeesListWrapperDTO requestEmployees) {
        Set<Employee> uniqueListOfEmployees = new HashSet<>(CapacityHandler.getCapacity(requestEmployees.getEmployees().size()));
        uniqueListOfEmployees.addAll(requestEmployees.getEmployees()
                .stream()
                .map(this::toEmployee)
                .collect(Collectors.toSet()));

        Set<EmployeesCollaboration> employeesCollaborations
                = new HashSet<>(CapacityHandler.getCapacity(uniqueListOfEmployees.size()));
        for (Employee mainEmployee : uniqueListOfEmployees) {
            for (Employee subEmployee : uniqueListOfEmployees) {
                if (mainEmployee != subEmployee && didEmployeesWorkTogether(mainEmployee, subEmployee)) {
                    employeesCollaborations.add(new EmployeesCollaboration(mainEmployee, subEmployee,
                            mainEmployee.getProjectId(), getCooperationDays(mainEmployee, subEmployee)));
                }
            }
        }

        Map<String, Integer> employeesCollaborationTotalDuration
                = new HashMap<>(CapacityHandler.getCapacity(employeesCollaborations.size()));
        for (EmployeesCollaboration employeeCollaboration : employeesCollaborations) {
            if (employeesCollaborationTotalDuration.containsKey(employeeCollaboration.getUniqueComplexId())) {
                int currentCount = employeesCollaborationTotalDuration.get(employeeCollaboration.getUniqueComplexId());
                employeesCollaborationTotalDuration.put(employeeCollaboration.getUniqueComplexId(),
                        currentCount + employeeCollaboration.getCollaborationDuration());
            } else {
                employeesCollaborationTotalDuration.put(employeeCollaboration.getUniqueComplexId(),
                        employeeCollaboration.getCollaborationDuration());
            }
        }

        if (employeesCollaborationTotalDuration.isEmpty()) {
            LOGGER.warn(MISSING_EMPLOYEE_COLLABORATION);
            return new ArrayList<>();
        }

        String theLongestEmployeesCollaboration = employeesCollaborationTotalDuration.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .get(0);

        return employeesCollaborations.stream()
                .filter(employeeCollaboration -> employeeCollaboration.getUniqueComplexId().equals(theLongestEmployeesCollaboration))
                .map(longestEmployeesCooperationMapper::toLongestEmployeesCooperationDTO)
                .collect(Collectors.toList());
    }

    private Employee toEmployee(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getEmpId(), employeeDTO.getProjectId(), employeeDTO.getDateFrom(), employeeDTO.getDateTo());
    }

    private static boolean didEmployeesWorkTogether(Employee firstEmployee, Employee secondEmployee) {
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo())
                || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo()))
                && (secondEmployee.getDateFrom().isBefore(firstEmployee.getDateTo())
                || secondEmployee.getDateFrom().isEqual(firstEmployee.getDateTo()))
                && firstEmployee.getProjectId() == secondEmployee.getProjectId();
    }

    private static int getCooperationDays(Employee firstEmployee, Employee secondEmployee) {
        LocalDate startDate = Collections.max(Arrays.asList(firstEmployee.getDateFrom(), secondEmployee.getDateFrom()));
        LocalDate endDate = Collections.min(Arrays.asList(firstEmployee.getDateTo(), secondEmployee.getDateTo()));
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

}
