package jsp.com.netcracker.students.o3.model.services;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
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
@Table(name = "services")
@XmlType(name = "service")
@XmlRootElement
public class ServiceImpl implements Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name="last_id",
            sequenceName="last_id")
    @Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;

    @JsonIgnore
    @ManyToOne(targetEntity = CustomerImpl.class)
    @JoinColumn(name = "userid")
    private Customer customer;


    @ManyToOne(targetEntity = TemplateImpl.class)
    @JoinColumn(name = "templateid")
    private Template template;


    @OneToOne(optional = true,targetEntity = OrderImpl.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "orderid")
    private Order order;

    @Column(name = "status")
    private ServiceStatus status;

    @Column(name = "activationdate")
    private Date activationDate;


    @Override
    public String toString()
    {
        return  "{" + "</br>"+
                "       id:" + id + ",</br>"+
                "       userId:" + addUrl(customer.getId()) + ",</br>"+
                "       templateId:" + addUrl(template.getId()) + ",</br>"+
                "       status:" + status + ",</br>"+
                "       cost:" + templateGetCost() + ",</br>"+
                "       activationDate:" + activationDate + "</br>"+
                "      }";
    }



    public ServiceImpl()
    {
        this.activationDate = new Date();
    }

    public ServiceImpl(final BigInteger id, final Customer customer, final Template template,
            final ServiceStatus status)
    {
        this.id = id;
        this.customer = customer;
        this.template = template;
        this.status = status;
        this.activationDate = new Date();
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
        return template.getName();
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(final Customer customer)
    {
        this.customer = customer;
    }

    public Template getTemplate()
    {
        return template;
    }

    public void setTemplate(final Template template)
    {
        this.template = template;
    }

    @Override
    public Order getOrder()
    {
        return order;
    }

    @Override
    public void setOrder(final Order order)
    {
        this.order = order;
    }

    public ServiceStatus getStatus()
    {
        return status;
    }

    public void setStatus(final ServiceStatus status)
    {
        this.status = status;
    }

    @JsonIgnore
    public BigDecimal templateGetCost()
    {
        return template.getCost();
    }

    public Date getActivationDate()
    {
        return activationDate;
    }

    public void setActivationDate(final Date activationDate)
    {
        this.activationDate = activationDate;
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
        final ServiceImpl service = (ServiceImpl) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    private String addUrl(BigInteger value){
        String start = "<a href='http://localhost:8080/JSONVisual.jsp?entityId=";
        String mid = "' target=\"_blank\">";
        String close = "</a>";

        return start+value+mid+value+close;
    }
}
