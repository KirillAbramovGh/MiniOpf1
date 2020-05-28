package jsp.com.netcracker.students.o3.controller.comparators.order;

import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;

public class ComparatorOrdersByEmployeeId implements Comparator<Order> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorOrdersByEmployeeId(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Order o1, final Order o2) {
        int res = o1.getEmployee().getId().compareTo(o2.getEmployee().getId());

        if (isUp) return res;
        else return (-1) * res;
    }
}
