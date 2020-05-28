package jsp.com.netcracker.students.o3.controller.comparators.employee;

import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.Comparator;

public class ComparatorEmployeesById implements Comparator<Employee> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorEmployeesById(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Employee o1, final Employee o2) {
        int res = o1.getId().compareTo(o2.getId());

        if (isUp) return res;
        else return (-1) * res;
    }
}

