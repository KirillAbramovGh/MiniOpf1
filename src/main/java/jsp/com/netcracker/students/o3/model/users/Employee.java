package jsp.com.netcracker.students.o3.model.users;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
@JsonDeserialize(as = EmployeeImpl.class)
public interface Employee extends User
{
    @Column(name = "login")
    @Override
    String getLogin();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name="last_id",
            sequenceName="last_id")
    @Column(name = "id", updatable = false, nullable = false)
    @Override
    BigInteger getId();

    @Column(name = "name")
    @Override
    String getName();

    @Column(name = "password")
    @Override
    String getPassword();
}
