package jsp.com.netcracker.students.o3.model.serialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * GRUD class for entities
 */
public class JsonEntitiesStorage
{
    private static JsonEntitiesStorage instance;

    @JsonDeserialize(as = HashSet.class, contentAs = OrderImpl.class)
    private Set<Order> orders;
    @JsonDeserialize(as = HashSet.class, contentAs = ServiceImpl.class)
    private Set<Service> services;
    @JsonDeserialize(as = HashSet.class, contentAs = TemplateImpl.class)
    private Set<Template> templates;
    @JsonDeserialize(as = HashSet.class, contentAs = AreaImpl.class)
    private Set<Area> areas;
    @JsonDeserialize(as = HashSet.class, contentAs = CustomerImpl.class)
    private Set<Customer> customers;
    @JsonDeserialize(as = HashSet.class, contentAs = EmployeeImpl.class)
    private Set<Employee> employees;


    /**
     * method initialize model
     */
    private JsonEntitiesStorage()
    {
        orders = new HashSet<>();
        templates = new HashSet<>();
        services = new HashSet<>();
        customers = new HashSet<>();
        employees = new HashSet<>();
        areas = new HashSet<>();
    }

    /**
     * Methods which set Map of entities
     */
    public void setOrders(final Set<Order> orders)
    {
        this.orders = new HashSet<>(orders);
    }

    public void setTemplates(final Set<Template> templates)
    {
        this.templates = new HashSet<>(templates);
        Set<Area> areaSet = new HashSet<>();
        for (Template template : templates)
        {
            areaSet.addAll(template.getPossibleAreas());
        }
        this.areas.addAll(areaSet);
    }

    public void setServices(final Set<Service> services)
    {
        this.services = new HashSet<>(services);
    }

    public void setCustomers(final Set<Customer> customers)
    {
        this.customers = new HashSet<>(customers);
        for (Customer customer : customers)
        {
            Set<Service> serviceSet = customer.getConnectedServices();
            for (Service service : serviceSet)
            {
                Template template = service.getTemplate();
                this.templates.add(template);
            }
            this.services.addAll(serviceSet);
            areas.add(customer.getArea());
        }
    }

    public void setEmployees(final Set<Employee> employees)
    {
        this.employees = new HashSet<>(employees);
    }

    public void setAreas(final Set<Area> areas)
    {
        this.areas = areas;
    }


    /**
     * return set and return instance
     *
     * @return instance
     */
    public static JsonEntitiesStorage getInstance()
    {
        if (instance == null)
        {
            instance = new JsonEntitiesStorage();
        }
        return instance;
    }




    /**
     * methods return map of entities
     *
     * @return map of entities
     */
    public Set<Order> getOrders()
    {
        return orders;
    }

    public Set<Template> getTemplates()
    {
        return templates;
    }

    public Set<Service> getServices()
    {
        return services;
    }

    public Set<Customer> getCustomers()
    {
        return customers;
    }

    public Set<Employee> getEmployees()
    {
        return employees;
    }

    public Set<Area> getAreas()
    {
        return areas;
    }



    public void exportToFile()
    {
        synchronized (this)
        {
            Serializer serializer = new SerializerImpl();
            try
            {
                serializer.serializeEntityStorage(this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void importFromFile()
    {
        Serializer serializer = new SerializerImpl();
        try
        {
            serializer.deserializeEntityStorage(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
