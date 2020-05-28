package jsp.builders;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.Collection;

public class HtmlCheckboxBuilder
{
    private static HtmlCheckboxBuilder instance;

    public static HtmlCheckboxBuilder getInstance(){
        if(instance == null){
            instance = new HtmlCheckboxBuilder();
        }
        return instance;
    }



    public String makeCheckboxesFromAreas(Collection<Area> areas){
        StringBuilder res = new StringBuilder();
        for(Area area : areas){
            res.append(makeCheckboxFromArea(area));
        }
        return res.toString();
    }

    public String makeCheckboxesFromTemplates(Collection<Template> templates){
        StringBuilder res = new StringBuilder();
        for(Template template : templates){
            res.append(makeCheckboxFromTemplate(template));
        }
        return res.toString();
    }

    public String makeCheckboxesFromServices(Collection<Service> services){
        StringBuilder res = new StringBuilder();
        for(Service service : services){
            res.append(makeCheckboxFromService(service));
        }
        return res.toString();
    }

    public String makeCheckboxesFromCustomers(Collection<Customer> customers){
        StringBuilder res = new StringBuilder();
        for(Customer customer : customers){
            res.append(makeCheckboxFromCustomer(customer));
        }
        return res.toString();
    }

    public String makeCheckboxesFromEmployees(Collection<Employee> employees){
        StringBuilder res = new StringBuilder();
        for(Employee employee : employees){
            res.append(makeCheckboxFromEmployee(employee));
        }
        return res.toString();
    }

    public String makeCheckboxesFromOrders(Collection<Order> orders){
        StringBuilder res = new StringBuilder();
        for(Order order : orders){
            res.append(makeCheckboxFromOrder(order));
        }
        return res.toString();
    }




    private String makeCheckboxFromArea(Area area){
        return makeCheckbox(area.getId()+"",area.getId()+"",area.getName()+"("+area.getId()+")");
    }

    private String makeCheckboxFromTemplate(Template template){
        return makeCheckbox(template.getId()+"",template.getId()+"",template.getName()+"("+template.getId()+")");
    }

    private String makeCheckboxFromService(Service service){
        return makeCheckbox(service.getId()+"",service.getId()+"",service.getId()+"");
    }

    private String makeCheckboxFromOrder(Order order){
        return makeCheckbox(order.getId()+"",order.getId()+"",order.getId()+"");
    }

    private String makeCheckboxFromCustomer(Customer customer){
        return makeCheckbox(customer.getId()+"",customer.getId()+"",customer.getName()+"("+customer.getId()+")");
    }

    private String makeCheckboxFromEmployee(Employee employee){
        return makeCheckbox(employee.getId()+"",employee.getId()+"",employee.getName()+"("+employee.getId()+")");
    }



    private String makeCheckbox(String name, String value, String visibleName){
        String result = "<input type=\"checkbox\" name=\""+name+"\" value=\""+value+"\">";
        result+=visibleName+"</br>";
        return result;
    }
}
