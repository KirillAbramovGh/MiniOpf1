package jsp.sessionBeans;

import jsp.com.netcracker.students.o3.Exceptions.UnpossibleChangeAreaException;
import jsp.com.netcracker.students.o3.Exceptions.WrongInputException;
import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.controller.searcher.ServiceSearcher;
import jsp.com.netcracker.students.o3.controller.searcher.TemplatesSearcher;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.TemplateSortType;
import jsp.com.netcracker.students.o3.controller.sorters.TemplateSorter;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.Customer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

@Stateless
public class CustomerSessionBean
{
    public List<Service> searchServices(String searchValue, BigInteger customerId)
    {
        Controller controller = ControllerImpl.getInstance();
        ServiceSearcher searcherService = ServiceSearcher.getInstance();
        List<Service> result;
        if (searchValue != null)
        {
            result = searcherService.searchServicesByAllFields(
                    controller.getPlannedActiveSuspendedProvisioningService(customerId),
                    searchValue
            );
        }
        else
        {
            result = new ArrayList<>();
        }
        return result;
    }

    public List<Template> searchTemplates(String searchValue, BigInteger customerId)
    {
        Controller controller = ControllerImpl.getInstance();
        TemplatesSearcher searcherTemplates = TemplatesSearcher.getInstance();
        List<Template> result;
        if (searchValue != null)
        {
            result = searcherTemplates.searchTemplatesByAllFields(
                    controller.getCustomerAvailableTemplates(customerId),
                    searchValue
            );
        }
        else
        {
            result = new ArrayList<>();
        }
        return result;
    }

    public BigDecimal getBalance(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getBalance(customerId);
    }

    public String getFIO(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getCustomerFio(customerId);
    }

    public String getAreaName(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getAreaName(customerId);
    }

    public String getPassword(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getCustomer(customerId).getPassword();
    }

    public Map<BigInteger, Template> getConnectedTemplates(BigInteger customerId)
    {
        List<Service> connectedServices =
                ControllerImpl.getInstance().getPlannedActiveSuspendedProvisioningService(customerId);
        Map<BigInteger, Template> templates = new HashMap<>();

        for (Service service : connectedServices)
        {
            templates.put(service.getId(), service.getTemplate());
        }

        return templates;
    }

    public List<Template> getUnconnectedTemplates(BigInteger customerId)
    {
        List<Template> templates = ControllerImpl.getInstance().getCustomerAvailableTemplates(customerId);
        for (Service service : ControllerImpl.getInstance().getPlannedActiveSuspendedProvisioningService(customerId))
        {
            templates.remove(service.getTemplate());
        }

        return templates;
    }

    public void changeName(String newName, BigInteger customerId) throws WrongInputException
    {
        if (newName != null && !newName.isEmpty())
        {
            ControllerImpl.getInstance().setCustomerName(customerId, newName);
        }
    }

    public void changePassword(String newPassword, BigInteger customerId) throws WrongInputException
    {

        if (newPassword != null && !newPassword.isEmpty())
        {
            ControllerImpl.getInstance().setUserPassword(customerId, newPassword);
        }
    }

    public void changeArea(Area area, BigInteger customerId) throws UnpossibleChangeAreaException
    {
        if (area != null)
        {
            ControllerImpl.getInstance().setCustomerArea(customerId, area.getId());
        }
    }


    public void addBalance(String value, BigInteger customerId)
    {
        if (value != null && !value.isEmpty())
        {
            ControllerImpl.getInstance().putOnBalance(customerId, parseBigDec(value));
        }
    }


    public BigDecimal parseBigDec(String value)
    {
        double d = Double.parseDouble(value);
        return BigDecimal.valueOf(d);
    }


    public void disconnectService(BigInteger serviceId)

    {
        ControllerImpl.getInstance().disconnectService(serviceId);
    }

    public void connectService(BigInteger templateId, BigInteger customerId)
    {
        ControllerImpl.getInstance().connectService(customerId, templateId);
    }

    public String getLogin(BigInteger customerId)

    {
        return ControllerImpl.getInstance().getCustomer(customerId).getLogin();
    }

    public List<Area> getAreas()
    {
        return ControllerImpl.getInstance().getAreas();
    }


    public List<Service> getConnectedServices(BigInteger customerId)
    {
        return ControllerImpl.getInstance().getPlannedActiveSuspendedProvisioningService(customerId);
    }


    public List<Template> showAllTemplates(TemplateSortType sortType, BigInteger customerId)
    {
        List<Template> templates = getUnconnectedTemplates(customerId);
        TemplateSorter.getInstance().sort(templates, sortType);

        return templates;
    }

    public void suspendService(final BigInteger serviceId)
    {
        ControllerImpl.getInstance().suspendService(serviceId);
    }

    public void resumeService(final BigInteger serviceId)
    {
        ControllerImpl.getInstance().resumeService(serviceId);
    }

    public BigInteger getAreaId(final BigInteger customerId){
        return ControllerImpl.getInstance().getCustomerArea(customerId).getId();
    }

    public void setCustomersFields(BigInteger customerId, String name, String password, String login, BigInteger areaId,
            BigDecimal balance){
        Controller controller = ControllerImpl.getInstance();
        Customer customer = controller.getCustomer(customerId);

        customer.setName(name);
        customer.setLogin(login);
        customer.setPassword(password);
        customer.setArea(controller.getArea(areaId));
        customer.setMoneyBalance(balance);

        controller.setCustomer(customer);
    }

    public Set<BigInteger> getConnectedServicesIds(BigInteger customerId)
    {
        Set<BigInteger> result = new HashSet<>();
        for(Service service :  ControllerImpl.getInstance().getCustomer(customerId).getConnectedServices()){
            result.add(service.getId());
        }
        return result;
    }
}
