package jsp.com.netcracker.students.o3.model.serialization.log;


import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XMLRequest
{
    private Date date;
    private String query;

    public XMLRequest(String q)
    {
        date = new Date();
        query = q;
    }


    public Date getDate()
    {
        return date;
    }

    public void setDate(final Date date)
    {
        this.date = date;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(final String query)
    {
        this.query = query;
    }

    public XMLRequest()
    {
    }

}
