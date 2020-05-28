package jsp.com.netcracker.students.o3.model.templates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "templates")
@JsonDeserialize(as = TemplateImpl.class)
public interface Template extends jsp.com.netcracker.students.o3.model.Entity
{
    /**
     * @return Template id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name = "last_id",
            sequenceName = "last_id")
    @Column(name = "id", updatable = false, nullable = false)
    BigInteger getId();

    /**
     * set template id
     */
    void setId(final BigInteger id);

    /**
     * @return template name
     */
    @Column(name = "template_name")
    String getName();

    /**
     * set template name
     */
    void setName(final String name);

    /**
     * @return template cost per month
     */
    @Column(name = "cost")
    BigDecimal getCost();

    /**
     * set template cost per month
     */
    void setCost(final BigDecimal cost);

    /**
     * @return template description
     */
    @Column(name = "description")
    String getDescription();

    /**
     * set template description
     */
    void setDescription(final String description);

    /**
     * @return possible area id by connected services
     */
    @ManyToMany(targetEntity = AreaImpl.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "template_area_link",
            joinColumns = @JoinColumn(name = "templateid"),
            inverseJoinColumns = @JoinColumn(name = "areaid")
    )
    List<Area> getPossibleAreas();

    /**
     * set possible area id
     */
    void setPossibleAreas(final List<Area> possibleAreas);
}
