package jsp.com.netcracker.students.o3.model.dao.сustomer;

import jsp.com.netcracker.students.o3.model.dao.AbstractHibDao;
import jsp.com.netcracker.students.o3.model.hibernate.HibernateSessionFactoryUtil;
import jsp.com.netcracker.students.o3.model.serialization.log.XMLLogController;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class CustomerHibDao extends AbstractHibDao<Customer> implements CustomerDao
{
    XMLLogController logController = XMLLogController.getInstance();
    @Override
    public Customer getCustomerByLogin(final String login)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from CustomerImpl where login=:login");
        query.setParameter("login", login);
        Customer customer = (Customer) query.uniqueResult();
        tx1.commit();
        session.close();
        logController.addRequest("from CustomerImpl where login=:"+login);
        return customer;
    }

    @Override
    public List<Customer> getAll()
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Customer> customers = session.
                createQuery("from CustomerImpl ").list();
        tx1.commit();
        session.close();
        logController.addRequest("from CustomerImpl");
        return customers;
    }

    @Override
    public Customer getEntity(final BigInteger id)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Customer customer = session.get(CustomerImpl.class, id);
        tx1.commit();
        session.close();
        logController.addRequest("from CustomerImpl where id=:"+id);
        return customer;
    }

}
