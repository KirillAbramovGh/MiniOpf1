package jsp.com.netcracker.students.o3.controller.comparators.customer;

import jsp.com.netcracker.students.o3.model.users.Customer;

import java.util.Comparator;

public class ComparatorCustomersByConnectedServices implements Comparator<Customer> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorCustomersByConnectedServices(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Customer o1, final Customer o2) {
        int res = o1.getConnectedServices().toString().compareTo(o2.getConnectedServices().toString());

        if (isUp) return res;
        else return (-1) * res;
    }
}
