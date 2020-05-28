package jsp.builders;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

public class HtmlTableBuilder {

    private String[] colors;

    private static HtmlTableBuilder instance;
    private Controller controller = ControllerImpl
            .getInstance();
    private static final String nextLine = "<br>";

    private HtmlTableBuilder() {
        colors = new String[]{"#8c8888", "#5c5959"};
    }




    public String createServicesHtmlTable(Collection<Service> services,final String id, final String name,
            final String cost,final String status,final String templateId,final String customerId,final String activationDate,
            final String areas) {
        String innerPart = "";

        innerPart +=
                addColumns(new String[]{"","unique id", "name of service","cost of service","status of service show his state",
                        "id of template","id of customer","date when service was activated","areas where status is availabel","",""},
                        addCheckboxImg(),
                        "id"+nextLine
                                +createButton("↑","ServiceSortUpById")+
                                createButton("↓","ServiceSortDownById")+nextLine+
                                createInput("filterServiceId",id),
                        "Name"+nextLine
                                +createButton("↑","ServiceSortDownByName")+
                                createButton("↓","ServiceSortUpByName")+nextLine+
                        createInput("filterServiceName",name),
                        "Cost"+nextLine
                                +createButton("↑","ServiceSortDownByCost")+
                                createButton("↓","ServiceSortUpByCost")+nextLine+
                        createInput("filterServiceCost",cost),
                        "Status"+nextLine
                                +createButton("↑","ServiceSortDownByStatus")+
                                createButton("↓","ServiceSortUpByStatus")+nextLine+
                                createInput("filterServiceStatus",status),
                        "TemplateId"+nextLine
                                +createButton("↑","ServiceSortDownByTemplateId")+
                                createButton("↓","ServiceSortUpByTemplateId")+nextLine+
                                createInput("filterServiceTemplateId",templateId),
                        "CustomerId"+nextLine
                                +createButton("↑","ServiceSortDownByCustomerId")+
                                createButton("↓","ServiceSortUpByCustomerId")+nextLine+
                                createInput("filterServiceCustomerId",customerId),
                        "ActivationDate" +nextLine+
                                createButton("↑","ServiceSortDownByActivationDate")+
                                createButton("↓","ServiceSortUpByActivationDate")+nextLine+
                                createInput("filterServiceActivationDate",activationDate)
                        , "Areas"+nextLine+
                                createButton("↑","ServiceSortDownByAreas")+
                                createButton("↓","ServiceSortUpByAreas")+nextLine+
                                createInput("filterServiceAreas",areas)
                        ,"","");

        innerPart += addServicesToHtmlTable(services);

        return build(innerPart);
    }

    public String createTemplatesHtmlTable(Collection<Template> templates,final String id, final String name,
            final String cost,final String description,final String areas) {
        StringBuilder innerPart = new StringBuilder();

        innerPart.append(
                addColumns(new String[]{"","unique id","name of template","cost of template","description of " +
                                "template","areas where template is available","",""},
                        addCheckboxImg(),
                        "Id"+nextLine+
                                createButton("↑","TemplateSortDownById")
                                +createButton("↓","TemplateSortUpById")+nextLine
                                +createInput("filterTemplateId",id)
                        , "Name"+nextLine
                                +createButton("↑","TemplateSortDownByName")
                                +createButton("↓","TemplateSortUpByName")+nextLine
                        +createInput("filterTemplateName",name)
                        , "Cost"+nextLine
                                +createButton("↑","TemplateSortDownByCost")
                                +createButton("↓","TemplateSortUpByCost")+nextLine
                        +createInput("filterTemplateCost",cost)
                        , "Description"+nextLine
                                +createButton("↑","TemplateSortDownByDescription")
                                +createButton("↓","TemplateSortUpByDescription")+nextLine
                                +createInput("filterTemplateDescription",description)
                        , "Areas"+nextLine
                                +createButton("↑","TemplateSortDownByAreas")
                                +createButton("↓","TemplateSortUpByAreas")+nextLine
                                +createInput("filterTemplateAreas",areas)
                        ,"","")
        );

        int i = 1;
        for (Template t : templates) {
            i = getNextColorNumber(i);
            innerPart.append(addEmployeeTemplateToHtmlTable(t, colors[i]));
        }

        return build(innerPart.toString());
    }

    public String createOrdersHtmlTable(
            Collection<Order> orders,final String id, final String templateId, final String serviceId,
            final String eId,final String status,final String action,final String creationDate) {
        StringBuilder innerPart = new StringBuilder();

        innerPart.append(addColumns(new String[]{"","unique id","id of template",
                "id of service","id of employee","status show state of order",
                "action show what need to do","date of creation",""},
                addCheckboxImg(),
                 "id"+nextLine
                 +createButton("↑","OrderSortDownById")
                 +createButton("↓","OrderSortUpById")+nextLine
                 +createInput("filterOrderId",id)
                ,"TemplateId"+nextLine
                        +createButton("↑","OrderSortDownByTemplateId")
                        +createButton("↓","OrderSortUpByTemplateId")+nextLine
                        +createInput("filterOrderTemplateId",templateId),
                "ServiceId"+nextLine
                        +createButton("↑","OrderSortDownByServiceId")
                        +createButton("↓","OrderSortUpByServiceId")+nextLine
                        +createInput("filterOrderServiceId",serviceId),
                "EmployeeId"+nextLine
                        +createButton("↑","OrderSortDownByEmployeeId")
                        +createButton("↓","OrderSortUpByEmployeeId")+nextLine
                        +createInput("filterOrderEmployeeId",eId),
                "Status"+nextLine
                        +createButton("↑","OrderSortDownByStatus")
                        +createButton("↓","OrderSortUpByStatus")
                        +createInput("filterOrderStatus",status)
                , "Action"+nextLine
                        +createButton("↑","OrderSortDownByAction")
                        +createButton("↓","OrderSortUpByAction")
                        +createInput("filterOrderAction",action)
                , "CreationDate"+nextLine
                        +createButton("↑","OrderSortDownByCreationDate")
                        +createButton("↓","OrderSortUpByCreationDate")+nextLine
                        +createInput("filterOrderCreationDate",creationDate)
                ,
                ""));
        int i = 1;
        if(orders!=null)
        for (Order order : orders) {
            i = getNextColorNumber(i);
            innerPart.append(addOrderToHtmlTable(order, colors[i]));
        }

        return build(innerPart.toString());
    }

    public String createCustomersHtmlTable(
            Collection<Customer> customers,final String id, final String name, final String area,
            final String login,final String password,final String balance,final String connectedServices) {
        StringBuilder innerPart = new StringBuilder();

        innerPart.append(
                addColumns(new String[]{"","unique id","name of customer","login of customer",
                        "password of customer","area of customer","balance of customer","list of connected ids","",""},
                        addCheckboxImg(),
                        "Id"+nextLine
                                +createButton("↑","CustomerSortDownById")+
                                createButton("↓","CustomerSortUpById")+nextLine
                                +createInput("filterCustomerId",id)
                        , "Name"+nextLine
                                +createButton("↑","CustomerSortDownByName")
                                +createButton("↓","CustomerSortUpByName")+nextLine
                                +createInput("filterCustomerName",name)
                        , "Login"+nextLine
                                +createButton("↑","CustomerSortDownByLogin")
                                +createButton("↓","CustomerSortUpByLogin")+nextLine
                                +createInput("filterCustomerLogin",login)
                        , "Password"+nextLine
                                +createButton("↑","CustomerSortDownByPassword")
                                +createButton("↓","CustomerSortUpByPassword")+nextLine
                                +createInput("filterCustomerLogin",password)
                        , "Area"+nextLine
                                +createButton("↑","CustomerSortDownByArea")
                                +createButton("↓","CustomerSortUpByArea")+nextLine
                                +createInput("filterCustomerArea",area),
                        "Balance"+nextLine
                                +createButton("↑","CustomerSortDownByBalance")
                                +createButton("↓","CustomerSortUpByBalance")+nextLine
                                +createInput("filterCustomerBalance",balance)
                        , "ConnectedServices"+nextLine
                                +createButton("↑","CustomerSortDownByConnectedServices")
                                +createButton("↓","CustomerSortUpByConnectedServices")+nextLine
                                +createInput("filterCustomerConnectedServices",connectedServices)
                        ,"","")
        );

        int i = 1;
        for (Customer customer : customers) {
            i = getNextColorNumber(i);
            innerPart.append(addCustomerToHtmlTable(customer, colors[i]));
        }

        return build(innerPart.toString());
    }

    public String createEmployeesHtmlTable(Collection<Employee> employees,final String id, final String name,
            final String login,final String password) {
        StringBuilder innerPart = new StringBuilder();

        innerPart.append(
                addColumns(new String[]{"","unique id","name of employee","login of employee",
                        "login of employee","password of employee","",""},
                        addCheckboxImg(),
                        "Id"+nextLine
                                +createButton("↑","EmployeeSortDownById")
                                +createButton("↓","EmployeeSortUpById")+nextLine
                                +createInput("filterEmployeeId",id)
                        , "Name"+nextLine
                                +createButton("↑","EmployeeSortDownByName")
                                +createButton("↓","EmployeeSortUpByName")+nextLine
                                +createInput("filterEmployeeName",name)
                        , "Login"+nextLine
                                +createButton("↑","EmployeeSortDownByLogin")
                                +createButton("↓","EmployeeSortUpByLogin")+nextLine
                                +createInput("filterEmployeeLogin",login)
                        , "Password"+nextLine
                                +createButton("↑","EmployeeSortDownByPassword")
                                +createButton("↓","EmployeeSortUpByPassword")+nextLine
                                +createInput("filterEmployeePassword",password)
                        ,"","")
        );

        int i = 1;
        for (Employee employee : employees) {
            i = getNextColorNumber(i);
            innerPart.append(addEmployeeToHtmlTable(employee, colors[i]));
        }

        return build(innerPart.toString());
    }

    public String createAreasHtmlTable(
            Collection<Area> areas,final String id, final String name,final String description) {
        StringBuilder innerPart = new StringBuilder();

        innerPart.append(
                addColumns(new String[]{"","unique id","area name","area description","",""},
                        addCheckboxImg(),
                        "Id"+nextLine
                                +createButton("↑","AreaSortDownById")
                                +createButton("↓","AreaSortUpById")+nextLine
                                +createInput("filterAreaId",id)
                        , "Name"+nextLine
                                +createButton("↑","AreaSortDownByName")
                                +createButton("↓","AreaSortUpByName")+nextLine
                                +createInput("filterAreaName",name)
                        , "Description"+nextLine
                                +createButton("↑","AreaSortDownByDescription")
                                +createButton("↓","AreaSortUpByDescription")+nextLine
                                +createInput("filterAreaDescription",description)
                        ,"","")
        );

        int i = 1;
        for (Area area : areas) {
            i = getNextColorNumber(i);
            innerPart.append(addAreaToHtmlTable(area, colors[i]));
        }

        return build(innerPart.toString());
    }






    private String addServicesToHtmlTable(Collection<Service> services) {
        StringBuilder innerPart = new StringBuilder();
        int i = 1;
        for (Service service : services) {
            i = getNextColorNumber(i);
            innerPart.append(addEmployeeServiceToHtmlTable(service, colors[i]));
        }
        return innerPart.toString();
    }




    private String addOrderToHtmlTable(Order order, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("orderChecked "+order.getId(),
                "orderChecked "+order.getId()));
        result += addCell(addId(order.getId()));
        result += addCell(addId(order.getTemplate().getId()));
        result += addCell(addId(order.getService().getId()));
        if(order.getEmployee()!=null)
        {
            result += addCell(addId(order.getEmployee().getId()));
        }else {
            result += addCell(addId(BigInteger.ZERO));
        }
        result += addCell(order.getStatus() + "");
        result += addCell(order.getAction() + "");
        result += addCell(order.getCreationDate() + "");

        String buttons = "";
        switch (order.getStatus()){
            case Entering:buttons+=createButton("Start","startOrder",order.getId().toString());break;
            case Suspended:
                buttons+=createButton("BackToEntering","cancelOrder",order.getId().toString());
                buttons+=createButton("Resume","resumeOrder",order.getId().toString());break;
            case Completed:break;
            case Processing:
                buttons+=createButton("BackToEntering","cancelOrder",order.getId().toString());
                buttons+=createButton("Complete","completeOrder",order.getId().toString());break;
        }
        buttons+= createButton("Delete","deleteOrder",order.getId().toString())+
                createButton("Update","updateOrder",order.getId().toString());
        result += addCell(buttons);

        return result + "</tr>";
    }

    private String addEmployeeServiceToHtmlTable(Service service, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("serviceChecked "+service.getId(),
                "serviceChecked "+service.getId()));
        result += addCell(addId(service.getId()));
        result += addCell(controller.getServiceName(service.getId()));
        result += addCell(service.templateGetCost() + "");
        result += addCell(service.getStatus() + "");
        result += addCell(addId(service.getTemplate().getId()));
        result += addCell(addId(service.getCustomer().getId()));
        result += addCell(service.getActivationDate() + "");
        result += addCell(getAreasByTemplateId(service.getTemplate().getId()));
        result += addCell(createButton("Delete","deleteService",service.getId().toString()));
        result += addCell(createButton("Update","updateService",service.getId().toString()));


        return result + "</tr>";
    }

    private String addEmployeeTemplateToHtmlTable(Template template, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("templateChecked "+template.getId(),
                "templateChecked "+template.getId()));
        result += addCell(addId(template.getId()));
        result += addCell(template.getName() + "");
        result += addCell(template.getCost() + "");
        result += addCell(template.getDescription() + "");
        result += addCell(getAreasByTemplateId(template.getId()));
        result +=addCell(createButton("Delete","deleteTemplate",template.getId().toString()));
        result += addCell(createButton("Update","updateTemplate",template.getId().toString()));

        return result + "</tr>";
    }

    private String addCustomerToHtmlTable(Customer customer, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("customerChecked "+customer.getId(),
                "customerChecked "+customer.getId()));
        result += addCell(addId(customer.getId()));
        result += addCell(customer.getName() + "");
        result += addCell(customer.getLogin());
        result += addCell(customer.getPassword());
        result += addCell(addId(customer.getArea().getId()));
        result += addCell(customer.getMoneyBalance() + "");
        result += addCell(
                getCustomerConnectedServiceIds(customer)
        );
        result+=addCell(createButton("Delete","deleteCustomer",customer.getId().toString()));
        result+=addCell(createButton("Update","updateCustomer",customer.getId().toString()));


        return result + "</tr>";
    }

    private String addEmployeeToHtmlTable(Employee employee, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("employeeChecked "+employee.getId(),
                "employeeChecked "+employee.getId()));
        result += addCell(addId(employee.getId()));
        result += addCell(employee.getName() + "");
        result += addCell(employee.getLogin());
        result += addCell(employee.getPassword());
        result+=addCell(createButton("Delete","deleteEmployee",employee.getId().toString()));
        result+=addCell(createButton("Update","updateEmployee",employee.getId().toString()));

        return result + "</tr>";
    }

    private String addAreaToHtmlTable(Area area, String color) {
        String result = "<tr bgcolor='" + color + "'>";

        result += addCell(createCheckbox("areaChecked "+area.getId(),
                "areaChecked "+area.getId()));
        result += addCell(addId(area.getId()));
        result += addCell(area.getName() + "");
        result += addCell(area.getDescription());
        result += addCell(createButton("Delete","deleteArea",area.getId().toString()));
        result += addCell(createButton("Update","updateArea",area.getId().toString()));

        return result + "</tr>";
    }


    private String addColumns(String[] titles, String... columnNames) {
        StringBuilder result = new StringBuilder();
        String rowStart = "<tr class='tableHeader'>";
        result.append(rowStart);
        int i = 0;
        for (String columnName : columnNames) {
            result.append("<th title='"+titles[i]+"'>").append(columnName).append("</th>");
            i++;
        }
        result.append("</tr>");
        return result.toString();
    }

    private String addCell(String cell) {
        String result = "";
        String cellStart = "<td align='center'>";
        String cellEnd = "</td>";
        result += cellStart + cell + cellEnd;
        return result;
    }

    private String addCell(String cell, String color) {
        String startFont = "<font color='" + color + "'>";
        String endFont = "</font>";
        return addCell(startFont + cell + endFont);
    }


    private String createButton(String value, String name) {
        String buttonStart = "<input ";
        String buttonEnd = " />";
        return buttonStart + "type='" + "submit" + "' value='" + value + "' name='" + name + "'" + buttonEnd;
    }

    private String createButton(String value, String name, String id) {
        return createButton(value,name+" "+id);
    }


    private String build(String innerPart) {
        String start = "<table cellpadding='10' class='table'>";
        String end = "</table>";
        return start + innerPart + end;
    }


    private int getNextColorNumber(int i) {
        if (i == 0) {
            return 1;
        }
        return 0;
    }


    private String getAreasByTemplateId(BigInteger templateId) {
        StringBuilder result = new StringBuilder();

        Controller controller = ControllerImpl.getInstance();
        Template template = controller.getTemplate(templateId);

        List<Area> areas = template.getPossibleAreas();

        for (Area area : areas) {
            result.append(" ").append(area.getName());
        }

        return result.toString();
    }

    private String getCustomerConnectedServiceIds(Customer customer) {
        StringBuilder result = new StringBuilder();
        Set<Service> services = customer.getConnectedServices();
        int i = 0;
        for (Service service : services) {
            if(i!=services.size()-1)
            {
                result.append(addId(service.getId())).append(",");
            }else {
                result.append(addId(service.getId()));
            }
            i++;
        }
        return result.toString();
    }

    private String createInput(String name){
        return "<input type=\"text\" name=\""+name+"\">";
    }

    private String createInput(String name, String value){
        if (value == null){
            value = "";
        }
        return "<input class='filterField' type=\"text\" name=\""+name+"\" value=\""+value+"\">";
    }

    private String createCheckbox(String name, String value){
        return  "<input type=\"checkbox\" name=\""+name+"\" value=\""+value+"\">";
    }

    private String addId(BigInteger id){
        String start = "<a href='http://localhost:8080/JSONVisual.jsp?entityId=";
        String mid = "' target=\"_blank\">";
        String close = "</a>";

        return start+id+mid+id+close;
    }
    public static HtmlTableBuilder getInstance() {
        if (instance == null) {
            instance = new HtmlTableBuilder();
        }

        return instance;
    }

    public String addCheckboxImg(){
        return "<img src='resources/checkbox.jpg'/>";
    }
}