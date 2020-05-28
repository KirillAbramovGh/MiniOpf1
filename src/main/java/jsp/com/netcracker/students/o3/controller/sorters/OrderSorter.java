package jsp.com.netcracker.students.o3.controller.sorters;

import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByAction;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByCreationDate;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByEmployeeId;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersById;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByServiceId;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByStatus;
import jsp.com.netcracker.students.o3.controller.comparators.order.ComparatorOrdersByTemplateId;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.OrderSortType;
import jsp.com.netcracker.students.o3.model.orders.Order;

import java.util.Comparator;
import java.util.List;

public class OrderSorter {
    /**
     * comparator which compare templates
     */
    private static OrderSorter instance;

    private OrderSorter() {
    }

    /**
     * define type of sorting
     *
     * @param type
     */
    private Comparator<Order> defineSortType(OrderSortType type) {
        switch (type) {
            case DownById:
                return new ComparatorOrdersById(false);
            case UpById:
                return new ComparatorOrdersById(true);
            case UpByAction:
                return new ComparatorOrdersByAction(true);
            case UpByStatus:
                return new ComparatorOrdersByStatus(true);
            case DownByAction:
                return new ComparatorOrdersByAction(false);
            case DownByStatus:
                return new ComparatorOrdersByStatus(false);
            case UpByServiceId:
                return new ComparatorOrdersByServiceId(true);
            case UpByEmployeeId:
                return new ComparatorOrdersByEmployeeId(true);
            case UpByTemplateId:
                return new ComparatorOrdersByTemplateId(true);
            case DownByServiceId:
                return new ComparatorOrdersByServiceId(false);
            case DownByEmployeeId:
                return new ComparatorOrdersByEmployeeId(false);
            case DownByTemplateId:
                return new ComparatorOrdersByTemplateId(false);
            case UpByCreationDate:
                return new ComparatorOrdersByCreationDate(true);
            case DownByCreationDate:
                return new ComparatorOrdersByCreationDate(false);
        }
        return new ComparatorOrdersById(true);
    }

    /**
     * sort services
     *
     * @param services
     */
    public void sort(List<Order> services, OrderSortType type) {
        if (type != null) {
            Comparator<Order> templateComparator = defineSortType(type);
            services.sort(templateComparator);
        }
    }

    public static OrderSorter getInstance() {
        if (instance == null) {
            instance = new OrderSorter();
        }
        return instance;
    }
}
