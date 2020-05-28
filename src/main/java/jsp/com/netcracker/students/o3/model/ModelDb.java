package jsp.com.netcracker.students.o3.model;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.dao.area.AreaDao;
import jsp.com.netcracker.students.o3.model.dao.area.AreaHibDao;
import jsp.com.netcracker.students.o3.model.dao.сustomer.CustomerDao;
import jsp.com.netcracker.students.o3.model.dao.сustomer.CustomerHibDao;
import jsp.com.netcracker.students.o3.model.dao.employee.EmployeeDao;
import jsp.com.netcracker.students.o3.model.dao.employee.EmployeeHibDao;
import jsp.com.netcracker.students.o3.model.dao.order.OrderDao;
import jsp.com.netcracker.students.o3.model.dao.order.OrderHibDao;
import jsp.com.netcracker.students.o3.model.dao.service.ServiceDao;
import jsp.com.netcracker.students.o3.model.dao.service.ServiceHibDao;
import jsp.com.netcracker.students.o3.model.dao.template.TemplateDao;
import jsp.com.netcracker.students.o3.model.dao.template.TemplateHibDao;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelDb implements Model
{
    private final OrderDao orderDao;
    private final TemplateDao templateDao;
    private final ServiceDao serviceDao;
    private final CustomerDao customerDao;
    private final EmployeeDao employeeDao;
    private final AreaDao areaDao;

    private static ModelDb instance;

    private ModelDb()
    {
        orderDao = new OrderHibDao();
        templateDao = new TemplateHibDao();
        serviceDao = new ServiceHibDao();
        customerDao = new CustomerHibDao();
        employeeDao = new EmployeeHibDao();
        areaDao = new AreaHibDao();
    }

    @Override
    public void setOrders(final Map<BigInteger, Order> orders)
    {
        synchronized (orderDao)
        {
            for (Order order : orders.values())
            {
                try
                {
                    orderDao.create(order);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setTemplates(final Map<BigInteger, Template> templates)
    {
        synchronized (templateDao)
        {
            for (Template template : templates.values())
            {
                try
                {
                    templateDao.create(template);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setServices(final Map<BigInteger, Service> services)
    {
        synchronized (serviceDao)
        {
            for (Service service : services.values())
            {
                try
                {
                    serviceDao.create(service);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setCustomers(final Map<BigInteger, Customer> customers)
    {
        synchronized (customerDao)
        {
            for (Customer customer : customers.values())
            {
                try
                {
                    customerDao.create(customer);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setEmployees(final Map<BigInteger, Employee> employees)
    {
        synchronized (employeeDao)
        {
            for (Employee employee : employees.values())
            {
                try
                {
                    employeeDao.create(employee);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setAreas(final Map<BigInteger, Area> areas)
    {
        synchronized (areaDao)
        {
            for (Area area : areas.values())
            {
                try
                {
                    areaDao.create(area);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Deprecated
    public BigInteger getLastId()
    {
        return null;
    }

    @Deprecated
    @Override
    public void setLastId(final BigInteger lastId)
    {

    }

    @Deprecated
    @Override
    public BigInteger getNextId()
    {
        return null;
    }

    @Override
    public Customer createCustomer(final String name, final String login, final String password,
            final Area area)
    {
        synchronized (customerDao)
        {
            Customer newCustomer = new CustomerImpl(null, name, login, password, area);
            addCustomer(newCustomer);

            return newCustomer;
        }
    }

    @Override
    public Employee createEmployee(final String name, final String login, final String password)

    {
        synchronized (employeeDao)
        {
            Employee newEmployee = new EmployeeImpl(null, name, login, password);
            addEmployee(newEmployee);

            return newEmployee;
        }
    }

    @Override
    public Order createOrder(final Template template, final Service service, final OrderStatus status,
            final OrderAction action)
    {
        synchronized (orderDao)
        {
            Order newOrder = new OrderImpl(null, template, service, status, action);
            newOrder.setCreationDate(new Date());
            addOrder(newOrder);

            return newOrder;
        }
    }

    @Override
    public Template createTemplate(final String name, final BigDecimal cost, final String description)

    {
        synchronized (templateDao)
        {
            Template newTemplate = new TemplateImpl(null, name, cost, description);
            addTemplate(newTemplate);

            return newTemplate;
        }
    }

    @Override
    public Service createService(final Customer customer, final Template template, final ServiceStatus status)

    {
        synchronized (serviceDao)
        {
            Service newService = new ServiceImpl(null, customer, template, status);
            addService(newService);

            return newService;
        }
    }

    @Override
    public Area createArea(final String name, final String description)

    {
        synchronized (areaDao)
        {
            Area newArea = new AreaImpl(null, name, description);
            addArea(newArea);

            return newArea;
        }
    }

    @Override
    public Map<BigInteger, Order> getOrders()
    {
        Map<BigInteger, Order> orderMap = new HashMap<>();
        try
        {
            for (Order order : orderDao.getAll())
            {
                if (order != null && order.getId() != null)
                {
                    orderMap.put(order.getId(), order);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return orderMap;
    }

    @Override
    public Map<BigInteger, Template> getTemplates()
    {
        Map<BigInteger, Template> templateMap = new HashMap<>();
        try
        {
            for (Template template : templateDao.getAll())
            {
                if (template != null && template.getName() != null)
                {
                    templateMap.put(template.getId(), template);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return templateMap;
    }

    @Override
    public Map<BigInteger, Service> getServices()
    {
        Map<BigInteger, Service> serviceMap = new HashMap<>();
        try
        {
            for (Service service : serviceDao.getAll())
            {
                if (service != null && service.getStatus() != null)
                {
                    serviceMap.put(service.getId(), service);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return serviceMap;
    }

    @Override
    public Map<BigInteger, Customer> getCustomers()
    {
        Map<BigInteger, Customer> serviceMap = new HashMap<>();
        try
        {
            for (Customer customer : customerDao.getAll())
            {
                if (customer != null && customer.getName() != null)
                {
                    serviceMap.put(customer.getId(), customer);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return serviceMap;
    }

    @Override
    public Map<BigInteger, Employee> getEmployees()
    {
        Map<BigInteger, Employee> employeeMap = new HashMap<>();
        try
        {
            for (Employee employee : employeeDao.getAll())
            {
                if (employee != null && employee.getName() != null)
                {
                    employeeMap.put(employee.getId(), employee);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return employeeMap;
    }

    @Override
    public Map<BigInteger, Area> getAreas()
    {
        Map<BigInteger, Area> areaMap = new HashMap<>();
        try
        {
            for (Area area : areaDao.getAll())
            {
                if (area != null && area.getName() != null)
                {
                    areaMap.put(area.getId(), area);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return areaMap;
    }

    @Override
    public Order getOrder(final BigInteger orderId)
    {
        try
        {
            Order order = orderDao.getEntity(orderId);
            if (order != null && order.getService() == null)
            {
                order = null;
            }
            return order;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Template getTemplate(final BigInteger templateId)
    {
        try
        {
            Template template = templateDao.getEntity(templateId);
            if (template != null && template.getName() == null)
            {
                template = null;
            }
            return template;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Service getService(final BigInteger serviceId)
    {
        try
        {
            Service service = serviceDao.getEntity(serviceId);
            if (service != null && service.getStatus() == null)
            {
                service = null;
            }
            return service;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Customer getCustomer(final BigInteger customerId)
    {
        try
        {
            Customer customer = customerDao.getEntity(customerId);
            if (customer != null && customer.getName() == null)
            {
                customer = null;
            }
            return customer;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee getEmployee(final BigInteger employeeId)
    {
        try
        {
            Employee employee = employeeDao.getEntity(employeeId);
            if (employee != null && employee.getName() == null)
            {
                employee = null;
            }

            return employee;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Area getArea(final BigInteger areaId)
    {
        try
        {
            Area area = areaDao.getEntity(areaId);
            if (area != null && area.getName() == null)
            {
                area = null;
            }
            return area;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addOrder(final Order order)
    {
        synchronized (areaDao)
        {
            try
            {
                orderDao.create(order);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addService(final Service service)
    {
        synchronized (serviceDao)
        {
            try
            {
                serviceDao.create(service);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addTemplate(final Template template)
    {
        synchronized (templateDao)
        {
            try
            {
                templateDao.create(template);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addCustomer(final Customer customer)
    {
        synchronized (customerDao)
        {
            try
            {
                customerDao.create(customer);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addEmployee(final Employee employee)
    {
        synchronized (employeeDao)
        {
            try
            {
                employeeDao.create(employee);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addArea(final Area area)
    {
        synchronized (areaDao)
        {
            try
            {
                areaDao.create(area);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteOrder(final BigInteger id)
    {
        synchronized (orderDao)
        {
            try
            {
                orderDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTemplate(final BigInteger id)
    {
        synchronized (templateDao)
        {
            try
            {
                templateDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteService(final BigInteger id)
    {
        synchronized (serviceDao)
        {
            try
            {
                serviceDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteCustomer(final BigInteger id)
    {
        synchronized (customerDao)
        {
            try
            {
                customerDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteEmployee(final BigInteger id)
    {
        synchronized (employeeDao)
        {
            try
            {
                employeeDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteArea(final BigInteger id)
    {
        synchronized (areaDao)
        {
            try
            {
                areaDao.delete(id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setOrder(final Order order)
    {
        synchronized (orderDao)
        {
            try
            {
                orderDao.update(order);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setTemplate(final Template template)
    {
        synchronized (templateDao)
        {
            try
            {
                templateDao.update(template);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setService(final Service service)
    {
        synchronized (serviceDao)
        {
            try
            {
                serviceDao.update(service);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCustomer(final Customer customer)
    {
        synchronized (customerDao)
        {
            try
            {
                customerDao.update(customer);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setEmployee(final Employee employee)
    {
        synchronized (employeeDao)
        {
            try
            {
                employeeDao.update(employee);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setArea(final Area area)
    {
        synchronized (areaDao)
        {
            try
            {
                areaDao.update(area);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public Area getAreaByName(final String name)
    {
        try
        {
            return  areaDao.getAreaByName(name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Customer getCustomerByLogin(final String login)
    {
        try
        {
            return customerDao.getCustomerByLogin(login);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByLogin(final String login)
    {
        try
        {
            return  employeeDao.getEmployeeByLogin(login);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getOrdersByTemplateId(final BigInteger templateId)
    {
        try
        {
            return orderDao.getOrdersByTemplateId(templateId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getOrdersByServiceId(final BigInteger serviceId)
    {
        try
        {
            return orderDao.getOrdersByServiceId(serviceId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getOrdersByEmployeeId(final BigInteger employeeId)
    {
        try
        {
            return orderDao.getOrdersByEmployeeId(employeeId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getOrdersByStatus(final OrderStatus status)
    {
        try
        {
            return  orderDao.getOrdersByStatus(status);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getOrdersByAction(final OrderAction action)
    {
        try
        {
            return  orderDao.getOrdersByAction(action);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Service> getServicesByCustomer(Customer customer)
    {
        try
        {
            return serviceDao.getServicesByCustomer(customer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Service> getServicesByTemplate(Template template)
    {
        try
        {
            return serviceDao.getServicesByTemplate(template);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Service> getServicesByStatus(ServiceStatus status)
    {
        try
        {
            return serviceDao.getServicesByStatus(status);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public List<Template> getTemplatesByArea(Area area)
    {
        return area.getTemplates();
    }

    public Template getTemplateByName(String name)
    {
        try
        {
            return templateDao.getTemplateByName(name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static synchronized ModelDb getInstance()
    {
        if (instance == null)
        {
            instance = new ModelDb();
        }

        return instance;
    }
}
