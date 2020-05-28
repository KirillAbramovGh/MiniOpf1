package jsp.com.netcracker.students.o3.model.dao.area;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

//DON't run tests if you don't want to lose all the data
public class AreaHibDaoTest
{
    Area leninDistrict;
    Area industrialDistrict;
    Area sovietDistrict;
    AreaDao areaDao;

    //DON't run tests if you don't want to lose all the data
    @Before
    public void before() throws SQLException
    {
        areaDao = new AreaHibDao();

        leninDistrict = new AreaImpl(BigInteger.ONE,"leninDistrict","Central");
        industrialDistrict = new AreaImpl(BigInteger.TWO,"industrialDistrict","Work district");
        sovietDistrict = new AreaImpl(BigInteger.TEN,"sovietDistrict","I remember it was cool");

        List<Area> areas = areaDao.getAll();
        for(Area area : areas){
            areaDao.delete(area.getId());
        }

    }

    @After
    public void after() throws SQLException
    {
        areaDao.delete(leninDistrict.getId());
        areaDao.delete(sovietDistrict.getId());
        areaDao.delete(industrialDistrict.getId());
    }

    //DON't run tests if you don't want to lose all the data
    @Test
    public void createAreasAndGetAllAndDelete() throws SQLException
    {
        //Create
        areaDao.create(leninDistrict);
        areaDao.create(industrialDistrict);
        areaDao.create(sovietDistrict);

        //GetAll
        List<Area> areas = areaDao.getAll();

        //Check Create and GetAll
        assertTrue(areas.contains(leninDistrict));
        assertTrue(areas.contains(industrialDistrict));
        assertTrue(areas.contains(sovietDistrict));

        //Get
        assertEquals(areaDao.getEntity(leninDistrict.getId()),leninDistrict);
        assertEquals(areaDao.getEntity(sovietDistrict.getId()),sovietDistrict);
        assertEquals(areaDao.getEntity(industrialDistrict.getId()),industrialDistrict);

        //Delete
        areaDao.delete(leninDistrict.getId());
        areaDao.delete(sovietDistrict.getId());
        areaDao.delete(industrialDistrict.getId());

        //Check Delete
        areas = areaDao.getAll();

        assertEquals(areas.size(),0);
    }

    @Test
    public void getAreaByName() throws SQLException
    {
        //Create
        areaDao.create(leninDistrict);
        areaDao.create(industrialDistrict);
        areaDao.create(sovietDistrict);


        assertEquals(leninDistrict,areaDao.getAreaByName(leninDistrict.getName()));
    }





}