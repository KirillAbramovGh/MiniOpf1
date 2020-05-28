package jsp.com.netcracker.students.o3.model.dto;

import java.util.Collection;
import java.util.Set;

public class HtmlParser
{
    public String parseToHtml(Dto dto)
    {
        StringBuilder res = new StringBuilder("{</br>");

        int i = 0;
        Set<String> keys = dto.getParameters().keySet();
        final int lastElementIndex = keys.size() - 1;
        for (String key : keys)
        {
            Object value = dto.getParameters().get(key);
            appendPair(key, value, res).append("</br>");
            if(i< lastElementIndex)
            {
                res.append(",");
            }
            i++;
        }
        res.append("}</br>");

        return res.toString();
    }

    private StringBuilder appendPair(String key, Object value, StringBuilder res)
    {
        res.append(key).append(":");
        appendObject(value, res);
        return res;
    }

    private void appendObject(Object value, StringBuilder res)
    {
        if (value instanceof Number)
        {
            res.append(value);
        }
        else if (value instanceof Collection)
        {
            appendIterable((Collection<Object>) value, res);
        }
        else
        {
            res.append('"').append(value).append('"');
        }
    }

    private void appendIterable(Collection<Object> value, StringBuilder res)
    {
        res.append("[");

        int i = 0;
        final int lastElementIndex = value.size() - 1;
        for (Object obj : value)
        {
            appendObject(obj, res);
            if(i<lastElementIndex){
                res.append(",");
            }
            i++;
        }

        res.append("]");
    }
}
