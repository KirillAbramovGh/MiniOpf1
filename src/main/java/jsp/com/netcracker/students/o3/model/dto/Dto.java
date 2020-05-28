package jsp.com.netcracker.students.o3.model.dto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Dto
{
    private final BigInteger id;
    private final String type;
    private Map<String, Object> parameters;

    public Dto(final BigInteger id, final String type)
    {
        this.id = id;
        this.type = type;
        this.parameters = new HashMap<>();
    }


    public BigInteger getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public Map<String, Object> getParameters()
    {
        return parameters;
    }

    public void setParameters(final Map<String, Object> parameters)
    {
        this.parameters = parameters;
    }

    public void putParameter(String key, Object value)
    {
        parameters.put(key, value);
    }
}
