package jsp.com.netcracker.students.o3.model.hibernate;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil
{
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil()
    {
    }

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null)
        {
            try
            {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Area.class);
                configuration.addAnnotatedClass(AreaImpl.class);
                configuration.addAnnotatedClass(Template.class);
                configuration.addAnnotatedClass(TemplateImpl.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(CustomerImpl.class);
                configuration.addAnnotatedClass(Service.class);
                configuration.addAnnotatedClass(ServiceImpl.class);
                configuration.addAnnotatedClass(EmployeeImpl.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(OrderImpl.class);
                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}