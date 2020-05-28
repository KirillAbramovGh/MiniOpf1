package jsp.com.netcracker.students.o3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.serialization.Serializer;
import jsp.com.netcracker.students.o3.model.serialization.SerializerImpl;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * GRUD class for entities
 */
public class ModelJson implements Model
{
    private static ModelJson instance;

    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = OrderImpl.class)
    private Map<BigInteger, Order> orders;
    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = TemplateImpl.class)
    private Map<BigInteger, Template> templates;
    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = ServiceImpl.class)
    private Map<BigInteger, Service> services;
    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = CustomerImpl.class)
    private Map<BigInteger, Customer> customers;
    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = EmployeeImpl.class)
    private Map<BigInteger, Employee> employees;
    @JsonDeserialize(as = HashMap.class, keyAs = BigInteger.class, contentAs = AreaImpl.class)
    private Map<BigInteger, Area> areas;

    /**
     * last entity id
     */
    private BigInteger lastId;


    /**
     * method initialize model
     */
    private ModelJson()
    {
        orders = new HashMap<>();
        templates = new HashMap<>();
        services = new HashMap<>();
        customers = new HashMap<>();
        employees = new HashMap<>();
        areas = new HashMap<>();
        lastId = BigInteger.ZERO;
    }

    /**
     * Methods which set Map of entities
     */
    public void setOrders(final Map<BigInteger, Order> orders)
    {
        this.orders = orders;
    }

    public void setTemplates(final Map<BigInteger, Template> templates)
    {
        this.templates = templates;
    }

    public void setServices(final Map<BigInteger, Service> services)
    {
        this.services = services;
    }

    public void setCustomers(final Map<BigInteger, Customer> customers)
    {
        this.customers = customers;
    }

    public void setEmployees(final Map<BigInteger, Employee> employees)
    {
        this.employees = employees;
    }

    public void setAreas(final Map<BigInteger, Area> areas)
    {
        this.areas = areas;
    }


    /**
     * return set and return instance
     *
     * @return instance
     */
    public static ModelJson getInstance()
    {
        if (instance == null)
        {
            instance = new ModelJson();
        }
        return instance;
    }


    /**
     * @return lastId
     */
    public BigInteger getLastId()
    {
        return lastId;
    }


    public void setLastId(final BigInteger lastId)
    {
        this.lastId = lastId;
    }


    /**
     * method increment lastId and return
     *
     * @return lastId
     */
    @JsonIgnore
    public BigInteger getNextId()
    {
        synchronized (lastId)
        {
            lastId = lastId.add(BigInteger.ONE);
            return lastId;
        }
    }


    /**
     * methods which create entities by credentials
     *
     * @return id of created entity
     */
    public Customer createCustomer(final String name, final String login, final String password, final Area area)
    {
        synchronized (customers)
        {
            Customer newCustomer = new CustomerImpl(getNextId(), name, login, password, area);
            addCustomer(newCustomer);
            return newCustomer;
        }
    }

    public Employee createEmployee(String name, String login, String password)
    {
        synchronized (employees)
        {
            Employee newEmployee = new EmployeeImpl(getNextId(), name, login, password);
            addEmployee(newEmployee);

            return newEmployee;
        }
    }


    public Order createOrder(final Template template, final Service service, final OrderStatus status,
            final OrderAction action)
    {
        synchronized (orders)
        {
            Order newOrder = new OrderImpl(getNextId(), template, service, status, action);
            newOrder.setCreationDate(new Date());
            addOrder(newOrder);
            return newOrder;
        }
    }

    public Template createTemplate(String name, BigDecimal cost, String description)
    {
        synchronized (templates)
        {
            Template newTemplate = new TemplateImpl(getNextId(), name, cost, description);
            BigInteger templateId = newTemplate.getId();

            addTemplate(newTemplate);
            return newTemplate;
        }
    }


    @Override
    public Service createService(Customer customer, Template template, ServiceStatus status)
    {
        synchronized (services)
        {
            Service newService = new ServiceImpl(getNextId(), customer, template, status);
            addService(newService);
            return newService;
        }
    }

    public Area createArea(String name, String description)
    {
        synchronized (areas)
        {
            Area newArea = new AreaImpl(getNextId(), name, description);
            addArea(newArea);
            return newArea;
        }
    }


    /**
     * methods return map of entities
     *
     * @return map of entities
     */
    public Map<BigInteger, Order> getOrders()
    {
        return orders;
    }

    public Map<BigInteger, Template> getTemplates()
    {
        return templates;
    }

    public Map<BigInteger, Service> getServices()
    {
        return services;
    }

    public Map<BigInteger, Customer> getCustomers()
    {
        return customers;
    }

    public Map<BigInteger, Employee> getEmployees()
    {
        return employees;
    }

    public Map<BigInteger, Area> getAreas()
    {
        return areas;
    }


    /**
     * methods which return entity by id
     *
     * @return entity
     */
    public Order getOrder(BigInteger orderId)
    {
        return orders.get(orderId);
    }

    public Template getTemplate(BigInteger templateId)
    {
        return templates.get(templateId);
    }

    public Service getService(BigInteger serviceId)
    {
        return services.get(serviceId);
    }

    public Customer getCustomer(BigInteger customerId)
    {
        return customers.get(customerId);
    }

    public Employee getEmployee(BigInteger employeeId)
    {
        return employees.get(employeeId);
    }

    public Area getArea(BigInteger areaId)
    {
        return areas.get(areaId);
    }


    /**
     * methods which add entity to model
     */
    public void addOrder(Order order)
    {
        synchronized (orders)
        {
            orders.put(order.getId(), order);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }

    public void addService(Service service)
    {
        synchronized (services)
        {
            services.put(service.getId(), service);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }

    public void addTemplate(Template template)
    {
        synchronized (templates)
        {
            templates.put(template.getId(), template);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }

    public void addCustomer(Customer customer)
    {
        synchronized (customers)
        {
            customers.put(customer.getId(), customer);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }

    public void addEmployee(Employee employee)
    {
        synchronized (employees)
        {
            employees.put(employee.getId(), employee);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }

    public void addArea(Area area)
    {
        synchronized (areas)
        {
            areas.put(area.getId(), area);
        }
        synchronized (this)
        {
            onDataChange();
        }
    }


    /**
     * delete entity by id
     */
    public void deleteOrder(BigInteger id)
    {
        synchronized (orders)
        {
            orders.remove(id);
        }
        onDataChange();
    }

    public void deleteTemplate(BigInteger id)
    {
        synchronized (templates)
        {
            templates.remove(id);
        }
        onDataChange();
    }

    public void deleteService(BigInteger id)
    {
        synchronized (services)
        {
            services.remove(id);
        }
        onDataChange();
    }

    public void deleteCustomer(BigInteger id)
    {
        synchronized (customers)
        {
            customers.remove(id);
        }
        onDataChange();
    }

    public void deleteEmployee(BigInteger id)
    {
        synchronized (employees)
        {
            employees.remove(id);
        }
        onDataChange();
    }

    public void deleteArea(BigInteger id)
    {
        synchronized (areas)
        {
            areas.remove(id);
        }
        onDataChange();
    }


    /**
     * methods update entities
     */
    public void setOrder(Order order)
    {
        synchronized (orders)
        {
            orders.put(order.getId(), order);
        }
        onDataChange();
    }

    public void setTemplate(Template template)
    {
        synchronized (templates)
        {
            templates.put(template.getId(), template);
        }
        onDataChange();
    }

    public void setService(Service service)
    {
        synchronized (services)
        {
            services.put(service.getId(), service);
        }
        onDataChange();
    }

    public void setCustomer(Customer customer)
    {
        synchronized (customers)
        {
            customers.put(customer.getId(), customer);
        }
        onDataChange();
    }

    public void setEmployee(Employee employee)
    {
        synchronized (employees)
        {
            employees.put(employee.getId(), employee);
        }
        onDataChange();
    }

    public void setArea(Area area)
    {
        synchronized (areas)
        {
            areas.put(area.getId(), area);
        }
        onDataChange();
    }

    @Override
    public Area getAreaByName(final String name)
    {
        return null;
    }

    @Override
    public Customer getCustomerByLogin(final String login)
    {
        return null;
    }

    @Override
    public Employee getEmployeeByLogin(final String login)
    {
        return null;
    }

    @Override
    public List<Order> getOrdersByTemplateId(final BigInteger templateId)
    {
        return null;
    }

    @Override
    public List<Order> getOrdersByServiceId(final BigInteger serviceId)
    {
        return null;
    }

    @Override
    public List<Order> getOrdersByEmployeeId(final BigInteger employeeId)
    {
        return null;
    }

    @Override
    public List<Order> getOrdersByStatus(final OrderStatus status)
    {
        return null;
    }

    @Override
    public List<Order> getOrdersByAction(final OrderAction action)
    {
        return null;
    }

    @Override
    public List<Service> getServicesByCustomer(final Customer customer)
    {
        return null;
    }

    @Override
    public List<Service> getServicesByTemplate(final Template template)
    {
        return null;
    }


    @Override
    public List<Service> getServicesByStatus(final ServiceStatus status)
    {
        return null;
    }

    @Override
    public List<Template> getTemplatesByArea(final Area area)
    {
        return null;
    }


    @Override
    public Template getTemplateByName(final String name)
    {
        return null;
    }


    /**
     * on data change save model
     */
    private void onDataChange()
    {
        synchronized (this)
        {
            Serializer serializer = new SerializerImpl();
            try
            {
                serializer.serializeModel(this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}