package jsp.com.netcracker.students.o3.model.dao.order;

import jsp.com.netcracker.students.o3.model.dao.AbstractJdbcDao;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderJdbcDao extends AbstractJdbcDao<Order> implements OrderDao
{
    private static final String tableName = "orders";

    @Override
    public List<Order> getAll() throws SQLException
    {
        String sqlReq = "select * from " + getTableName();
        List<Order> orders = getOrders(sqlReq);

        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }

        return orders;
    }

    @Override
    public Order getEntity(final BigInteger id) throws SQLException
    {
        Order order = null;
        String sqlReq = "select * from " + getTableName() + " where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, id.longValue());
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    order = getOrderFromResultSet(resultSet);
                }
            }
        }

        xmlLogController.addRequest(sqlReq);

        return order;
    }

    @Override
    public void update(final Order entity) throws SQLException
    {
        String sqlReq =
                "update " + getTableName() +
                        " set templateid=?, serviceid=?, status=?, creationdate=?,employeeid=?, orderaction=? where " +
                        "id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, entity.getTemplate().getId().longValue());
            statement.setLong(2, entity.getService().getId().longValue());
            statement.setString(3, entity.getStatus().toString());
            statement.setDate(4, new java.sql.Date(entity.getCreationDate().getTime()));
            if (entity.getEmployee() != null)
            {
                statement.setLong(5, entity.getEmployee().getId().longValue());
            }
            else
            {
                statement.setBigDecimal(5, null);
            }
            statement.setString(6, entity.getAction() + "");
            statement.setLong(7, entity.getId().longValue());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(final Order entity) throws SQLException
    {

    }

    @Override
    protected String getTableName()
    {
        return tableName;
    }

    @Override
    public void create(final Order entity) throws SQLException
    {
        System.out.println("order create");
        String sqlReq = "INSERT INTO " + getTableName() + " VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, entity.getId().longValue());
            statement.setLong(2, entity.getTemplate().getId().longValue());
            statement.setLong(3, entity.getService().getId().longValue());

            if (entity.getEmployee() != null)
            {
                statement.setLong(4, entity.getEmployee().getId().longValue());
            }
            else
            {
                statement.setBigDecimal(4, null);
            }
            statement.setString(5, entity.getStatus().toString());
            statement.setString(6, entity.getAction().toString());
            statement.setDate(7, new java.sql.Date(entity.getCreationDate().getTime()));


            statement.executeUpdate();
        }
    }


    public List<Order> getOrdersByTemplateId(final BigInteger templateId) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where templateid=" + templateId;
        List<Order> orders = getOrders(sqlReq);

        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return orders;
    }

    public List<Order> getOrdersByServiceId(final BigInteger serviceId) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where serviceid=" + serviceId;
        List<Order> orders = getOrders(sqlReq);
        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return orders;
    }

    public List<Order> getOrdersByEmployeeId(final BigInteger employeeId) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where employeeid=" + employeeId;
        List<Order> orders = getOrders(sqlReq);
        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return orders;
    }

    public List<Order> getOrdersByStatus(final OrderStatus status) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where status='" + status + "'";
        List<Order> orders = getOrders(sqlReq);
        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return orders;
    }

    public List<Order> getOrdersByAction(final OrderAction action) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where orderaction='" + action + "'";
        List<Order> orders = getOrders(sqlReq);
        for (Order entity : orders)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return orders;
    }

    private List<Order> getOrders(String sqlReq) throws SQLException
    {
        List<Order> orders;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {
                orders = new ArrayList<>();
                for (Order order; resultSet.next(); )
                {
                    order = getOrderFromResultSet(resultSet);
                    if (order != null)
                    {
                        orders.add(order);
                    }
                }
            }
        }


        return orders;
    }

    private Order getOrderFromResultSet(ResultSet set) throws SQLException
    {
        Order order;
        if (set.getBigDecimal("id") == null)
        {
            order = null;
        }
        else
        {
            order = new OrderImpl();
            order.setId(set.getBigDecimal("id").toBigInteger());
//            order.setTemplate(BigInteger.valueOf(set.getLong("templateid")));
//            order.setService(BigInteger.valueOf(set.getLong("serviceid")));
//            order.setStatus(OrderStatus.valueOf(set.getString("status")));
//            order.setCreationDate(new Date(set.getDate("creationdate").getTime()));
//            order.setAction(OrderAction.valueOf(set.getString("orderaction")));
//            order.setEmployee(BigInteger.valueOf(set.getLong("employeeid")));
        }
        return order;
    }
}
