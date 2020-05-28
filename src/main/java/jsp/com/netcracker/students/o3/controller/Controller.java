package jsp.com.netcracker.students.o3.controller;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectCredentialsException;
import jsp.com.netcracker.students.o3.Exceptions.LoginOccupiedException;
import jsp.com.netcracker.students.o3.Exceptions.UnpossibleChangeAreaException;
import jsp.com.netcracker.students.o3.Exceptions.WrongInputException;
import jsp.com.netcracker.students.o3.model.Entity;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.serialization.JsonEntitiesStorage;
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

/**
 * class contains business logic of program
 */


public interface Controller
{
    /**
     * disconnect service with serviceId
     *
     * @param serviceId - id of service to be disconnected
     */
    void disconnectService(BigInteger serviceId);

    void resumeService(BigInteger serviceId);

    void suspendService(BigInteger serviceId);

    void process(BigInteger serviceId);

    void backToPlanned(BigInteger serviceId);
    void activate(BigInteger serviceId);

    /**
     * start execution of order
     */
    void startOrder(BigInteger orderId, Employee employee);

    /**
     * suspend execution of order
     */
    void suspendOrder(BigInteger orderId);

    /**
     * stop execution of order
     */
    void stopOrder(BigInteger orderId);

    /**
     * complete order
     */
    void completeOrder(BigInteger orderId);

    void completeOrder(BigInteger orderId, Service service);

    void completeOrder(Order order, Service service);

    /**
     * delete of entities
     */
    void deleteArea(BigInteger areaId);

    void deleteOrder(BigInteger orderId);

    void deleteService(BigInteger serviceId);

    void deleteTemplate(BigInteger templateId);

    void deleteCustomer(BigInteger customerId);

    void deleteEmployee(BigInteger employeeId);

    /**
     * delete entities with linked entities
     */
    void deepDeleteArea(BigInteger areaId);

    void deepDeleteOrder(BigInteger orderId);

    void deepDeleteService(BigInteger serviceId);

    void deepDeleteTemplate(BigInteger templateId);

    void deepDeleteCustomer(BigInteger customerId);

    void deepDeleteEmployee(BigInteger employeeId);


    /**
     * create entities
     */
    Customer createCustomer(String name, String login, String password, Area area)
    ;

    Employee createEmployee(String name, String login, String password);

    Order createOrder(Template template, Service service,
            OrderStatus status, OrderAction action);

    Template createTemplate(String name, BigDecimal cost, String description)
            ;

    Service createService(Customer customer, Template template, ServiceStatus status)
            ;

    Area createArea(String name, String description);


    /**
     * @return userId by login and password
     */
    BigInteger getUserIdByCredentials(String login, String password)
            throws IncorrectCredentialsException;

    /**
     * register new Customer
     */
    Customer registerCustomer(String login, String password, String Name, Area area)
            throws LoginOccupiedException;

    /**
     * register new Employee
     */
    Employee registerEmployee(String login, String password, String Name)
            throws LoginOccupiedException;

    /**
     * check equals password and user password
     */
    boolean checkCustomerPassword(BigInteger id, String password);

    /**
     * check existing of login
     */
    boolean isLoginExists(String login);

    /**
     * @return suspended services of customer
     */
    List<Service> getSuspendedServices(BigInteger customerId);

    /**
     * @return entering services of customer
     */
    List<Service> getPlannedServices(BigInteger customerId);

    /**
     * @return active services of customer
     */
    List<Service> getActiveServices(BigInteger customerId);

    /**
     * @return entering and active services of customer
     */
    List<Service> getPlannedAndActiveServices(BigInteger customerId);

    /**
     * @return templates which available to area
     * @param area
     */
    List<Template> getTemplatesByArea(Area area);


    /**
     * @return employee orders
     */
    List<Order> getOrdersByEmployeeId(BigInteger employeeId);

    /**
     * @return customer area id
     */
    Area getCustomerArea(BigInteger customerId);

    /**
     * check user on customer
     */
    boolean isCustomer(BigInteger userId);

    /**
     * check user on employee
     */
    boolean isEmployee(BigInteger userId);


    /**
     * get list of entities
     */
    List<Area> getAreas();

    List<Template> getTemplates();

    List<Service> getServices();

    List<Customer> getCustomers();

    List<Order> getOrders();


    /**
     * get entity by id
     */
    Customer getCustomer(BigInteger userId);

    Employee getEmployee(BigInteger userId);

    Area getArea(BigInteger areaId);

    Template getTemplate(BigInteger templateId);

    Service getService(BigInteger serviceId);

    Order getOrder(BigInteger orderId);

    /**
     * put money on balance
     */
    void putOnBalance(BigInteger customerId, BigDecimal money);


    /**
     * check user by login
     */
    boolean isCustomerLogin(String login);

    boolean isEmployeeLogin(String login);

    /**
     * @return customer balance
     */
    BigDecimal getBalance(BigInteger customerId);

    /**
     * @return customer name
     */
    String getCustomerFio(BigInteger customerId);

    /**
     * @return area name
     */
    String getAreaName(BigInteger customerId);

    /**
     * @return customer available templates
     */
    List<Template> getCustomerAvailableTemplates(BigInteger customerId);

    /**
     * set customer credentials
     */
    void setCustomerName(BigInteger customerId, String name) throws WrongInputException;

    void setUserLogin(BigInteger userId, String login) throws LoginOccupiedException, WrongInputException;

    void setUserPassword(BigInteger userId, String password) throws WrongInputException;

    void setCustomerArea(BigInteger customerId, BigInteger areaId) throws UnpossibleChangeAreaException;

    void setEmployee(Employee employee);


    /**
     * connect service
     */
    void connectService(BigInteger customerId, BigInteger templateId);

    /**
     * @return list of customers service
     * @param customer
     */
    List<Service> getCustomerServices(Customer customer);

    /**
     * @return areas available to customer
     */
    List<Area> getAvailableAreas(BigInteger customerId);

    void resumeOrder(BigInteger orderId);

    List<Service> getPlannedActiveSuspendedProvisioningService(BigInteger customerId);

    String getServiceName(final BigInteger serviceId);

    String getServiceDescription(final BigInteger serviceId);

    List<Employee> getEmployees();


    void setCustomer(Customer customer);

    void setOrder(Order order);

    void setTemplate(Template template);

    void setService(Service service);

    void setArea(Area area);


    Area getAreaByName(final String name);
    Customer getCustomerByLogin(final String login);
    Employee getEmployeeByLogin(final String login);
    List<Order> getOrdersByTemplateId(final BigInteger templateId);
    List<Order> getOrdersByServiceId(final BigInteger serviceId);
    List<Order> getOrdersByStatus(final OrderStatus status);
    List<Order> getOrdersByAction(final OrderAction action);
    List<Service> getServicesByCustomer(Customer customer);
    List<Service> getServicesByTemplate(Template template);
    List<Service> getServicesByStatus(ServiceStatus status);
    List<Service> getServicesByStatusAndCustomerId(BigInteger userId, ServiceStatus status);
    Template getTemplateByName(String name);

    /**
     * dont use it if you need high performance
     * @param entityId
     * @return
     */
    Entity getEntity(BigInteger entityId);

    void importEntities(JsonEntitiesStorage jsonEntitiesStorage, boolean isIgnored);

}
