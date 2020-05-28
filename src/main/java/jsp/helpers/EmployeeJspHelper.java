package jsp.helpers;

import jsp.com.netcracker.students.o3.controller.sorters.SortType.AreaSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.CustomerSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.EmployeeSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.OrderSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.TemplateSortType;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.dto.DtoTransformer;
import jsp.com.netcracker.students.o3.model.dto.HtmlParser;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.List;

import jsp.builders.HtmlCheckboxBuilder;
import jsp.builders.HtmlTableBuilder;
import jsp.sessionBeans.EmployeeSessionBean;

public class EmployeeJspHelper
{
    private static EmployeeJspHelper instance;

    private HtmlTableBuilder htmlTableBuilder;


    private EmployeeJspHelper()
    {
        htmlTableBuilder = HtmlTableBuilder.getInstance();
    }

    public static EmployeeJspHelper getInstance()
    {
        if (instance == null)
        {
            instance = new EmployeeJspHelper();
        }
        return instance;
    }

    public String showFilteredOrdersByEmployeeId(OrderSortType sortOrders,
            String templateId, String serviceId, String eId,
            String id, String status, String action, String creationDate, EmployeeSessionBean employeeSessionBean)
    {
        List<Order> orders = employeeSessionBean.getFilteredOrdersByEmployeeId(sortOrders,
                templateId,serviceId,eId,id,status,action,creationDate);
        return htmlTableBuilder.createOrdersHtmlTable(orders, id,templateId,serviceId,eId,
                status,action,creationDate);
    }

    public String showFilteredOrders(OrderSortType sortOrders,
            String templateId, String serviceId, String eId,
            String id, String status, String action, String creationDate, EmployeeSessionBean employeeSessionBean)
    {
        List<Order> orders = employeeSessionBean.getFilteredOrders(sortOrders,
                templateId,serviceId,eId,id,status,action,creationDate);
        return htmlTableBuilder.createOrdersHtmlTable(orders, id,templateId,serviceId,eId,
                status,action,creationDate);
    }

    public String showFilteredServices(ServiceSortType sortService, String id,
            String name, String cost, String status, String templateId,
            String customerId, String activationDate, String areas, EmployeeSessionBean employeeSessionBean)
    {
        List<Service> services = employeeSessionBean.getFilteredServices(sortService,id,name,cost,status,
                templateId,customerId,activationDate,areas);
        return htmlTableBuilder.createServicesHtmlTable(services,id,name,cost,status,templateId,
                customerId,activationDate,areas);
    }

    public String showFilteredTemplates(TemplateSortType sortTemplates,
            String name, String cost, String id, String description,
            String areas, EmployeeSessionBean employeeSessionBean)
    {
        List<Template> templates = employeeSessionBean.getFilteredTemplates(sortTemplates,name,
                cost,id,description,areas);
        return htmlTableBuilder.createTemplatesHtmlTable(templates,id,name,cost,description,areas);
    }

    public String showFilteredCustomers(CustomerSortType sortCustomers,
            String id, String name, String area, String login,
            String password, String balance, String connectedServices, EmployeeSessionBean employeeSessionBean)
    {
        List<Customer> customers = employeeSessionBean.getFilteredCustomers(sortCustomers,id,
                name,area,login,password,balance,connectedServices);
        return htmlTableBuilder.createCustomersHtmlTable(customers,id,name,area,login,password,balance,
                connectedServices);
    }


    public String showFilteredEmployees(EmployeeSortType sortEmployees,
            String id, String name, String login, String password, EmployeeSessionBean employeeSessionBean)
    {
        List<Employee> employees = employeeSessionBean.getFilteredEmployees(sortEmployees,id,
                name,login,password);
        return htmlTableBuilder.createEmployeesHtmlTable(employees,id,name,login,password);
    }

    public String showFilteredAreas(AreaSortType sortAreas,
            String id, String name, String description, EmployeeSessionBean employeeSessionBean)
    {
        List<Area> areas = employeeSessionBean.getFilteredAreas(sortAreas,id,name,description);
        return htmlTableBuilder.createAreasHtmlTable(areas,id,name,description);
    }

    public String showAll(String search, EmployeeSessionBean employeeSessionBean, String... entities)
    {
        StringBuilder result = new StringBuilder();

        for (String entity : entities)
        {
            if (entity != null)
            {
                switch (entity)
                {
                    case "Services":
                        result.append("<h2>Services</h2><div class='table'>")
                                .append(showFilteredServices( null,search,search,search,search,search,search,search,search, employeeSessionBean))
                                .append("</div>"); break;
                    case "Templates":
                        result.append("<h2>Templates</h2><div class='table'>")
                                .append(showFilteredTemplates(null,search,search,search,search,search, employeeSessionBean))
                                .append("</div>"); break;
                    case "Orders":
                        result.append("<h2>Orders</h2><div class='table'>")
                                .append(showFilteredOrders(null,search,search,search,search,search,search,search, employeeSessionBean))
                                .append("</div>"); break;
                    case "Customers":
                        result.append("<h2>Customers</h2><div class='table'>")
                                .append(showFilteredCustomers(null,search,search,search,search,search,search,search, employeeSessionBean))
                                .append("</div>"); break;
                    case "Areas":
                        result.append("<h2>Areas</h2><div class='table'>")
                                .append(showFilteredAreas(null,search,search,search, employeeSessionBean)).append("</div>"); break;
                    case "Employees":
                        result.append("<h2>Employees</h2><div class='table'>")
                                .append(showFilteredEmployees(null,search,search,search,search, employeeSessionBean)).append("</div>"); break;
                }
            }
        }

        return result.toString();
    }

    public String checkBocksEntities(EmployeeSessionBean sessionBean, String type){
        HtmlCheckboxBuilder checkboxBuilder = HtmlCheckboxBuilder.getInstance();
        String result = "error";
        switch (type){
            case "area":
                result = checkboxBuilder.makeCheckboxesFromAreas(sessionBean.getAreas()); break;
            case "customer":
                result = checkboxBuilder.makeCheckboxesFromCustomers(sessionBean.getCustomers()); break;
            case "template":
                result = checkboxBuilder.makeCheckboxesFromTemplates(sessionBean.getTemplates()); break;
            case "service":
                result = checkboxBuilder.makeCheckboxesFromServices(sessionBean.getServices()); break;
            case "employee":
                result = checkboxBuilder.makeCheckboxesFromEmployees(sessionBean.getEmployees()); break;
            case "order":
                result = checkboxBuilder.makeCheckboxesFromOrders(sessionBean.getOrders()); break;
        }

        return result;
    }

    public String transformAreasToHtml(List<Area> areas){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
       for(Area area : areas){
           res.append(parser.parseToHtml(transformer.transform(area))).append(",</br>");
       }
       return res.toString();
    }

    public String transformCustomersToHtml(List<Customer> customers){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
        for(Customer customer : customers){
            res.append(parser.parseToHtml(transformer.transform(customer))).append(",</br>");
        }
        return res.toString();
    }

    public String transformTemplatesToHtml(List<Template> templates){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
        for(Template template : templates){
            res.append(parser.parseToHtml(transformer.transform(template))).append(",</br>");
        }
        return res.toString();
    }

    public String transformEmployeesToHtml(List<Employee> employees){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
        for(Employee employee : employees){
            res.append(parser.parseToHtml(transformer.transform(employee))).append(",</br>");
        }
        return res.toString();
    }

    public String transformServicesToHtml(List<Service> services){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
        for(Service service : services){
            res.append(parser.parseToHtml(transformer.transform(service))).append(",</br>");

        }
        return res.toString();
    }

    public String transformOrdersToHtml(List<Order> orders){
        StringBuilder res = new StringBuilder("</br>");
        DtoTransformer transformer = new DtoTransformer();
        HtmlParser parser = new HtmlParser();
        for(Order order : orders){
            res.append(parser.parseToHtml(transformer.transform(order))).append(",</br>");
        }
        return res.toString();
    }

    public String getEntitiesEditForm(String type){
        switch (type){
            case "customer": return getCustomersEditForm();
            case "employee": return getEmployeesEditForm();
            case "template": return getTemplatesEditForm();
            case "service": return getServicesEditForm();
            case "order": return getOrdersEditForm();
            case "area": return getAreasEditForm();
        }
        return "";
    }

    public String getCustomersEditForm(){
        String res = "";

        res+= "<p>name " + createInput("name")+"</p>";
        res+= "<p>login " + createInput("login")+"</p>";
        res+= "<p>password " + createInput("password")+"</p>";
        res+= "<p>balance " + createInput("balance")+"</p>";

        return res;
    }

    public String getEmployeesEditForm(){
        String res = "";

        res+= "<p>name " + createInput("name")+"</p>";
        res+= "<p>login " + createInput("login")+"</p>";
        res+= "<p>password " + createInput("password")+"</p>";

        return res;
    }

    public String getAreasEditForm(){
        String res = "";

        res+= "<p>name " + createInput("name")+"</p>";
        res+= "<p>description " + createInput("description")+"</p>";

        return res;
    }

    public String getTemplatesEditForm(){
        String res = "";

        res+= "<p>name " + createInput("name")+"</p>";
        res+= "<p>description " + createInput("description")+"</p>";
        res+= "<p>cost " + createInput("cost")+"</p>";

        return res;
    }

    public String getServicesEditForm(){
        String res = "";
        res+= "<p>status " + "<select name=\"status\">" +
                "                <option>Planned</option>" +
                "                <option>Active</option>" +
                "                <option>Processing</option>" +
                "                <option>Disconnected</option>" +
                "                <option>Suspended</option>" +
                "            </select>"+"</p>";
        return res;
    }

    public String getOrdersEditForm(){
        String res = "";
        res+= "<p>action " + "<select name=\"action\">" +
                "            <option>New</option>" +
                "            <option>Disconnect</option>" +
                "            <option>Resume</option>" +
                "            <option>Suspend</option>" +
                "        </select>"+"</p>";
        res+= "<p>status " + "<select name=\"status\">" +
                "            <option>Entering</option>" +
                "            <option>Active</option>" +
                "            <option>Processing</option>" +
                "            <option>Disconnected</option>" +
                "        </select>"+"</p>";
        return res;
    }


    private String createInput(String name){
        return "<input type=\"text\" name=\""+name+"\" >";
    }
}
