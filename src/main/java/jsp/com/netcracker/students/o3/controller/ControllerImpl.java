package jsp.com.netcracker.students.o3.controller;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectCredentialsException;
import jsp.com.netcracker.students.o3.Exceptions.LoginOccupiedException;
import jsp.com.netcracker.students.o3.Exceptions.WrongInputException;
import jsp.com.netcracker.students.o3.controller.sorters.ServiceSorter;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType;
import jsp.com.netcracker.students.o3.model.Entity;
import jsp.com.netcracker.students.o3.model.Model;
import jsp.com.netcracker.students.o3.model.ModelDb;
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
import jsp.com.netcracker.students.o3.model.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerImpl implements Controller
{
    private final Model model;
    private static ControllerImpl instance;
    private final ServiceSorter serviceSorter;

    private ControllerImpl()
    {
        model = ModelDb.getInstance();
        serviceSorter = ServiceSorter.getInstance();
        takeMoney(new Timer());
    }

    private ControllerImpl(Model model)
    {
        this.model = model;
        serviceSorter = ServiceSorter.getInstance();
        // takeMoney(new Timer());
    }


    @Override
    public void startOrder(final BigInteger orderId, final Employee employee)
    {
        Order order = getOrder(orderId);
        order.setEmployee(employee);
        order.setStatus(OrderStatus.Processing);
        Service service = order.getService();
        service.setStatus(ServiceStatus.Provisioning);
        model.setOrder(order);
        model.setService(service);
    }

    @Override
    public void suspendOrder(final BigInteger orderId)
    {
        Order order = getOrder(orderId);
        order.setEmployee(null);
        order.setStatus(OrderStatus.Entering);
        Service service = order.getService();
        switch (order.getAction())
        {
            case Suspend:
                service.setStatus(ServiceStatus.Active); break;
            case Resume:
            case Disconnect:
                service.setStatus(ServiceStatus.Suspended); break;
            case New:
                service.setStatus(ServiceStatus.Planned); break;
        }
        model.setOrder(order);
        model.setService(service);
    }

    @Override
    public void stopOrder(final BigInteger orderId)
    {

    }


    @Override
    public void completeOrder(final BigInteger orderId)
    {
        Order order = getOrder(orderId);
        Service service = order.getService();
        completeOrder(order, service);
    }

    @Override
    public void completeOrder(final BigInteger orderId, final Service service)
    {
        Order order = getOrder(orderId);
        completeOrder(order, service);
    }

    @Override
    public void completeOrder(final Order order, final Service service)
    {
        switch (order.getAction())
        {
            case New:
            case Resume:
                service.setStatus(ServiceStatus.Active);
                break;
            case Suspend:
                service.setStatus(ServiceStatus.Suspended);
                break;
            case Disconnect:
                service.setStatus(ServiceStatus.Disconnected);
                break;
        }
        order.setStatus(OrderStatus.Completed);
        model.setOrder(order);
        model.setService(service);
    }


    @Override
    public void deleteArea(final BigInteger areaId)
    {
        model.deleteArea(areaId);
    }

    @Override
    public void deleteOrder(final BigInteger orderId)
    {
        model.deleteOrder(orderId);
    }

    @Override
    public void deleteService(final BigInteger serviceId)
    {
        model.deleteService(serviceId);
    }

    @Override
    public void deleteTemplate(final BigInteger templateId)
    {
        model.deleteTemplate(templateId);
    }

    @Override
    public void deleteCustomer(final BigInteger customerId)
    {
        model.deleteCustomer(customerId);
    }

    @Override
    public void deleteEmployee(final BigInteger employeeId)
    {
        model.deleteEmployee(employeeId);
    }

    @Override
    public void deepDeleteArea(final BigInteger areaId)
    {
        List<Template> templates = getArea(areaId).getTemplates();
        for (Template template : templates)
        {
            List<Area> possibleAreas = template.getPossibleAreas();
            for (Area area : possibleAreas)
            {
                if (area != null && area.getId().equals(areaId))
                {
                    possibleAreas.remove(area);
                    break;
                }
            }
            template.setPossibleAreas(possibleAreas);
            model.setTemplate(template);
        }

        List<Customer> customers = getCustomers();
        for (Customer customer : customers)
        {
            if (customer.getArea().equals(areaId))
            {
                deepDeleteCustomer(customer.getId());
            }
        }
        model.deleteArea(areaId);
    }

    @Override
    public void deepDeleteOrder(final BigInteger orderId)
    {
        Order order = getOrder(orderId);
        Service service = order.getService();
        model.deleteOrder(orderId);
        model.deleteService(service.getId());
    }

    @Override
    public void deepDeleteService(final BigInteger serviceId)
    {
        for (Order order : getOrdersByServiceId(serviceId))
        {
            model.deleteOrder(order.getId());
        }
        model.deleteService(serviceId);
    }

    @Override
    public void deepDeleteTemplate(final BigInteger templateId)
    {
        List<Service> services = getServices();

        for (Service service : services)
        {
            if (service.getTemplate().equals(templateId))
            {
                deepDeleteService(service.getId());
            }
        }

        model.deleteTemplate(templateId);
    }

    @Override
    public void deepDeleteCustomer(final BigInteger customerId)
    {
        Set<Service> services = getCustomer(customerId).getConnectedServices();
        for (Service service : services)
        {
            deepDeleteService(service.getId());
        }
        model.deleteCustomer(customerId);
    }

    @Override
    public void deepDeleteEmployee(final BigInteger employeeId)
    {
        List<Order> orders = getOrdersByEmployeeId(employeeId);
        for (Order order : orders)
        {
            deepDeleteOrder(order.getId());
        }

        model.deleteEmployee(employeeId);
    }

    @Override
    public Customer createCustomer(final String name, final String login, final String password,
            final Area area)
    {
        return model.createCustomer(name, login, password, area);
    }

    @Override
    public Employee createEmployee(final String name, final String login, final String password)

    {
        return model.createEmployee(name, login, password);
    }

    @Override
    public Order createOrder(final Template template, final Service service,
            final OrderStatus status, final OrderAction action)

    {
        return model.createOrder(template, service, status, action);
    }

    @Override
    public Template createTemplate(final String name, final BigDecimal cost, final String description)

    {
        return model.createTemplate(name, cost, description);
    }

    @Override
    public Service createService(final Customer customer, final Template template, final ServiceStatus status)

    {
        return model.createService(customer, template, status);
    }

    @Override
    public Area createArea(final String name, final String description)

    {
        return model.createArea(name, description);
    }

    @Override
    public BigInteger getUserIdByCredentials(final String login, final String password)
            throws IncorrectCredentialsException
    {
        Customer customer = model.getCustomerByLogin(login);
        if (customer != null && customer.getPassword().equals(password))
        {
            return customer.getId();
        }
        Employee employee = model.getEmployeeByLogin(login);
        if (employee != null && employee.getPassword().equals(password))
        {
            return employee.getId();
        }

        throw new IncorrectCredentialsException("Неправильный логин или пароль!");
    }

    public List<Employee> getEmployees()
    {
        return new ArrayList<>(model.getEmployees().values());
    }

    @Override
    public void setCustomer(final Customer customer)
    {
        model.setCustomer(customer);
    }

    @Override
    public void setOrder(final Order order)
    {
        model.setOrder(order);
    }

    @Override
    public void setTemplate(final Template template)
    {
        model.setTemplate(template);
    }

    @Override
    public void setService(final Service service)
    {
        model.setService(service);
    }

    @Override
    public void setArea(final Area area)
    {
        model.setArea(area);
    }

    @Override
    public Area getAreaByName(final String name)
    {
        return model.getAreaByName(name);
    }

    @Override
    public Customer getCustomerByLogin(final String login)
    {
        return model.getCustomerByLogin(login);
    }

    @Override
    public Employee getEmployeeByLogin(final String login)
    {
        return model.getEmployeeByLogin(login);
    }

    @Override
    public List<Order> getOrdersByTemplateId(final BigInteger templateId)
    {
        return model.getOrdersByTemplateId(templateId);
    }

    @Override
    public List<Order> getOrdersByServiceId(final BigInteger serviceId)
    {
        return model.getOrdersByServiceId(serviceId);
    }

    @Override
    public List<Order> getOrdersByStatus(final OrderStatus status)
    {
        return model.getOrdersByStatus(status);
    }

    @Override
    public List<Order> getOrdersByAction(final OrderAction action)
    {
        return model.getOrdersByAction(action);
    }

    @Override
    public List<Service> getServicesByCustomer(final Customer customer)
    {
        return model.getServicesByCustomer(customer);
    }

    @Override
    public List<Service> getServicesByTemplate(final Template template)
    {
        return model.getServicesByTemplate(template);
    }

    @Override
    public List<Service> getServicesByStatus(final ServiceStatus status)
    {
        return model.getServicesByStatus(status);
    }

    @Override
    public List<Service> getServicesByStatusAndCustomerId(final BigInteger userId, final ServiceStatus status)
    {
        Set<Service> services = model.getCustomer(userId).getConnectedServices();
        List<Service> result = new ArrayList<>();
        for (Service service : services)
        {
            if (service.getStatus().equals(status))
            {
                result.add(service);
            }
        }
        return result;
    }


    @Override
    public Template getTemplateByName(final String name)
    {
        return model.getTemplateByName(name);
    }

    @Override
    public Entity getEntity(final BigInteger entityId)
    {
        Entity result = getArea(entityId);
        if (result == null)
        {
            result = getTemplate(entityId);
        }
        if (result == null)
        {
            result = getEmployee(entityId);
        }
        if (result == null)
        {
            result = getCustomer(entityId);
        }
        if (result == null)
        {
            result = getService(entityId);
        }
        if (result == null)
        {
            result = getOrder(entityId);
        }

        return result;
    }

    @Override
    public void importEntities(JsonEntitiesStorage jsonEntitiesStorage, boolean isIgnored)
    {
        importEmployees(new ArrayList<>(jsonEntitiesStorage.getEmployees()), isIgnored);
        importAreas(new ArrayList<>(jsonEntitiesStorage.getAreas()), isIgnored);
        importTemplates(new ArrayList<>(jsonEntitiesStorage.getTemplates()), isIgnored);
        importServices(new ArrayList<>(jsonEntitiesStorage.getServices()), isIgnored);
        importCustomers(new ArrayList<>(jsonEntitiesStorage.getCustomers()), isIgnored);
        importOrders(new ArrayList<>(jsonEntitiesStorage.getOrders()), isIgnored);
    }


    private void importOrders(List<Order> orders, boolean isIgnored)
    {
        for (Order order : orders)
        {
            if (getEntity(order.getId()) == null)
            {
                model.addOrder(order);
            }
            else if (!isIgnored)
            {
                model.setOrder(order);
            }
        }
    }

    private void importCustomers(List<Customer> customers, boolean isIgnored)
    {
        for (Customer customer : customers)
        {
            if (getEntity(customer.getId()) == null)
            {
                model.addCustomer(customer);
            }
            else if (!isIgnored)
            {
                model.setCustomer(customer);
            }
        }
    }

    private void importEmployees(List<Employee> employees, boolean isIgnored)
    {
        for (Employee employee : employees)
        {
            if (getEntity(employee.getId()) == null)
            {
                model.addEmployee(employee);
            }
            else if (!isIgnored)
            {
                model.setEmployee(employee);
            }
        }
    }

    private void importAreas(List<Area> areas, boolean isIgnored)
    {
        for (Area area : areas)
        {
            if (getEntity(area.getId()) == null)
            {
                model.addArea(area);
            }
            else if (!isIgnored)
            {
                model.setArea(area);
            }
        }
    }

    private void importServices(List<Service> services, boolean isIgnored)
    {
        for (Service service : services)
        {
            if (getEntity(service.getId()) == null)
            {
                model.addService(service);
            }
            else if (!isIgnored)
            {
                model.setService(service);
            }
        }
    }

    private void importTemplates(List<Template> templates, boolean isIgnored)
    {
        for (Template template : templates)
        {
            if (getEntity(template.getId()) == null)
            {
                model.addTemplate(template);
            }
            else if (!isIgnored)
            {
                model.setTemplate(template);
            }
        }
    }


    @Override
    public Customer registerCustomer(final String login, final String password, final String name,
            final Area area)
            throws LoginOccupiedException
    {
        if (!isLoginExists(login))
        {
            return createCustomer(name, login, password, area);
        }
        else
        {
            throw new LoginOccupiedException("Login occupied");
        }
    }

    @Override
    public Employee registerEmployee(final String login, final String password, final String Name)
            throws LoginOccupiedException
    {
        if (!isLoginExists(login))
        {
            return createEmployee(Name, login, password);
        }

        throw new LoginOccupiedException("Login occupied");
    }

    @Override
    public boolean checkCustomerPassword(final BigInteger customerId, final String password)
    {
        return model.getCustomer(customerId).getPassword().equals(password);
    }

    @Override
    public boolean isLoginExists(final String login)
    {

        return isEmployeeLogin(login) || isCustomerLogin(login);
    }

    @Override
    public List<Service> getSuspendedServices(final BigInteger customerId)
    {
        Set<Service> services = model.getCustomer(customerId).getConnectedServices();
        List<Service> result = new ArrayList<>();
        for (Service service : services)
        {
            if (service.getStatus().equals(ServiceStatus.Suspended))
            {
                result.add(service);
            }
        }
        return getServicesByStatusAndCustomerId(customerId, ServiceStatus.Suspended);
    }

    @Override
    public List<Service> getPlannedServices(final BigInteger customerId)
    {
        return getServicesByStatusAndCustomerId(customerId, ServiceStatus.Planned);
    }


    @Override
    public List<Service> getActiveServices(final BigInteger customerId)
    {
        return getServicesByStatusAndCustomerId(customerId, ServiceStatus.Active);
    }

    @Override
    public List<Service> getPlannedAndActiveServices(final BigInteger customerId)
    {
        List<Service> services = new ArrayList<>(getPlannedServices(customerId));
        services.addAll(getActiveServices(customerId));
        return services;
    }


    @Override
    public List<Template> getTemplatesByArea(final Area area)
    {
        return model.getTemplatesByArea(area);
    }


    @Override
    public List<Order> getOrdersByEmployeeId(final BigInteger employeeId)
    {
        return model.getOrdersByEmployeeId(employeeId);
    }

    @Override
    public Area getCustomerArea(final BigInteger customerId)
    {
        return model.getCustomer(customerId).getArea();
    }

    @Override
    public boolean isCustomer(final BigInteger userId)
    {
        return model.getCustomer(userId) != null;
    }

    @Override
    public boolean isEmployee(final BigInteger userId)
    {
        return model.getEmployee(userId) != null;
    }

    @Override
    public Customer getCustomer(final BigInteger id)
    {
        return model.getCustomer(id);
    }

    @Override
    public Employee getEmployee(final BigInteger userId)
    {
        return model.getEmployee(userId);
    }

    @Override
    public List<Area> getAreas()
    {
        return new ArrayList<>(model.getAreas().values());
    }

    @Override
    public List<Template> getTemplates()
    {
        return new ArrayList<>(model.getTemplates().values());
    }

    @Override
    public List<Service> getServices()
    {
        return new ArrayList<>(model.getServices().values());
    }

    @Override
    public List<Customer> getCustomers()
    {
        return new ArrayList<>(model.getCustomers().values());
    }

    @Override
    public List<Order> getOrders()
    {
        return new ArrayList<>(model.getOrders().values());
    }


    @Override
    public Area getArea(final BigInteger areaId)
    {
        return model.getArea(areaId);
    }

    @Override
    public Template getTemplate(final BigInteger templateId)
    {
        return model.getTemplate(templateId);
    }

    @Override
    public Service getService(final BigInteger serviceId)
    {
        return model.getService(serviceId);
    }

    @Override
    public Order getOrder(final BigInteger orderId)
    {
        return model.getOrder(orderId);
    }

    @Override
    public void putOnBalance(final BigInteger customerId, final BigDecimal money)
    {
        Customer customer = model.getCustomer(customerId);
        BigDecimal currentMoney = customer.getMoneyBalance();
        customer.setMoneyBalance(currentMoney.add(money));

        model.setCustomer(customer);
    }


    @Override
    public boolean isCustomerLogin(final String login)
    {
        return model.getCustomerByLogin(login) != null;
    }

    @Override
    public boolean isEmployeeLogin(final String login)
    {
        return model.getEmployeeByLogin(login) != null;
    }

    public static Controller getInstance()
    {
        if (instance == null)
        {
            instance = new ControllerImpl();
        }

        return instance;
    }

    public static ControllerImpl getInstance(Model model)
    {
        if (instance == null)
        {
            instance = new ControllerImpl(model);
        }

        return instance;
    }

    public BigDecimal getBalance(BigInteger customerId)
    {
        return getCustomer(customerId).getMoneyBalance();
    }

    public String getCustomerFio(BigInteger customerId)
    {
        return getCustomer(customerId).getName();
    }

    public String getAreaName(BigInteger customerId)
    {
        return getCustomer(customerId).getArea().getName();
    }

    public List<Template> getCustomerAvailableTemplates(BigInteger customerId)
    {
        return getTemplatesByArea(getCustomerArea(customerId));
    }

    public void setCustomerName(BigInteger customerId, String name) throws WrongInputException
    {
        if (!name.isEmpty())
        {
            Customer customer = getCustomer(customerId);
            customer.setName(name);
            model.setCustomer(customer);
        }
        else
        {
            throw new WrongInputException("Имя не может быть пустым!");
        }
    }

    public void setUserLogin(BigInteger userId, String login) throws LoginOccupiedException, WrongInputException
    {
        if (!login.isEmpty())
        {
            if (!isLoginExists(login))
            {
                User user = getUser(userId);
                user.setLogin(login);
                if (isCustomer(userId))
                {
                    model.setCustomer((Customer) user);
                }
                else if (isEmployee(userId))
                {
                    model.setEmployee((Employee) user);
                }
            }
            else
            {
                if (!getUser(userId).getLogin().equals(login))
                {
                    throw new LoginOccupiedException("Логин занят");
                }
            }
        }
        else
        {
            throw new WrongInputException("Логин не может быть пустым");
        }
    }

    private User getUser(BigInteger userId)
    {
        User user = getCustomer(userId);

        if (user == null)
        {
            user = getEmployee(userId);
        }

        return user;
    }

    public void setUserPassword(BigInteger userId, String password) throws WrongInputException
    {
        if (!password.isEmpty())
        {
            User user = getUser(userId);
            user.setPassword(password);
            if (isCustomer(userId))
            {
                model.setCustomer((Customer) user);
            }
            else if (isEmployee(userId))
            {
                model.setEmployee((Employee) user);
            }
        }
        else
        {
            throw new WrongInputException("Пароль не может быть пустым");
        }
    }

    public void setCustomerArea(BigInteger customerId, BigInteger areaId)
    {
        Customer customer = getCustomer(customerId);
        Area area = getArea(areaId);
        disconnectImpossibleServices(customer, area);
        customer.setArea(area);
        model.setCustomer(customer);
    }

    @Override
    public void setEmployee(final Employee employee)
    {
        model.setEmployee(employee);
    }

    private void disconnectImpossibleServices(Customer customer, Area area)
    {
        Template template;
        for (Service service : customer.getConnectedServices())
        {
            template = service.getTemplate();
            if (!template.getPossibleAreas().contains(area))
            {
                disconnectService(service.getId());
            }
        }
    }


    @Override
    public void suspendService(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        Order order = createOrder(service.getTemplate(), service, OrderStatus.Entering, OrderAction.Suspend);
        completeOrder(order, service);
    }

    @Override
    public void process(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        service.setStatus(ServiceStatus.Provisioning);
    }

    @Override
    public void backToPlanned(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        service.setStatus(ServiceStatus.Planned);
    }

    @Override
    public void activate(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        service.setStatus(ServiceStatus.Active);
    }

    @Override
    public void resumeService(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        Order order = createOrder(service.getTemplate(), service, OrderStatus.Entering, OrderAction.Resume);
        completeOrder(order, service);
    }

    @Override
    public void connectService(final BigInteger customerId, final BigInteger templateId)
    {
        Customer customer = getCustomer(customerId);
        for (Service service : getPlannedActiveSuspendedProvisioningService(customerId))
        {
            if (service.getTemplate().equals(templateId))
            {
                return;
            }
        }
        Template template = getTemplate(templateId);
        if (customer.getMoneyBalance().doubleValue() >= template.getCost().doubleValue())
        {
            customer.setMoneyBalance(BigDecimal.valueOf(
                    customer.getMoneyBalance().doubleValue() - template.getCost().doubleValue())
            );

            Service service = createService(customer, template, ServiceStatus.Planned);
            Order order = createOrder(template, service, OrderStatus.Entering, OrderAction.New);
            customer.addConnectedService(service);
            model.setCustomer(customer);
        }
        // completeOrder(order,service);
    }

    @Override
    public void disconnectService(final BigInteger serviceId)
    {
        Service service = getService(serviceId);
        Order order =
                createOrder(service.getTemplate(), service, OrderStatus.Entering, OrderAction.Disconnect);
        service.setStatus(ServiceStatus.Provisioning);
        model.setService(service);
        // completeOrder(order,service);
    }


    @Override
    public List<Service> getCustomerServices(final Customer customer)
    {
        return model.getServicesByCustomer(customer);
    }

    @Override
    public List<Area> getAvailableAreas(final BigInteger customerId)
    {
        List<Service> services = getPlannedAndActiveServices(customerId);
        List<Area> availableAreas = new ArrayList<>();

        if (services.size() > 0)
        {
            availableAreas.add(getCustomerArea(customerId));
            for (Service service : services)
            {
                Template template = service.getTemplate();
                for (Area area : template.getPossibleAreas())
                {
                    if (!availableAreas.contains(area))
                    {
                        availableAreas.add(area);
                    }
                }
            }
        }
        else
        {
            availableAreas.addAll(getAreas());
        }

        return availableAreas;
    }

    @Override
    public void resumeOrder(BigInteger orderId)
    {
        Order order = getOrder(orderId);
        order.setStatus(OrderStatus.Processing);
        Service service = order.getService();
        service.setStatus(ServiceStatus.Provisioning);
        model.setService(service);
        model.setOrder(order);
    }

    @Override
    public List<Service> getPlannedActiveSuspendedProvisioningService(BigInteger customerId)
    {
        List<Service> services = getPlannedAndActiveServices(customerId);
        services.addAll(getSuspendedServices(customerId));
        services.addAll(getProvisioningServices(customerId));
        return services;
    }

    private List<Service> getProvisioningServices(BigInteger customerId)
    {
        return getServicesByStatusAndCustomerId(customerId, ServiceStatus.Provisioning);
    }

    @Override
    public String getServiceName(final BigInteger serviceId)
    {
        return getService(serviceId).getTemplate().getName();
    }

    @Override
    public String getServiceDescription(final BigInteger serviceId)
    {
        return getService(serviceId).getTemplate().getDescription();
    }


    public void takeMoney(Timer timer)
    {
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                for (Customer customer : getCustomers())
                {
                    List<Service> services = getActiveServices(customer.getId());
                    System.out.println("Take money from customer");
                    serviceSorter.sort(services, ServiceSortType.UpByCost);
                    for (Service service : services)
                    {
                        if (customer.getMoneyBalance().compareTo(service.templateGetCost()) > -1)
                        {
                            customer.setMoneyBalance(
                                    BigDecimal.valueOf(
                                            customer.getMoneyBalance().doubleValue() -
                                                    service.templateGetCost().doubleValue()));
                        }
                        else
                        {
                            service.setStatus(ServiceStatus.Suspended);
                            setService(service);
                        }
                    }
                    setCustomer(customer);
                }
            }
        }, new Date(), 10_000_000);
    }
}
