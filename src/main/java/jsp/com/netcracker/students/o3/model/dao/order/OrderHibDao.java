package jsp.com.netcracker.students.o3.model.dao.order;

import jsp.com.netcracker.students.o3.model.dao.AbstractHibDao;
import jsp.com.netcracker.students.o3.model.hibernate.HibernateSessionFactoryUtil;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.serialization.log.XMLLogController;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class OrderHibDao extends AbstractHibDao<Order> implements OrderDao
{
    XMLLogController logController = XMLLogController.getInstance();
    @Override
    public List<Order> getOrdersByTemplateId(final BigInteger templateId)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from OrderImpl where template.id=:templateId");
        query.setParameter("templateId", templateId);
        List<Order> order = (List<Order>) query.getResultList();
        tx1.commit();
        session.close();
        logController.addRequest("from OrderImpl where template.id=:"+templateId);
        return order;
    }

    @Override
    public List<Order> getOrdersByServiceId(final BigInteger serviceId)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from OrderImpl where service.id=:serviceId");
        query.setParameter("serviceId", serviceId);
        List<Order> order = (List<Order>) query.getResultList();
        tx1.commit();
        session.close();
        logController.addRequest("from OrderImpl where service.id=:"+serviceId);
        return order;
    }

    @Override
    public List<Order> getOrdersByEmployeeId(final BigInteger employeeId)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from OrderImpl where employee.id=:employeeId");
        query.setParameter("employeeId", employeeId);
        List<Order> order = (List<Order>) query.getResultList();
        tx1.commit();
        session.close();
        logController.addRequest("from OrderImpl where employee.id=:"+employeeId);
        return order;
    }

    @Override
    public List<Order> getOrdersByStatus(final OrderStatus status)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from OrderImpl where status=:status");
        query.setParameter("status", status);
        List<Order> order = (List<Order>) query.getResultList();
        tx1.commit();
        session.close();
        logController.addRequest("from OrderImpl where status=:"+status);
        return order;
    }

    @Override
    public List<Order> getOrdersByAction(final OrderAction action)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from OrderImpl where action=:action");
        query.setParameter("action", action);
        List<Order> order = (List<Order>) query.getResultList();
        tx1.commit();
        session.close();
        logController.addRequest("from OrderImpl where action=:"+action);
        return order;
    }

    @Override
    public List<Order> getAll()
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orders =
                (List<Order>) session.createQuery("From OrderImpl ")
                        .list();
        transaction.commit();
        session.close();
        logController.addRequest("from OrderImpl ");
        return orders;
    }

    @Override
    public Order getEntity(final BigInteger id)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(OrderImpl.class, id);
        transaction.commit();
        session.close();
        logController.addRequest("from OrderImpl where id=:"+id);
        return order;
    }
}
