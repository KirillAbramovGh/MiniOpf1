package jsp.builders;

import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;

import java.util.Collection;

public class CardBuilder
{

    private static CardBuilder instance;
    private Controller cntrl = ControllerImpl.getInstance();

    private CardBuilder()
    {
    }

    public static CardBuilder getInstance()
    {
        if (instance == null)
        {
            instance = new CardBuilder();
        }

        return instance;
    }


    private String makeCardFromService(Service service)
    {
        String result = "";
        String start = "";

        result += "</br>"+"<l style=\"font-size: 15px;margin:3px\">" + cntrl.getServiceName(service.getId()) + "</l>"+"</br>";
        result +=  service.getStatus() +"</br>" + "<k style=\"font-size: 6px\">"+getServiceAreaName(service)+"</k>"+"</br>"
                + cntrl.getServiceDescription(service.getId())+"</br>"+service.templateGetCost() + "$"+"</br>";

        switch (service.getStatus())
        {
            case Suspended:
                start = "<div class='cardred'>";
                result += createButton("resume", "resume", service.getId().toString());
                result += createButton("disconnect", "disconnect", service.getId().toString());
                break;
            case Planned:
                start = "<div class='cardyellow'>";
                result += createButton("cancel", "disconnect", service.getId().toString());
                break;
            case Active:
                start = "<div class='card'>";
                result += createButton("disconnect", "disconnect", service.getId().toString());
                result += createButton("suspend", "suspend", service.getId().toString());
                break;
            case Provisioning:
                start = start = "<div class='card'>";
                break;
        }
        result += "</div>";
        return start+result;
    }

    private String makeCardFromTemplate(Template template)
    {
        String result = "";

        result += "<div class='card'>";
        result += "<h3>" + template.getName() + "</h3>";
        result += "<p>" + template.getDescription() + "</p>";
        result += "<p>" + template.getCost() + "$" + "</p>";
        result += createButton("connect", "connect", template.getId().toString());
        result += "</div>";

        return result;
    }


    private String createButton(String value, String name)
    {
        String buttonStart = "<input ";
        String buttonEnd = " />";
        return buttonStart + "type='" + "submit" + "' value='" + value + "' name='" + name + "' class='button' " +
                buttonEnd;
    }

    private String createButton(String buttonName, String command, String id)
    {
        return createButton(buttonName, command + " " + id);
    }

    public String makeCardsFromServices(Collection<Service> services)
    {
        StringBuilder result = new StringBuilder();
        for (Service service : services)
        {
            if(service.getStatus().equals(ServiceStatus.Planned))
            {
                result.append(makeCardFromService(service));
            }
        }
        for(Service service : services){
            if(service.getStatus().equals(ServiceStatus.Active))
            {
                result.append(makeCardFromService(service));
            }
        }
        for(Service service : services){
            if(service.getStatus().equals(ServiceStatus.Suspended))
            {
                result.append(makeCardFromService(service));
            }
        }
        for(Service service : services){
            if(service.getStatus().equals(ServiceStatus.Provisioning))
            {
                result.append(makeCardFromService(service));
            }
        }
        return result.toString();
    }

    public String makeCardsFromTemplates(Collection<Template> templates)
    {
        StringBuilder result = new StringBuilder();

        for (Template template : templates)
        {
            result.append(makeCardFromTemplate(template));
        }

        return result.toString();
    }

    private String getServiceAreaName(Service service){
        String res = "";
        for(Area area : service.getTemplate().getPossibleAreas()){
            res += area.getName()+",";
        }
        return res;
    }
}
