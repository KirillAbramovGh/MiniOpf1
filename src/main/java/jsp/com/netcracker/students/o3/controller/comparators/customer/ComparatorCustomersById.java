package jsp.com.netcracker.students.o3.controller.comparators.customer;

import jsp.com.netcracker.students.o3.model.users.Customer;

import java.util.Comparator;

public class ComparatorCustomersById implements Comparator<Customer> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorCustomersById(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Customer o1, final Customer o2) {
        int res = o1.getId().compareTo(o2.getId());

        if (isUp) return res;
        else return (-1) * res;
    }
}
