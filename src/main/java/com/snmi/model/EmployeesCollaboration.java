package com.snmi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class EmployeesCollaboration {

    private String uniqueComplexId;

    private Employee firstEmployee;

    private Employee secondEmployee;

    private int projectId;

    private int collaborationDuration;

    public EmployeesCollaboration(Employee firstEmployee, Employee secondEmployee, int projectId, int collaborationDuration) {
        this.uniqueComplexId = String.format("%s%s", firstEmployee.getEmpId(), secondEmployee.getEmpId());
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.projectId = projectId;
        this.collaborationDuration = collaborationDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeesCollaboration that = (EmployeesCollaboration) o;

        return ((Objects.equals(firstEmployee, that.firstEmployee) && Objects.equals(secondEmployee, that.secondEmployee))
                || ((Objects.equals(firstEmployee, that.secondEmployee) && Objects.equals(secondEmployee, that.firstEmployee))))
                && projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
