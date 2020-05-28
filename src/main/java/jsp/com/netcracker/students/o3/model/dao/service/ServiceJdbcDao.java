package jsp.com.netcracker.students.o3.model.dao.service;

import jsp.com.netcracker.students.o3.model.dao.AbstractJdbcDao;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceJdbcDao extends AbstractJdbcDao<Service> implements ServiceDao
{
    private static final String tableName = "services";

    @Override
    public List<Service> getAll() throws SQLException
    {
        String sqlReq = "select * from " + getTableName();
        List<Service> services = getServices(sqlReq);
        for (Service entity : services)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return services;
    }

    @Override
    public Service getEntity(final BigInteger id) throws SQLException
    {
        Service service = null;
        String sqlReq = "select * from " + getTableName() + " where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, id.longValue());
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    service = getServiceFromResultSet(resultSet);
                }
            }
        }

        xmlLogController.addRequest(sqlReq);
        return service;
    }

    @Override
    public void update(final Service entity) throws SQLException
    {
        System.out.println("service update");
        String sqlReq =
                "update " + getTableName() + " set userid=?, templateid=?, status=?, activationdate=? where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
//            statement.setLong(1, entity.getCustomer().longValue());
//            statement.setLong(2, entity.getTemplate().longValue());
//            statement.setString(3, entity.getStatus() + "");
            statement.setDate(4, new java.sql.Date(entity.getActivationDate().getTime()));
            statement.setLong(5, entity.getId().longValue());

            statement.executeUpdate();
        }

    }

    @Override
    public void delete(final Service entity) throws SQLException
    {

    }

    @Override
    protected String getTableName()
    {
        return tableName;
    }

    @Override
    public void create(final Service entity) throws SQLException
    {
        String sqlReq = "INSERT INTO " + getTableName() + " VALUES (?,?,?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, entity.getId().longValue());
            statement.setLong(2, entity.getCustomer().getId().longValue());
            statement.setLong(3, entity.getTemplate().getId().longValue());
            statement.setString(4, entity.getStatus() + "");
            statement.setDate(5, new java.sql.Date(entity.
                    getActivationDate().
                    getTime()));

            statement.executeUpdate();
        }

    }

    public List<Service> getServicesByCustomer(Customer customer) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where userid=" + customer;
        List<Service> services = getServices(sqlReq);

        return services;
    }

    public List<Service> getServicesByTemplate(Template template) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where templateid=" + template;
        List<Service> services = getServices(sqlReq);

        return services;
    }

    public List<Service> getServicesByStatus(ServiceStatus status) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where status='" + status + "'";
        List<Service> services = getServices(sqlReq);

        return services;
    }

    public List<Service> getServicesByStatusAndCustomerId(BigInteger userId, ServiceStatus status) throws SQLException
    {
        String sqlReq = "select * from " + getTableName() + " where status='" + status + "' and userid=" + userId;
        List<Service> services = getServices(sqlReq);

        return services;
    }

    private List<Service> getServices(String sqlReq) throws SQLException
    {
        List<Service> services = new ArrayList<>();

        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {

                for (Service service; resultSet.next(); )
                {
                    service = getServiceFromResultSet(resultSet);
                    if (service != null)
                    {
                        services.add(service);
                    }
                }
            }
        }


            xmlLogController.addRequest(sqlReq);
        return services;
    }

    private Service getServiceFromResultSet(ResultSet set) throws SQLException
    {
        Service service;
        if (set.getString("status") == null)
        {
            service = null;
        }
        else
        {
            service = new ServiceImpl();
            service.setId(set.getBigDecimal("id").toBigInteger());
//            service.setCustomer(BigInteger.valueOf(set.getLong("userid")));
//            service.setTemplate(BigInteger.valueOf(set.getLong("templateid")));
            service.setStatus(ServiceStatus.valueOf(set.getString("status")));
            service.setActivationDate(new Date(set.getDate("activationdate").getTime()));
        }
        return service;
    }
}
