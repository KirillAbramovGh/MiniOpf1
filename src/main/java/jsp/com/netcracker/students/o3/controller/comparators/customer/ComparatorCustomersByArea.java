package jsp.com.netcracker.students.o3.controller.comparators.customer;

import jsp.com.netcracker.students.o3.model.users.Customer;

import java.util.Comparator;

public class ComparatorCustomersByArea implements Comparator<Customer> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorCustomersByArea(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Customer o1, final Customer o2) {
        int res = o1.getArea().getId().compareTo(o2.getArea().getId());

        if (isUp) return res;
        else return (-1) * res;
    }
}
