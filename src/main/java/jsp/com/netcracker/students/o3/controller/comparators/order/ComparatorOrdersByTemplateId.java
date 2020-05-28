package jsp.com.netcracker.students.o3.controller.comparators.order;

import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;

public class ComparatorOrdersByTemplateId implements Comparator<Order> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorOrdersByTemplateId(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Order o1, final Order o2) {
        int res = o1.getTemplate().getId().compareTo(o2.getTemplate().getId());

        if (isUp) return res;
        else return (-1) * res;
    }
}
