package jsp.sessionBeans;

import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.controller.searcher.AreaSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.CustomerSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.EmployeeSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.OrdersSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.ServiceSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.TemplatesSearcher;
import jsp.com.netcracker.students.o3.controller.sorters.AreaSorter;
import jsp.com.netcracker.students.o3.controller.sorters.CustomerSorter;
import jsp.com.netcracker.students.o3.controller.sorters.EmployeeSorter;
import jsp.com.netcracker.students.o3.controller.sorters.OrderSorter;
import jsp.com.netcracker.students.o3.controller.sorters.ServiceSorter;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.AreaSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.CustomerSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.EmployeeSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.OrderSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.TemplateSortType;
import jsp.com.netcracker.students.o3.controller.sorters.TemplateSorter;
import jsp.com.netcracker.students.o3.model.Entity;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.serialization.JsonEntitiesStorage;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

@Stateless
public class EmployeeSessionBean
{
    public Employee getEmployee(BigInteger employeeId)
    {
        return ControllerImpl.getInstance().getEmployee(employeeId);
    }

    public Entity getEntity(BigInteger id)
    {
        return ControllerImpl.getInstance().getEntity(id);
    }

    public Customer getCustomer(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getCustomer(customerId);
    }

    public Template getTemplate(BigInteger templateId)
    {
        return ControllerImpl.getInstance().getTemplate(templateId);
    }

    public Service getService(BigInteger serviceId)
    {
        return ControllerImpl.getInstance().getService(serviceId);
    }

    public Order getOrder(BigInteger orderId)
    {
        return ControllerImpl.getInstance().getOrder(orderId);
    }

    public Area getArea(BigInteger areaId)
    {
        return ControllerImpl.getInstance().getArea(areaId);
    }

    public void changeNameAndPassword(String name, String password, BigInteger employeeId)
    {
        Employee employee = ControllerImpl.getInstance().getEmployee(employeeId);

        employee.setName(name);
        employee.setPassword(password);

        ControllerImpl.getInstance().setEmployee(employee);
    }

    public List<Order> getFilteredOrdersByEmployeeId(OrderSortType sortOrders,
            String templateId, String serviceId, String eId,
            String id, String status, String action, String creationDate)
    {
        OrdersSearcher searcherOrders = OrdersSearcher.getInstance();

        List<Order> orders = null;
        try
        {
            if (isNotNullOrEmpty(eId))
            {
                orders = ControllerImpl.getInstance().getOrdersByEmployeeId(BigInteger.valueOf(Long.parseLong(eId)));
            }

            if (isNotNullOrEmpty(templateId))
            {
                orders = searcherOrders.search(templateId + "", "TemplateId", orders);
            }
            if (isNotNullOrEmpty(serviceId))
            {
                orders = searcherOrders.search(serviceId + "", "ServiceId", orders);
            }
            if (isNotNullOrEmpty(id))
            {
                orders = searcherOrders.search(id + "", "Id", orders);
            }
            if (isNotNullOrEmpty(status))
            {
                orders = searcherOrders.search(status + "", "Status", orders);
            }
            if (isNotNullOrEmpty(action))
            {
                orders = searcherOrders.search(action + "", "Action", orders);
            }
            if (isNotNullOrEmpty(creationDate))
            {
                orders = searcherOrders.search(creationDate + "", "CreationDate", orders);
            }


            if (sortOrders != null && orders != null)
            {
                OrderSorter.getInstance().sort(orders, sortOrders);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getFilteredOrders(OrderSortType sortOrders,
            String templateId, String serviceId, String eId,
            String id, String status, String action, String creationDate)
    {
        OrdersSearcher searcherOrders = OrdersSearcher.getInstance();
        List<Order> orders = ControllerImpl.getInstance().getOrders();
        try
        {
            if (isNotNullOrEmpty(eId))
            {
                orders = ControllerImpl.getInstance().getOrdersByEmployeeId(BigInteger.valueOf(Long.parseLong(eId)));
            }

            if (isNotNullOrEmpty(eId))
            {
                orders = searcherOrders.search(eId, "EmployeeId", orders);
            }
            if (isNotNullOrEmpty(templateId))
            {
                orders = searcherOrders.search(templateId, "TemplateId", orders);
            }
            if (isNotNullOrEmpty(serviceId))
            {
                orders = searcherOrders.search(serviceId, "ServiceId", orders);
            }
            if (isNotNullOrEmpty(id))
            {
                orders = searcherOrders.search(id, "Id", orders);
            }
            if (isNotNullOrEmpty(status))
            {
                orders = searcherOrders.search(status, "Status", orders);
            }
            if (isNotNullOrEmpty(action))
            {
                orders = searcherOrders.search(action, "Action", orders);
            }
            if (isNotNullOrEmpty(creationDate))
            {
                orders = searcherOrders.search(creationDate, "CreationDate", orders);
            }


            if (sortOrders != null && orders != null)
            {
                OrderSorter.getInstance().sort(orders, sortOrders);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    public List<Service> getFilteredServices(ServiceSortType sortService, String id,
            String name, String cost, String status, String templateId,
            String customerId, String activationDate, String areas)
    {
        ServiceSearcher searcherService = ServiceSearcher.getInstance();
        List<Service> services = ControllerImpl.getInstance().getServices();
        try
        {
            if (isNotNullOrEmpty(name))
            {
                services = searcherService.search(name, "Name", services);
            }
            if (isNotNullOrEmpty(cost))
            {
                services = searcherService.search(cost, "Cost", services);
            }
            if (isNotNullOrEmpty(status))
            {
                services = searcherService.search(status, "Status", services);
            }
            if (isNotNullOrEmpty(templateId))
            {
                services = searcherService.search(templateId, "TemplateId", services);
            }
            if (isNotNullOrEmpty(id))
            {
                services = searcherService.search(id, "Id", services);
            }
            if (isNotNullOrEmpty(customerId))
            {
                services = searcherService.search(templateId, "TemplateId", services);
            }
            if (isNotNullOrEmpty(activationDate))
            {
                services = searcherService.search(activationDate, "ActivationDate", services);
            }
            if (isNotNullOrEmpty(areas))
            {
                services = searcherService.search(areas, "Areas", services);
            }

            if (sortService != null && services != null)
            {
                ServiceSorter.getInstance().sort(services, sortService);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return services;
    }

    public List<Template> getFilteredTemplates(TemplateSortType sortTemplates,
            String name, String cost, String id, String description, String areas)
    {
        TemplatesSearcher searcherTemplates = TemplatesSearcher.getInstance();
        List<Template> templates = ControllerImpl.getInstance().getTemplates();
try
{
    if (isNotNullOrEmpty(name))
    {
        templates = searcherTemplates.search(name, "Name", templates);
    }
    if (isNotNullOrEmpty(cost))
    {
        templates = searcherTemplates.search(cost, "Cost", templates);
    }
    if (isNotNullOrEmpty(id))
    {
        templates = searcherTemplates.search(id, "Id", templates);
    }
    if (isNotNullOrEmpty(description))
    {
        templates = searcherTemplates.search(description, "Description", templates);
    }
    if (isNotNullOrEmpty(areas))
    {
        templates = searcherTemplates.search(areas, "Areas", templates);
    }

    if (sortTemplates != null && templates != null)
    {
        TemplateSorter.getInstance().sort(templates, sortTemplates);
    }
}catch (Exception e){
    e.printStackTrace();
}
        return templates;
    }

    public List<Customer> getFilteredCustomers(CustomerSortType sortCustomers,
            String id, String name, String area, String login,
            String password, String balance, String connectedServices)
    {
        CustomerSearcher searcherCustomer = CustomerSearcher.getInstance();
        List<Customer> customers = ControllerImpl.getInstance().getCustomers();

        try
        {
            if (isNotNullOrEmpty(name))
            {
                customers = searcherCustomer.search(name, "Name", customers);
            }
            if (isNotNullOrEmpty(area))
            {
                customers = searcherCustomer.search(area, "Area", customers);
            }
            if (isNotNullOrEmpty(login))
            {
                customers = searcherCustomer.search(login, "Login", customers);
            }
            if (isNotNullOrEmpty(password))
            {
                customers = searcherCustomer.search(password, "Password", customers);
            }
            if (isNotNullOrEmpty(balance))
            {
                customers = searcherCustomer.search(balance, "Balance", customers);
            }
            if (isNotNullOrEmpty(connectedServices))
            {
                customers = searcherCustomer.search(connectedServices, "ConnectedServices", customers);
            }

            if (sortCustomers != null && customers != null)
            {
                CustomerSorter.getInstance().sort(customers, sortCustomers);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return customers;
    }


    public List<Employee> getFilteredEmployees(EmployeeSortType sortEmployees,
            String id, String name, String login, String password)
    {
        EmployeeSearcher employeeSearcher = EmployeeSearcher.getInstance();
        List<Employee> employees = ControllerImpl.getInstance().getEmployees();

        try
        {
            if (isNotNullOrEmpty(id))
            {
                employees = employeeSearcher.search(id, "Id", employees);
            }
            if (isNotNullOrEmpty(login))
            {
                employees = employeeSearcher.search(login, "Login", employees);
            }
            if (isNotNullOrEmpty(name))
            {
                employees = employeeSearcher.search(name, "Name", employees);
            }
            if (isNotNullOrEmpty(password))
            {
                employees = employeeSearcher.search(password, "Password", employees);
            }

            if (sortEmployees != null && employees != null)
            {
                EmployeeSorter.getInstance().sort(employees, sortEmployees);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    public List<Area> getFilteredAreas(AreaSortType sortAreas,
            String id, String name, String description)
    {
        AreaSearcher searcherArea = AreaSearcher.getInstance();
        List<Area> areas = ControllerImpl.getInstance().getAreas();
        try
        {
            if (isNotNullOrEmpty(id))
            {
                areas = searcherArea.search(id, "Id", areas);
            }
            if (isNotNullOrEmpty(name))
            {
                areas = searcherArea.search(name, "Name", areas);
            }
            if (isNotNullOrEmpty(description))
            {
                areas = searcherArea.search(description, "Description", areas);
            }

            if (sortAreas != null && areas != null)
            {
                AreaSorter.getInstance().sort(areas, sortAreas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return areas;
    }


    public void startOrder(BigInteger orderId, BigInteger employeeId)
    {
        Order order = ControllerImpl.getInstance().getOrder(orderId);
        if (order.getEmployee() == null || order.getEmployee().getId().equals(employeeId))
        {
            ControllerImpl.getInstance().startOrder(orderId, getEmployee(employeeId));
        }
    }

    private boolean isNotNullOrEmpty(Object value)
    {
        return value != null && !value.toString().replaceAll(" ", "").isEmpty();
    }

    public void resumeOrder(BigInteger orderId)
    {
        ControllerImpl.getInstance().resumeOrder(orderId);
    }

    public void completeOrder(BigInteger orderId)
    {
        ControllerImpl.getInstance().completeOrder(orderId);
    }

    public void deleteTemplate(BigInteger templateId)
    {
        ControllerImpl.getInstance().deepDeleteTemplate(templateId);
    }

    public void deleteCustomer(BigInteger customerId)
    {
        ControllerImpl.getInstance().deepDeleteCustomer(customerId);
    }

    public void deleteEmployee(BigInteger employeeId)
    {
        ControllerImpl.getInstance().deepDeleteEmployee(employeeId);
    }

    public void deleteArea(BigInteger areaId)
    {
        ControllerImpl.getInstance().deepDeleteArea(areaId);
    }

    public void deleteOrder(final BigInteger id)
    {
        ControllerImpl.getInstance().deepDeleteOrder(id);
    }

    public void deleteService(final BigInteger id)
    {
        ControllerImpl.getInstance().deepDeleteService(id);
    }


    public List<Area> getAreas()
    {
        return ControllerImpl.getInstance().getAreas();
    }

    public List<Customer> getCustomers()
    {
        return ControllerImpl.getInstance().getCustomers();
    }

    public List<Employee> getEmployees()
    {
        return ControllerImpl.getInstance().getEmployees();
    }

    public List<Template> getTemplates()
    {
        return ControllerImpl.getInstance().getTemplates();
    }

    public List<Service> getServices()
    {
        return ControllerImpl.getInstance().getServices();
    }

    public List<Order> getOrders()
    {
        return ControllerImpl.getInstance().getOrders();
    }


    public List<Order> getOrders(List<BigInteger> ids)
    {
        List<Order> orders = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            orders.add(controller.getOrder(id));
        }
        return orders;
    }

    public List<Service> getServices(List<BigInteger> ids)
    {
        List<Service> services = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            services.add(controller.getService(id));
        }
        return services;
    }

    public List<Template> getTemplates(List<BigInteger> ids)
    {
        List<Template> templates = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            templates.add(controller.getTemplate(id));
        }
        return templates;
    }

    public List<Customer> getCustomers(List<BigInteger> ids)
    {
        List<Customer> orders = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            orders.add(controller.getCustomer(id));
        }
        return orders;
    }

    public List<Employee> getEmployees(List<BigInteger> ids)
    {
        List<Employee> employees = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            employees.add(controller.getEmployee(id));
        }
        return employees;
    }

    public List<Area> getAreas(List<BigInteger> ids)
    {
        List<Area> areas = new ArrayList<>();
        Controller controller = ControllerImpl.getInstance();

        for (BigInteger id : ids)
        {
            areas.add(controller.getArea(id));
        }
        return areas;
    }

    public void importEntities(boolean isIgnored)
    {
        JsonEntitiesStorage jsonEntitiesStorage = JsonEntitiesStorage.getInstance();
        jsonEntitiesStorage.importFromFile();

        ControllerImpl.getInstance().importEntities(jsonEntitiesStorage, isIgnored);
    }

    public void setFieldsOfEntities(Set<BigInteger> idsOfEntities, String type, Map<String, String[]> params)
    {
        switch (type)
        {
            case "customer":
                setFieldsOfCustomers(idsOfEntities, params); break;
            case "employee":
                setFieldsOfEmployees(idsOfEntities, params); break;
            case "template":
                setFieldsOfTemplates(idsOfEntities, params); break;
            case "service":
                setFieldsOfServices(idsOfEntities, params); break;
            case "order":
                setFieldsOfOrders(idsOfEntities, params); break;
            case "area":
                setFieldsOfAreas(idsOfEntities, params); break;
        }
    }

    public void setFieldsOfCustomers(Set<BigInteger> idsOfCustomers, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfCustomers)
        {
            Customer customer = controller.getCustomer(id);
            String name = params.get("name")[0];
            String login = params.get("login")[0];
            String password = params.get("password")[0];
            String balance = params.get("balance")[0];
            if (!"".equals(name))
            {
                customer.setName(name + customer.getId());
            }
            if (!"".equals(login))
            {
                customer.setLogin(login + customer.getId());
            }
            if (!"".equals(password))
            {
                customer.setPassword(password + customer.getId());
            }
            if (!"".equals(balance))
            {
                customer.setMoneyBalance(BigDecimal.valueOf(Long.parseLong(balance)));
            }
            controller.setCustomer(customer);
        }
    }

    public void setFieldsOfEmployees(Set<BigInteger> idsOfEmployees, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfEmployees)
        {
            Employee employee = controller.getEmployee(id);
            String name = params.get("name")[0];
            String login = params.get("login")[0];
            String password = params.get("password")[0];
            if (!"".equals(name))
            {
                employee.setName(name + employee.getId());
            }
            if (!"".equals(login))
            {
                employee.setLogin(login + employee.getId());
            }
            if (!"".equals(password))
            {
                employee.setPassword(password + employee.getId());
            }
            controller.setEmployee(employee);
        }
    }

    public void setFieldsOfTemplates(Set<BigInteger> idsOfTemplates, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfTemplates)
        {
            Template template = controller.getTemplate(id);
            String name = params.get("name")[0];
            String description = params.get("description")[0];
            String cost = params.get("cost")[0];
            if (!"".equals(name))
            {
                template.setName(name + template.getId());
            }
            if (!"".equals(description))
            {
                template.setDescription(description);
            }
            if (!"".equals(cost))
            {
                template.setCost(BigDecimal.valueOf(Long.parseLong(cost)));
            }
            controller.setTemplate(template);
        }
    }

    public void setFieldsOfServices(Set<BigInteger> idsOfServices, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfServices)
        {
            Service service = controller.getService(id);
            String status = params.get("status")[0];
            if (!"".equals(status))
            {
                service.setStatus(ServiceStatus.valueOf(status));
            }
            controller.setService(service);
        }
    }

    public void setFieldsOfOrders(Set<BigInteger> idsOfOrders, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfOrders)
        {
            Order order = controller.getOrder(id);
            String status = params.get("status")[0];
            String action = params.get("action")[0];
            if (!"".equals(status))
            {
                order.setStatus(OrderStatus.valueOf(status));
            }
            if (!"".equals(action))
            {
                order.setAction(OrderAction.valueOf(status));
            }
            controller.setOrder(order);
        }
    }

    public void setFieldsOfAreas(Set<BigInteger> idsOfAreas, Map<String, String[]> params)
    {
        Controller controller = ControllerImpl.getInstance();
        for (BigInteger id : idsOfAreas)
        {
            Area area = controller.getArea(id);
            String name = params.get("name")[0];
            String description = params.get("description")[0];
            if (!"".equals(name))
            {
                area.setName(name + area.getId());
            }
            if (!"".equals(description))
            {
                area.setDescription(description);
            }
            controller.setArea(area);
        }
    }
}