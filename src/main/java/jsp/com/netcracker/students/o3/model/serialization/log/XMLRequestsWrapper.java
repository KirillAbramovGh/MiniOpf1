package jsp.com.netcracker.students.o3.model.serialization.log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "requests")
public class XMLRequestsWrapper
{
    private List<XMLRequest> request;

    public XMLRequestsWrapper()
    {
        request = new ArrayList<>();
    }

    public void addRequests(XMLRequest... req)
    {
        request.addAll(Arrays.asList(req));
    }

    public int getNumOfRequests()
    {
        return request.size();
    }

    public List<XMLRequest> getRequest()
    {
        return request;
    }

    public void setRequest(final List<XMLRequest> request)
    {
        this.request = request;
    }
}
