package jsp.com.netcracker.students.o3.model.orders;

/**
 * class of order status
 */
public enum OrderStatus
{
    /**
     * don't has employee
     */
    Entering,
    /**
     * employee take order to tasks
     */
    Processing,
    /**
     * employee stop executing order
     */
    Suspended,
    /**
     * employee complete order
     */
    Completed
}
