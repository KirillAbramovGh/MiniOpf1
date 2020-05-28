package jsp.com.netcracker.students.o3.controller.comparators.employee;

import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.Comparator;

public class ComparatorEmployeesByName implements Comparator<Employee>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorEmployeesByName(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Employee o1, final Employee o2) {
        int res = o1.getName().compareTo(o2.getName());

        if (isUp) return res;
        else return (-1) * res;
    }
}
