package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.model.area.Area;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class SearcherUtil
{
    private static SearcherUtil instance;

    public static SearcherUtil getInstance()
    {
        if (instance == null)
        {
            instance = new SearcherUtil();
        }
        return instance;
    }

    public boolean checkRegExp(String regExp, String input)
    {
        try
        {
            return Pattern.matches(regExp, input);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public BigDecimal parseBigDecimal(String value)
    {
        try
        {
            double doubleValue = Double.parseDouble(value);
            return BigDecimal.valueOf(doubleValue);
        }
        catch (NumberFormatException e)
        {
            return BigDecimal.ZERO;
        }
    }

    public boolean isCostInDiapason(BigDecimal cost, String value, double diapason)
    {
        return Math.abs(cost.doubleValue() - parseBigDecimal(value).doubleValue()) < Math.abs(diapason);
    }

    public boolean checkArea(String search, Area area)
    {
        String areaName = area.getName();
        return area.getId().toString().equals(search) ||
                checkRegExp(search, area.getId().toString()) ||
                areaName.contains(search) ||
                checkRegExp(search, areaName);
    }
}
