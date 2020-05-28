package jsp.com.netcracker.students.o3.model.dao.template;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.dao.AbstractJdbcDao;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TemplateJdbcDao extends AbstractJdbcDao<Template> implements TemplateDao
{
    private static final String tableName = "templates";
    private static final String templateAreaLinkTable = "template_area_link";

    @Override
    public List<Template> getAll() throws SQLException
    {

        List<Template> templates = new ArrayList<>();
        String sqlReq;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + getTableName();
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {
                ResultSet areaSet;
                String areaReq = "select * from " + templateAreaLinkTable + " where templateid=";


                for (Template template; resultSet.next(); )
                {
                    template = new TemplateImpl();
                    template.setId(BigInteger.valueOf(resultSet.getInt("id")));
                    template.setName(resultSet.getString("template_name"));
                    template.setCost(resultSet.getBigDecimal("cost"));
                    template.setDescription(resultSet.getString("description"));

                    try (Statement areaStatement = connection.createStatement())
                    {
                        areaSet = areaStatement.executeQuery(areaReq + template.getId());

                        List<BigInteger> areaIds = new ArrayList<>();
                        for (; areaSet.next(); )
                        {
                            areaIds.add(areaSet.getBigDecimal("areaid").toBigInteger());
                        }
                       // template.setPossibleAreas(areaIds);

                        templates.add(template);
                        areaSet.close();
                    }
                }
            }
        }


            xmlLogController.addRequest(sqlReq);
        return templates;
    }

    @Override
    public Template getEntity(final BigInteger id) throws SQLException
    {
        String sqlReq;
        Template template;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + getTableName() + " where id=" + id;
            String areaReq = "select * from " + templateAreaLinkTable + " where templateid=";

            template = getTemplateFromResultSet(statement, sqlReq, areaReq + id);
        }
        xmlLogController.addRequest(sqlReq);
        return template;
    }

    @Override
    public void update(final Template entity) throws SQLException
    {
        String sqlReq =
                "update " + getTableName() + " set template_name=?, cost=?, description=? where id=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getCost());
            statement.setString(3, entity.getDescription());
            statement.setLong(4, entity.getId().longValue());

            statement.executeUpdate();
        }
        sqlReq = "delete from template_area_link where templateid=?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            statement.setLong(1, entity.getId().longValue());
            statement.executeUpdate();
        }

        sqlReq = "insert into template_area_link values (?,?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sqlReq))
        {
            for (Area areaId : entity.getPossibleAreas())
            {
                statement.setLong(1, entity.getId().longValue());
                statement.setLong(2, areaId.getId().longValue());
                statement.executeUpdate();
            }
        }

    }

    @Override
    protected String getTableName()
    {
        return tableName;
    }

    @Override
    public void delete(final BigInteger id) throws SQLException
    {
        System.out.println("TemplateJdbcDao.delete(" + id + ") ");

        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            String areasReq = "delete from template_area_link where templateid=" + id;
            statement.executeUpdate(areasReq);
            String sqlReq = "delete from " + getTableName() + " where id=" + id;
            statement.executeUpdate(sqlReq);
        }
    }

    @Override
    public void delete(final Template entity) throws SQLException
    {

    }

    @Override
    public void create(final Template entity) throws SQLException
    {
        System.out.println("template create");
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            String values = entity.getId() + ",'" + entity.getName() + "'," + entity.getCost() + ",'" +
                    entity.getDescription() + "'";
            String sqlReq = "INSERT INTO " + getTableName() + " VALUES (" + values + ")";
            System.out.println(sqlReq);

            statement.executeUpdate(sqlReq);

//            for (BigInteger areaId : entity.getPossibleAreas())
//            {
//                String templateAreaLink =
//                        "INSERT INTO " + "template_area_link" + " VALUES (" + entity.getId() + ", " + areaId + ")";
//                statement.executeUpdate(templateAreaLink);
//            }
        }

    }

    public Template getTemplateByName(String templateName) throws SQLException
    {
        String sqlReq;
        Template template = null;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + getTableName() + " where template_name='" + templateName + "'";
            String areaReq = "select * from " + templateAreaLinkTable + " where templateid=";
            ResultSet resultSet = statement.executeQuery(sqlReq);
            ResultSet areaSet;

            if (resultSet.next())
            {
                template = new TemplateImpl();
                template.setId(resultSet.getBigDecimal("id").toBigInteger());
                template.setName(resultSet.getString("template_name"));
                template.setCost(resultSet.getBigDecimal("cost"));
                template.setDescription(resultSet.getString("description"));

                areaSet = statement.executeQuery(areaReq + template.getId());

                List<BigInteger> areaIds = new ArrayList<>();
                for (; areaSet.next(); )
                {
                    areaIds.add(areaSet.getBigDecimal("areaid").toBigInteger());
                }
              //  template.setPossibleAreas(areaIds);
                areaSet.close();
            }
            resultSet.close();
        }

        xmlLogController.addRequest(sqlReq);
        return template;
    }

    public List<Template> getTemplatesByArea(Area area) throws SQLException
    {
        List<Template> templates = new ArrayList<>();
        String sqlReq;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            sqlReq = "select * from " + templateAreaLinkTable + " where areaid=" + area;
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {
                List<BigInteger> templateIds = new ArrayList<>();
                for (; resultSet.next(); )
                {
                    templateIds.add(resultSet.getBigDecimal("templateid").toBigInteger());
                }
                for (BigInteger id : templateIds)
                {
                    templates.add(getEntity(id));
                }
            }
        }

        for (Template entity : templates)
        {
            xmlLogController.addRequest(sqlReq);
        }
        return templates;
    }

    private Template getTemplateFromResultSet(Statement statement, String temSql, String areaSql) throws SQLException
    {
        ResultSet resultSet = statement.executeQuery(temSql);
        if (!resultSet.next() || resultSet.getString("template_name") == null)
        {
            return null;
        }
        Template template = new TemplateImpl();
        template.setId(resultSet.getBigDecimal("id").toBigInteger());
        template.setName(resultSet.getString("template_name"));
        template.setCost(resultSet.getBigDecimal("cost"));
        template.setDescription(resultSet.getString("description"));

        ResultSet areaSet = statement.executeQuery(areaSql);
        List<BigInteger> areaIds = new ArrayList<>();
        for (; areaSet.next(); )
        {
            areaIds.add(areaSet.getBigDecimal("areaid").toBigInteger());
        }
     //   template.setPossibleAreas(areaIds);
        resultSet.close();
        areaSet.close();
        return template;
    }

    private List<Template> getTemplates(String sqlReq) throws SQLException
    {


        List<Template> templates = new ArrayList<>();


        try (Connection connection = getConnection(); Statement statement = connection.createStatement())
        {
            try (ResultSet resultSet = statement.executeQuery(sqlReq))
            {

                String areaReq = "select * from " + templateAreaLinkTable + " where templateid=";


                for (Template template; resultSet.next(); )
                {
                    template = new TemplateImpl();
                    template.setId(BigInteger.valueOf(resultSet.getInt("id")));
                    template.setName(resultSet.getString("template_name"));
                    template.setCost(resultSet.getBigDecimal("cost"));
                    template.setDescription(resultSet.getString("description"));

                    try (ResultSet areaSet = getConnection().createStatement().executeQuery(areaReq + template.getId()))
                    {

                        List<BigInteger> areaIds = new ArrayList<>();
                        for (BigInteger areaId; areaSet.next(); )
                        {
                            areaIds.add(areaSet.getBigDecimal("areaid").toBigInteger());
                        }
              //          template.setPossibleAreas(areaIds);

                        templates.add(template);
                    }
                }
            }
        }


        return templates;
    }
}
