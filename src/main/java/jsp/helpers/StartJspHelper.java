package jsp.helpers;

import jsp.com.netcracker.students.o3.model.area.Area;

import javax.servlet.http.HttpSession;

import jsp.builders.HtmlSelectBuilder;
import jsp.sessionBeans.StartSessionBean;

public class StartJspHelper
{

    private static StartJspHelper instance;

    private StartJspHelper()
    {
    }

    public static StartJspHelper getInstance()
    {
        if (instance == null)
        {
            instance = new StartJspHelper();
        }
        return instance;
    }

    public String showAreas(StartSessionBean startSessionBean)
    {
        HtmlSelectBuilder selectBuilder = new HtmlSelectBuilder();
        selectBuilder.setNameAttribute("area");

        for (Area area : startSessionBean.getAreas())
        {
            selectBuilder.addElement(area.getName());
        }

        return selectBuilder.built();
    }

    public String showErrorMessage(HttpSession session)
    {
        String error = (String) session.getAttribute("error");
        error = error == null ? "" : error;
        return "<h4 align='center'>" + error + "</h4>";
    }
}

