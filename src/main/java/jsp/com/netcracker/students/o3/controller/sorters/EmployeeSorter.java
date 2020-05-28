package jsp.com.netcracker.students.o3.controller.sorters;

import jsp.com.netcracker.students.o3.controller.comparators.employee.ComparatorEmployeesById;
import jsp.com.netcracker.students.o3.controller.comparators.employee.ComparatorEmployeesByLogin;
import jsp.com.netcracker.students.o3.controller.comparators.employee.ComparatorEmployeesByName;
import jsp.com.netcracker.students.o3.controller.comparators.employee.ComparatorEmployeesByPassword;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.EmployeeSortType;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {
    /**
     * comparator which compare templates
     */
    private static EmployeeSorter instance;

    private EmployeeSorter() {
    }

    /**
     * define type of sorting
     *
     * @param type
     */
    private Comparator<Employee> defineSortType(EmployeeSortType type) {
        switch (type) {
            case DownById:
                return new ComparatorEmployeesById(false);
            case UpById:
                return new ComparatorEmployeesById(true);
            case UpByName:
                return new ComparatorEmployeesByName(true);
            case DownByName:
                return new ComparatorEmployeesByName(false);
            case UpByLogin:
                return new ComparatorEmployeesByLogin(true);
            case DownByLogin:
                return new ComparatorEmployeesByLogin(false);
            case UpByPassword:
                return new ComparatorEmployeesByPassword(true);
            case DownByPassword:
                return new ComparatorEmployeesByPassword(false);
        }
        return new ComparatorEmployeesById(true);
    }

    /**
     * sort services
     *
     * @param services
     */
    public void sort(List<Employee> services, EmployeeSortType type) {
        if (type != null) {
            Comparator<Employee> templateComparator = defineSortType(type);
            services.sort(templateComparator);
        }
    }

    public static EmployeeSorter getInstance() {
        if (instance == null) {
            instance = new EmployeeSorter();
        }
        return instance;
    }
}
