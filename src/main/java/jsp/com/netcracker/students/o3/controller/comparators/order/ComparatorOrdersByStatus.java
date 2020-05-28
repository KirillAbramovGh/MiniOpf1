package jsp.com.netcracker.students.o3.controller.comparators.order;

import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;

public class ComparatorOrdersByStatus implements Comparator<Order>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorOrdersByStatus(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Order o1, final Order o2) {
        int res = o1.getStatus().compareTo(o2.getStatus());

        if (isUp) return res;
        else return (-1) * res;
    }
}
