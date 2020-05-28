package jsp.com.netcracker.students.o3.model.dao;

import org.postgresql.util.PSQLException;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LastIdDao
{
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/entities";
    private static final String DRIVER_NAME = "org.postgresql.Driver";

    public BigInteger getNextId() throws SQLException
    {
        System.out.println("getNextId");
        BigInteger lastId = null;
        String sqlReq = "select nextval('lastid')";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sqlReq);

            if (resultSet.next())
            {
                lastId = resultSet.getBigDecimal("nextval").toBigInteger();
            }
            resultSet.close();
        }


        return lastId;
    }


    public void setLastId(final BigInteger id) throws SQLException
    {
        String sqlReq = "ALTER SEQUENCE serial RESTART WITH ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, id.longValue());
            statement.executeUpdate();
        }
    }


    public void createLastId(final BigInteger entity) throws SQLException
    {
        System.out.println("createLastId");
        String sqlReq = "create sequence lastid START " + entity;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sqlReq);
        }
    }

    public void deleteLastId() throws SQLException
    {
        System.out.println("deleteLastID");
        String sqlReq = "drop sequence lastid";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sqlReq);
        }
        catch (PSQLException e)
        {
            System.out.println("lastid is not exist");
        }
    }

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
        //return ConnectionPool.getInstance().getConnection();
    }
}
