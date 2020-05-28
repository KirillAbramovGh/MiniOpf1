package jsp.com.netcracker.students.o3.model.dao.area;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.dao.AbstractJdbcDao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AreaJdbcDao extends AbstractJdbcDao<Area> implements AreaDao
{
    private static final String tableName = "areas";

    @Override
    public List<Area> getAll() throws SQLException
    {
        List<Area> areas = new ArrayList<>();

        String sqlReq;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + getTableName();
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {

                for (Area area; resultSet.next(); )
                {
                    area = new AreaImpl();
                    area.setId(BigInteger.valueOf(resultSet.getInt("id")));
                    area.setName(resultSet.getString("area_name"));
                    area.setDescription(resultSet.getString("description"));

                    if (area.getName() == null)
                    {
                        continue;
                    }

                    areas.add(area);
                }
            }
        }
        for(Area area : areas){
            xmlLogController.addRequest(sqlReq);
        }
        return areas;
    }

    @Override
    public Area getEntity(final BigInteger id) throws SQLException
    {
        Area area = new AreaImpl();
        String sqlReq = "select * from " + getTableName() + " where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, id.longValue());
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                {
                    area.setId(id);
                    area.setName(resultSet.getString("area_name"));
                    area.setDescription(resultSet.getString("description"));
                    if (area.getName() == null)
                    {
                        return null;
                    }
                }
            }
        }
        xmlLogController.addRequest(sqlReq);
        return area;
    }

    @Override
    public void update(final Area entity) throws SQLException
    {
        System.out.println("area update");
        String sqlReq =
                "update " + getTableName() + " set area_name=?, description=? where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getDescription());
            statement.setLong(3,entity.getId().longValue());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(final Area entity) throws SQLException
    {

    }

    @Override
    protected String getTableName()
    {
        return tableName;
    }

    @Override
    public void create(final Area entity) throws SQLException
    {
        String sqlReq = "INSERT INTO " + getTableName() + " VALUES (?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlReq))
        {
            preparedStatement.setLong(1, entity.getId().longValue());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.executeUpdate();
        }
    }

    public Area getAreaByName(String areaName) throws SQLException
    {
        Area area = new AreaImpl();
        String sqlReq;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + getTableName() + " where area_name='" + areaName + "'";
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {
                if (resultSet.next())
                {
                    area.setId(resultSet.getBigDecimal("id").toBigInteger());
                    area.setName(resultSet.getString("name"));
                    area.setDescription(resultSet.getString("description"));
                    if (area.getName() == null)
                    {
                        return null;
                    }
                }
            }
        }
        xmlLogController.addRequest(sqlReq);
        return area;
    }
}
