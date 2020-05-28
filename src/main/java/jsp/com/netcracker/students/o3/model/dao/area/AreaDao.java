package jsp.com.netcracker.students.o3.model.dao.area;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.dao.Dao;

import java.sql.SQLException;

public interface AreaDao extends Dao<Area>
{
    Area getAreaByName(String areaName) throws SQLException;

}
