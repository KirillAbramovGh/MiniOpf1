package jsp.com.netcracker.students.o3;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.dao.area.AreaHibDao;
import jsp.com.netcracker.students.o3.model.dao.employee.EmployeeHibDao;
import jsp.com.netcracker.students.o3.model.dao.order.OrderHibDao;
import jsp.com.netcracker.students.o3.model.dao.service.ServiceHibDao;
import jsp.com.netcracker.students.o3.model.dao.template.TemplateHibDao;
import jsp.com.netcracker.students.o3.model.dao.сustomer.CustomerHibDao;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

public class Main
{
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, JAXBException
    {
       clearDB();
      downloadDataFromJSON();

    }

    private static void clearDB()
    {
        AreaHibDao areaHibDao = new AreaHibDao();
        TemplateHibDao templateHibDao = new TemplateHibDao();
        CustomerHibDao customerHibDao = new CustomerHibDao();
        EmployeeHibDao employeeHibDao = new EmployeeHibDao();
        ServiceHibDao serviceHibDao = new ServiceHibDao();
        OrderHibDao orderHibDao = new OrderHibDao();

        for (Order order : orderHibDao.getAll())
        {
            orderHibDao.delete(order);
        }
        for (Service service : serviceHibDao.getAll())
        {
            serviceHibDao.delete(service);
        }
        for (Customer customer : customerHibDao.getAll())
        {
            customerHibDao.delete(customer);
        }
        for (Area area : areaHibDao.getAll())
        {
            areaHibDao.delete(area);
        }

        for (Template template : templateHibDao.getAll())
        {
            templateHibDao.delete(template);
        }

        for (Employee employee : employeeHibDao.getAll())
        {
            employeeHibDao.delete(employee);
        }
    }

    public static void downloadDataFromJSON() throws SQLException, ClassNotFoundException, IOException
    {
        AreaHibDao areaHibDao = new AreaHibDao();
        TemplateHibDao templateHibDao = new TemplateHibDao();
        CustomerHibDao customerHibDao = new CustomerHibDao();
        EmployeeHibDao employeeHibDao = new EmployeeHibDao();

        Template template1 = new TemplateImpl(BigInteger.valueOf(1), "Internet2.0",
                BigDecimal.valueOf(500), "Internet 100Mb/s"
        );
        Template template2 = new TemplateImpl(BigInteger.valueOf(2), "Internet1.0",
                BigDecimal.valueOf(300), "Internet 50Mb/s"
        );
        Template template3 = new TemplateImpl(BigInteger.valueOf(3), "Tv100",
                BigDecimal.valueOf(200), "100 ch,2 TV"
        );
        Template template4 = new TemplateImpl(BigInteger.valueOf(4), "MobilePac2.0",
                BigDecimal.valueOf(650), "10g 500min 100sms"
        );
        Template template5 = new TemplateImpl(BigInteger.valueOf(5), "MobilePac1.0",
                BigDecimal.valueOf(400), "7g 400min"
        );
        Template template6 = new TemplateImpl(BigInteger.valueOf(6), "TV200",
                BigDecimal.valueOf(100), "200 ch,3 TV"
        );
        Template template7 = new TemplateImpl(BigInteger.valueOf(7), "FullFamily",
                BigDecimal.valueOf(1000), "100Mb/s+200 channels"
        );
        Template template8 = new TemplateImpl(BigInteger.valueOf(8), "TV300",
                BigDecimal.valueOf(500), "do not watch TV so much"
        );
        Template template9 = new TemplateImpl(BigInteger.valueOf(9), "MobilePacUnlimited",
                BigDecimal.valueOf(1000), "Unlimited"
        );
        Template template10 = new TemplateImpl(BigInteger.valueOf(10), "InternetMax",
                BigDecimal.valueOf(1200), "150Mb/s"
        );
        Template template11 = new TemplateImpl(BigInteger.valueOf(11), "OutOfFantasy",
                BigDecimal.valueOf(800), "100Mb/s + TV100"
        );
        Template template12 = new TemplateImpl(BigInteger.valueOf(12), "PhoneCall",
                BigDecimal.valueOf(200), "500min"
        );

        Area area13 = new AreaImpl(BigInteger.valueOf(13), "LeninDistrict",
                "Named by our grandfather Lenin");
        Area area14 = new AreaImpl(BigInteger.valueOf(14), "SamaraDistrict",
                "City center");
        Area area15 = new AreaImpl(BigInteger.valueOf(15), "ManufactureDistrict",
                "Peace Labor May");
        Area area16 = new AreaImpl(BigInteger.valueOf(16), "SovietDistrict",
                "parapapam");

        template1.setPossibleAreas(Arrays.asList(area13, area14, area15, area16));
        template12.setPossibleAreas(Arrays.asList(area13, area14, area16));
        template11.setPossibleAreas(Arrays.asList(area13, area15, area16));
        template10.setPossibleAreas(Arrays.asList(area13, area14, area15));
        template9.setPossibleAreas(Arrays.asList(area14, area15, area16));
        template8.setPossibleAreas(Arrays.asList(area14, area15, area16));
        template7.setPossibleAreas(Arrays.asList(area15));
        template6.setPossibleAreas(Arrays.asList(area14, area15));
        template5.setPossibleAreas(Arrays.asList(area16));
        template4.setPossibleAreas(Arrays.asList(area15, area16));
        template3.setPossibleAreas(Arrays.asList(area13, area14, area15));
        template2.setPossibleAreas(Arrays.asList(area14, area15));

        Customer customer = new CustomerImpl(BigInteger.valueOf(17),
                "kirill", "kirill", "1234", area13);
        customer.setMoneyBalance(BigDecimal.valueOf(900));

        Employee employee = new EmployeeImpl(BigInteger.valueOf(18),
                "ivan", "ivan", "1234");


        areaHibDao.create(area13);
        areaHibDao.create(area14);
        areaHibDao.create(area15);
        areaHibDao.create(area16);

        templateHibDao.create(template1);
        templateHibDao.create(template2);
        templateHibDao.create(template3);
        templateHibDao.create(template4);
        templateHibDao.create(template5);
        templateHibDao.create(template6);
        templateHibDao.create(template7);
        templateHibDao.create(template8);
        templateHibDao.create(template9);
        templateHibDao.create(template10);
        templateHibDao.create(template11);
        templateHibDao.create(template12);

        customerHibDao.create(customer);
        employeeHibDao.create(employee);
    }

}
