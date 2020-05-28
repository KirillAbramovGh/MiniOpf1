package jsp.com.netcracker.students.o3.model.dao;

import jsp.com.netcracker.students.o3.model.Entity;
import jsp.com.netcracker.students.o3.model.serialization.log.XMLLogController;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractJdbcDao<T extends Entity> implements Dao<T>
{
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/entities";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    protected XMLLogController xmlLogController = XMLLogController.getInstance();


    public abstract List<T> getAll() throws SQLException;

    public abstract T getEntity(BigInteger id) throws SQLException;

    public abstract void update(T entity) throws SQLException;

    protected abstract String getTableName();

    public void delete(BigInteger id) throws SQLException
    {
        String sqlReq = "delete from " + getTableName() + " where id="+id;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sqlReq);
        }

    }

    public abstract void create(T entity) throws SQLException;

    protected Connection getConnection() throws SQLException
    {
        try
                {
                    Class.forName(DRIVER_NAME);
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);

         // return ConnectionPool.getInstance().getConnection();
    }
}
