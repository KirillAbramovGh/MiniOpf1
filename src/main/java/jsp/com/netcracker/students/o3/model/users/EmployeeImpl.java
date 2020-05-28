package jsp.com.netcracker.students.o3.model.users;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "employees")
@XmlType(name = "employee")
@XmlRootElement
public class EmployeeImpl implements Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name="last_id",
            sequenceName="last_id")
    @Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;


    @Override
    public String toString()
    {
        return  "{" + "</br>"+
                "        id:" + id + ",</br>"+
                "        name:'" + name + '\'' + ",</br>"+
                "    }";
    }

    public EmployeeImpl(){}

    public EmployeeImpl(final BigInteger id, final String name, final String login, final String password)
    {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public BigInteger getId()
    {
        return id;
    }


    @Override
    public void setId(final BigInteger id)
    {
        this.id = id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(final String name)
    {
        if(name!=null && !name.replaceAll(" ","").isEmpty())
        {
            this.name = name;
        }
    }

    @Override
    public String getLogin()
    {
        return login;
    }

    @Override
    public void setLogin(final String login)
    {
        if(login!=null && !login.replaceAll(" ","").isEmpty())
        {
            this.login = login;
        }
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public void setPassword(final String password)
    {
        if(password!=null && !password.replaceAll(" ","").isEmpty())
        {
            this.password = password;
        }
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
        final EmployeeImpl employer = (EmployeeImpl) o;
        return Objects.equals(id, employer.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
