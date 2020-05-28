package jsp.com.netcracker.students.o3.controller.comparators.customer;

import jsp.com.netcracker.students.o3.model.users.Customer;

import java.util.Comparator;

public class ComparatorCustomersByPassword implements Comparator<Customer> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorCustomersByPassword(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Customer o1, final Customer o2) {
        int res = o1.getPassword().compareTo(o2.getPassword());

        if (isUp) return res;
        else return (-1) * res;
    }
}
