package jsp.com.netcracker.students.o3.controller.sorters;

import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByArea;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByBalance;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByConnectedServices;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersById;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByLogin;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByName;
import jsp.com.netcracker.students.o3.controller.comparators.customer.ComparatorCustomersByPassword;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.CustomerSortType;
import jsp.com.netcracker.students.o3.model.users.Customer;

import java.util.Comparator;
import java.util.List;

public class CustomerSorter {
    /**
     * comparator which compare templates
     */
    private static CustomerSorter instance;

    private CustomerSorter() {
    }

    /**
     * define type of sorting
     *
     * @param type
     */
    private Comparator<Customer> defineSortType(CustomerSortType type) {
        switch (type) {
            case DownById:
                return new ComparatorCustomersById(false);
            case UpById:
                return new ComparatorCustomersById(true);
            case UpByName:
                return new ComparatorCustomersByName(true);
            case DownByName:
                return new ComparatorCustomersByName(false);
            case UpByLogin:
                return new ComparatorCustomersByLogin(true);
            case DownByLogin:
                return new ComparatorCustomersByLogin(false);
            case UpByBalance:
                return new ComparatorCustomersByBalance(true);
            case DownByBalance:
                return new ComparatorCustomersByBalance(false);
            case UpByArea:
                return new ComparatorCustomersByArea(true);
            case DownByArea:
                return new ComparatorCustomersByArea(false);
            case UpByPassword:
                return new ComparatorCustomersByPassword(true);
            case DownByPassword:
                return new ComparatorCustomersByPassword(false);
            case UpByConnectedServices:
                return new ComparatorCustomersByConnectedServices(true);
            case DownByConnectedServices:
                return new ComparatorCustomersByConnectedServices(false);
        }
        return new ComparatorCustomersById(true);
    }

    /**
     * sort services
     *
     * @param services
     */
    public void sort(List<Customer> services, CustomerSortType type) {
        if (type != null) {
            Comparator<Customer> templateComparator = defineSortType(type);
            services.sort(templateComparator);
        }
    }

    public static CustomerSorter getInstance() {
        if (instance == null) {
            instance = new CustomerSorter();
        }
        return instance;
    }
}
