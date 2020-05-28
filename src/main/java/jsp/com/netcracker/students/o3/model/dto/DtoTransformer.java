package jsp.com.netcracker.students.o3.model.dto;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.Employee;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DtoTransformer
{
    public Dto transform(Order order)
    {
        Dto entityDto = new Dto(order.getId(), "order");

        BigInteger employeeId = BigInteger.ZERO;

        if (order.getEmployee() != null)
        {
            employeeId = order.getEmployee().getId();
        }

        entityDto.putParameter("id", order.getId());
        entityDto.putParameter("action", order.getAction());
        entityDto.putParameter("status", order.getStatus());
        entityDto.putParameter("date", order.getCreationDate());
        entityDto.putParameter("employeeId", employeeId);
        entityDto.putParameter("serviceId", order.getService().getId());
        entityDto.putParameter("templateId", order.getTemplate().getId());

        return entityDto;
    }

    public Dto transform(Service service)
    {
        Dto entityDto = new Dto(service.getId(), "service");

        entityDto.putParameter("id", service.getId());
        entityDto.putParameter("status", service.getStatus());
        entityDto.putParameter("customerId", service.getCustomer().getId());
        entityDto.putParameter("templateId", service.getTemplate().getId());
        entityDto.putParameter("date", service.getActivationDate());

        return entityDto;
    }

    public Dto transform(Template template)
    {
        Dto entityDto = new Dto(template.getId(), "template");

        entityDto.putParameter("id", template.getId());
        entityDto.putParameter("cost", template.getCost());
        entityDto.putParameter("description", template.getDescription());
        entityDto.putParameter("name", template.getName());

        List<BigInteger> areaIds = new ArrayList<>();
        List<Area> areas = template.getPossibleAreas();
        areas.forEach(area -> {
            areaIds.add(area.getId());
        });
        entityDto.putParameter("areaIds", areaIds);

        return entityDto;
    }

    public Dto transform(Area area)
    {
        Dto entityDto = new Dto(area.getId(), "area");

        entityDto.putParameter("id", area.getId());
        entityDto.putParameter("name", area.getName());
        entityDto.putParameter("description", area.getDescription());

        return entityDto;
    }

    public Dto transform(Customer customer)
    {
        Dto entityDto = new Dto(customer.getId(), "customer");

        entityDto.putParameter("id", customer.getId());
        entityDto.putParameter("name", customer.getName());
        entityDto.putParameter("areaId", customer.getArea().getId());
        entityDto.putParameter("balance", customer.getMoneyBalance());
        entityDto.putParameter("login", customer.getLogin());
        entityDto.putParameter("password", customer.getPassword());

        List<BigInteger> servicesIds = new ArrayList<>();
        customer.getConnectedServices().forEach(service -> {
            servicesIds.add(service.getId());
        });

        entityDto.putParameter("serviceIds", servicesIds);
        return entityDto;
    }

    public Dto transform(Employee employee)
    {
        Dto entityDto = new Dto(employee.getId(), "employee");

        entityDto.putParameter("id", employee.getId());
        entityDto.putParameter("name", employee.getName());
        entityDto.putParameter("login", employee.getLogin());
        entityDto.putParameter("password", employee.getPassword());

        return entityDto;
    }
}
