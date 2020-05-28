package jsp.com.netcracker.students.o3.model.dao.template;

import jsp.com.netcracker.students.o3.model.dao.AbstractHibDao;
import jsp.com.netcracker.students.o3.model.hibernate.HibernateSessionFactoryUtil;
import jsp.com.netcracker.students.o3.model.serialization.log.XMLLogController;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class TemplateHibDao extends AbstractHibDao<Template> implements TemplateDao
{
    XMLLogController logController = XMLLogController.getInstance();
    @Override
    public Template getTemplateByName(final String templateName)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.
                createQuery("from TemplateImpl where name=:name");
        query.setParameter("name", templateName);
        Template template = (Template) query.uniqueResult();
        tx1.commit();
        session.close();
        logController.addRequest("from TemplateImpl where name=:"+templateName);
        return template;
    }


    @Override
    public List<Template> getAll()
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Template> templates =
                (List<Template>) session
                        .createQuery("From TemplateImpl  ")
                        .list();
        tx1.commit();
        session.close();
        logController.addRequest("from TemplateImpl");
        return templates;
    }

    @Override
    public Template getEntity(final BigInteger id)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Template template = session.get(TemplateImpl.class, id);
        tx1.commit();
        session.close();
        logController.addRequest("from TemplateImpl where id=:"+id);
        return template;
    }
}
