package jsp.com.netcracker.students.o3.model.area;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "areas")
@XmlType(name = "area")
@XmlRootElement
public class AreaImpl implements Area {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "last_id")
    @SequenceGenerator(name="last_id",
            sequenceName="last_id")
    @Column(name = "id", updatable = false, nullable = false)
    private BigInteger id;
    @Column(name = "area_name")
    private String name;
    @Column(name = "description")
    private String description;


    @JsonIgnore
    @ManyToMany(targetEntity = TemplateImpl.class,fetch = FetchType.EAGER)
    @JoinTable(
            name="template_area_link",
            joinColumns=@JoinColumn(name="areaid"),
            inverseJoinColumns=@JoinColumn(name="templateid")
    )
    List<Template> templates;

    public AreaImpl() {
    }

    public AreaImpl(final BigInteger id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public List<Template> getTemplates()
    {
        return templates;
    }

    @Override
    public void setTemplates(final List<Template> templates)
    {
        this.templates = templates;
    }

    @Override
    public String toString()
    {
        String tmpls = "[";
        int i = 0;
        for(Template template : templates){
            if(i!=templates.size()-1)
            {
                tmpls += addUrl(template.getId()) + ",";
            }else {
                tmpls += addUrl(template.getId());
            }
            i++;
        }
        tmpls+="]";
        return  "{" + "</br>"+
                "      id:" + id + ",</br>"+
                "      name:'" + name + '\'' + ",</br>"+
                "      description:'" + description + '\'' +  ",</br>"+
                "      templates:" + tmpls + "</br>"+
                "    }";
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
        final AreaImpl area = (AreaImpl) o;
        return Objects.equals(id, area.id);
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
