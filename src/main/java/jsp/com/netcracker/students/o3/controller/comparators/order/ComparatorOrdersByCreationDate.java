package jsp.com.netcracker.students.o3.controller.comparators.order;

import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;

public class ComparatorOrdersByCreationDate implements Comparator<Order> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorOrdersByCreationDate(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Order o1, final Order o2) {
        int res = o1.getCreationDate().compareTo(o2.getCreationDate());

        if (isUp) return res;
        else return (-1) * res;
    }
}
