package jsp.com.netcracker.students.o3.model;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface Model
{
    void setOrders(final Map<BigInteger, Order> orders);

    void setTemplates(final Map<BigInteger, Template> templates);

    void setServices(final Map<BigInteger, Service> services);

    void setCustomers(final Map<BigInteger, Customer> customers);

    void setEmployees(final Map<BigInteger, Employee> employees);

    void setAreas(final Map<BigInteger, Area> areas);


    BigInteger getLastId();

    void setLastId(final BigInteger lastId);

    BigInteger getNextId();


    Customer createCustomer(String name, String login, String password, Area area);

    Employee createEmployee(String name, String login, String password);

    Order createOrder(Template template, Service service,
            OrderStatus status, OrderAction action);

    Template createTemplate(String name, BigDecimal cost, String description);

    Service createService(Customer customer, Template template, ServiceStatus status);

    Area createArea(String name, String description);


    Map<BigInteger, Order> getOrders();

    Map<BigInteger, Template> getTemplates();

    Map<BigInteger, Service> getServices();

    Map<BigInteger, Customer> getCustomers();

    Map<BigInteger, Employee> getEmployees();

    Map<BigInteger, Area> getAreas();


    Order getOrder(BigInteger orderId);

    Template getTemplate(BigInteger templateId);

    Service getService(BigInteger serviceId);

    Customer getCustomer(BigInteger customerId);

    Employee getEmployee(BigInteger employeeId);

    Area getArea(BigInteger areaId);


    void addOrder(Order order);

    void addService(Service service);

    void addTemplate(Template template);

    void addCustomer(Customer customer);

    void addEmployee(Employee employee);

    void addArea(Area area);


    void deleteOrder(BigInteger id);

    void deleteTemplate(BigInteger id);

    void deleteService(BigInteger id);

    void deleteCustomer(BigInteger id);

    void deleteEmployee(BigInteger id);

    void deleteArea(BigInteger id);


    void setOrder(Order order);

    void setTemplate(Template template);

    void setService(Service service);

    void setCustomer(Customer customer);

    void setEmployee(Employee employee);

    void setArea(Area area);


    Area getAreaByName(final String name);

    Customer getCustomerByLogin(final String login);

    Employee getEmployeeByLogin(final String login);

    List<Order> getOrdersByTemplateId(final BigInteger templateId);

    List<Order> getOrdersByServiceId(final BigInteger serviceId);

    List<Order> getOrdersByEmployeeId(final BigInteger employeeId);

    List<Order> getOrdersByStatus(final OrderStatus status);

    List<Order> getOrdersByAction(final OrderAction action);

    List<Service> getServicesByCustomer(Customer customer);

    List<Service> getServicesByTemplate(Template template);

    List<Service> getServicesByStatus(ServiceStatus status);

    List<Template> getTemplatesByArea(Area area);

    Template getTemplateByName(String name);

}
