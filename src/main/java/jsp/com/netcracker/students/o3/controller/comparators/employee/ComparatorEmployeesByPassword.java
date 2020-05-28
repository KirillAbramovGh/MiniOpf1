package jsp.com.netcracker.students.o3.controller.comparators.employee;

import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.Comparator;

public class ComparatorEmployeesByPassword implements Comparator<Employee> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorEmployeesByPassword(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Employee o1, final Employee o2) {
        int res = o1.getPassword().compareTo(o2.getPassword());

        if (isUp) return res;
        else return (-1) * res;
    }
}

