package jsp.com.netcracker.students.o3.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool
{
    private static ConnectionPool instance;
    private DataSource dataSource;

    private ConnectionPool(){
        try
        {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/entities");

        }catch (NamingException e){
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance(){
        if(instance == null){
            instance = new ConnectionPool();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException
    {
        System.out.println("get connection");
        return dataSource.getConnection();
    }

}

