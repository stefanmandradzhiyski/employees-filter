package com.snmi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @NotNull(message = "Employee id can't be null.")
    @Min(value = 1, message = "Employee id must be greater than 0.")
    private int empId;

    @NotNull(message = "Project id can't be null.")
    @Min(value = 1, message = "Project id must be greater than 0.")
    private int projectId;

    @NotNull(message = "Date from can't be null.")
    private LocalDate dateFrom;

    private LocalDate dateTo;

    private int collaborationDuration;

    public Employee(@NotNull int empId, @NotNull int projectId, @NotNull LocalDate dateFrom, LocalDate dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        setDateTo(dateTo);
    }

    private void setDateTo(LocalDate dateTo) {
        this.dateTo = Objects.requireNonNullElseGet(dateTo, LocalDate::now);

        if (dateFrom.isAfter(this.dateTo)) {
            this.dateTo = this.dateFrom;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;
        return empId == employee.empId && projectId == employee.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, projectId);
    }
}
