package jsp.com.netcracker.students.o3.model.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "orders")
@XmlType(name = "order")
@XmlRootElement
public class OrderImpl implements Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name = "last_id",
            sequenceName = "last_id")
    @Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;


    @JsonIgnore
    @ManyToOne(targetEntity = TemplateImpl.class)
    @JoinColumn(name = "templateid")
    private Template template;

    @JsonIgnore
    @OneToOne(targetEntity = ServiceImpl.class)
    @JoinColumn(name = "serviceid")
    private Service service;

    @ManyToOne(targetEntity = EmployeeImpl.class)
    @JoinColumn(name = "employeeid")
    private Employee employee;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "orderaction")
    private OrderAction action;

    @Column(name = "creationdate")
    private Date creationDate;

    public OrderImpl()
    {
    }

    public OrderImpl(final BigInteger id, final Template template, final Service service,
            final OrderStatus status, final OrderAction action)
    {
        this.id = id;
        this.template = template;
        this.service = service;
        this.status = status;
        this.action = action;
        this.creationDate = new Date();

    }


    @Override
    public String toString()
    {
        BigInteger employeeId = null;
        if (employee != null)
        {
            employeeId = employee.getId();
        }
        return  "{" + "</br>" +
                "        id:" + id + ",</br>" +
                "        serviceId:" + addUrl(service.getId()) + ",</br>" +
                "        templateId:" + addUrl(template.getId()) + ",</br>" +
                "        employeeId:" + addUrl(employeeId) + ",</br>" +
                "        status:" + status + ",</br>" +
                "        action:" + action + ",</br>" +
                "        creationDate:" + creationDate + "</br>" +
                "     }";
    }


    public BigInteger getId()
    {
        return id;
    }

    public void setId(final BigInteger id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return "Order";
    }

    public Template getTemplate()
    {
        return template;
    }

    public void setTemplate(final Template template)
    {
        this.template = template;
    }

    public Service getService()
    {
        return service;
    }

    public void setService(final Service service)
    {
        this.service = service;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(final Employee employee)
    {
        this.employee = employee;
    }

    public OrderStatus getStatus()
    {
        return status;
    }

    public void setStatus(final OrderStatus status)
    {
        this.status = status;
    }

    public OrderAction getAction()
    {
        return action;
    }

    public void setAction(final OrderAction action)
    {
        this.action = action;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate)
    {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final OrderImpl order = (OrderImpl) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    private String addUrl(BigInteger value)
    {
        String start = "<a href='http://localhost:8080/JSONVisual.jsp?entityId=";
        String mid = "' target=\"_blank\">";
        String close = "</a>";

        return start + value + mid + value + close;
    }
}
