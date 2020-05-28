package jsp.com.netcracker.students.o3.model.users;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@Entity
@Table(name = "customers")
@XmlType(name = "customer")
@XmlRootElement
public class CustomerImpl implements Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name = "last_id",
            sequenceName = "last_id")
    @Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "moneybalance")
    private BigDecimal moneyBalance;

    @OneToMany(targetEntity = ServiceImpl.class, fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<Service> connectedServices;

    @ManyToOne(targetEntity = AreaImpl.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "areaid")
    private Area area;

    public CustomerImpl()
    {
        moneyBalance = BigDecimal.ZERO;
        connectedServices = new HashSet<>();
    }

    public CustomerImpl(final BigInteger id, final String name, final String login, final String password,
            final Area area)
    {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.area = area;
        connectedServices = new HashSet<>();
        moneyBalance = BigDecimal.ZERO;
    }


    @Override
    public String toString()
    {
        String tmpr = "[";
        int i = 0;
        for (Service service : connectedServices)
        {
            if (i != connectedServices.size() - 1)
            {
                tmpr += addUrl(service.getId()) + ",";
            }
            else
            {
                tmpr += addUrl(service.getId());
            }
            i++;
        }
        tmpr += "]";
        return  "{" + "</br>" +
                "           id:" + id + ",</br>" +
                "           name:'" + name + '\'' + ",</br>" +
                "           login:'" + login + '\'' + ",</br>" +
                "           password:'" + password + '\'' + ",</br>" +
                "           moneyBalance:" + moneyBalance + ",</br>" +
                "           connectedServicesIds:" + tmpr + ",</br>" +
                "           areaId:" + addUrl(area.getId()) + "</br>" +
                "      }";
    }

    private String addUrl(BigInteger value)
    {
        String start = "<a href='http://localhost:8080/JSONVisual.jsp?entityId=";
        String mid = "' target=\"_blank\">";
        String close = "</a>";

        return start + value + mid + value + close;
    }

    public BigDecimal getMoneyBalance()
    {
        return moneyBalance;
    }

    public void setMoneyBalance(final BigDecimal moneyBalance)
    {
        this.moneyBalance = moneyBalance;
    }

    public Set<Service> getConnectedServices()
    {
        return connectedServices;
    }

    public void setConnectedServices(final Set<Service> connectedServices)
    {
        this.connectedServices = connectedServices;
    }

    public Area getArea()
    {
        return area;
    }

    public void setArea(final Area area)
    {
        this.area = area;
    }

    @Override
    public void addConnectedService(Service service)
    {
        connectedServices.add(service);
    }

    public BigInteger getId()
    {
        return id;
    }

    public void setId(final BigInteger id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(final String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(final String password)
    {
        this.password = password;
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
        final CustomerImpl customer = (CustomerImpl) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
