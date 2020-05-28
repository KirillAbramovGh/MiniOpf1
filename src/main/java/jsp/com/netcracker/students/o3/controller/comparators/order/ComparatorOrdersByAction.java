package jsp.com.netcracker.students.o3.controller.comparators.order;

import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;

public class ComparatorOrdersByAction implements Comparator<Order>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorOrdersByAction(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Order o1, final Order o2) {
        int res = o1.getAction().compareTo(o2.getAction());

        if (isUp) return res;
        else return (-1) * res;
    }
}
