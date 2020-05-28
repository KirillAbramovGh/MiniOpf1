package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.services.Service;

import java.util.*;

/**
 * class search templates and services
 */
public class ServiceSearcher extends EntitySearcher<Service>
{
    private static ServiceSearcher instance;
    private SearcherUtil searcherUtil;

    private ServiceSearcher() {
        searcherUtil = SearcherUtil.getInstance();
    }

    /**
     * search services by cost
     */
    public List<Service> searchServiceByCost(Collection<Service> services, String cost) {
        List<Service> result = new ArrayList<>();

        for (Service service : services) {
            if (searcherUtil.isCostInDiapason(service.templateGetCost(), cost, 60)
                    || searcherUtil.checkRegExp(cost, service.templateGetCost().toString())
            ) {
                result.add(service);
            }
        }

        return result;
    }


    /**
     * search services by name
     */
    public List<Service> searchServiceByName(Collection<Service> services, String search) {
        List<Service> result = new ArrayList<>();

        String name;
        for (Service service : services) {
            name = ControllerImpl.getInstance().getServiceName(service.getId());
            if (name.contains(search) || searcherUtil.checkRegExp(search, name)) {
                result.add(service);
            }
        }

        return result;
    }

    /**
     * search services by status
     */
    public List<Service> searchServiceByStatus(Collection<Service> services, String search) {
        List<Service> result = new ArrayList<>();

        String status;
        for (Service service : services) {
            status = service.getStatus().toString().toLowerCase();
            if (status.contains(search) || searcherUtil.checkRegExp(search, status)) {
                result.add(service);
            }
        }

        return result;
    }


    /**
     * search services by all fields
     */
    public List<Service> searchServicesByAllFields(List<Service> services, String searchField) {
        Set<Service> result = new HashSet<>();


            result.addAll(searchServiceByName(services, searchField));
            result.addAll(searchServiceByStatus(services, searchField));
            result.addAll(searchServiceByCost(services, searchField));


        return new ArrayList<>(result);
    }

    public static ServiceSearcher getInstance() {
        if (instance == null) {
            instance = new ServiceSearcher();
        }

        return instance;
    }

    public List<Service> search(String search, String field, Collection<Service> services) {
        switch (field) {
            case "Id":
                return searchServiceById(search, services);
            case "Name":
                return searchServiceByName(services, search);
            case "Cost":
                return searchServiceByCost(services, search);
            case "Status":
                return searchServiceByStatus(services, search);
            case "TemplateId":
                return searchServiceByTemplateId(search, services);
            case "UserId":
                return searchServiceByUserId(search, services);
            case "Areas":
                return searchServiceByArea(search, services);
            case "ActivationDate":
                return searchServiceByActivationDate(search,services);
            case "all":
                Set<Service> result = new HashSet<>(searchServiceById(search, services));

                result.addAll(searchServiceByName(services, search));
                result.addAll(searchServiceByCost(services, search));
                result.addAll(searchServiceByStatus(services, search));
                result.addAll(searchServiceByTemplateId(search, services));
                result.addAll(searchServiceByUserId(search, services));
                result.addAll(searchServiceByArea(search, services));

                return new ArrayList<>(result);
        }

        return new ArrayList<>();
    }

    private List<Service> searchServiceByActivationDate(final String search, final Collection<Service> services)
    {
        List<Service> result = new ArrayList<>();

        for (Service service : services) {
            if (service.getActivationDate().toString().contains(search)) {
                result.add(service);
            }
        }

        return result;
    }

    private List<Service> searchServiceByArea(String search, Collection<Service> services) {
        List<Service> result = new ArrayList<>();

        for (Service service : services) {
            if (searcherUtil.checkArea(search, service.getCustomer().getArea())) {
                result.add(service);
            }
        }

        return result;
    }


    private List<Service> searchServiceByUserId(String search, Collection<Service> services) {
        List<Service> result = new ArrayList<>();

        String userId;
        for (Service service : services) {
            userId = service.getCustomer().toString();
            if (userId.contains(search) || searcherUtil.checkRegExp(search, userId)) {
                result.add(service);
            }
        }

        return result;
    }

    private List<Service> searchServiceByTemplateId(String search, Collection<Service> services) {
        List<Service> result = new ArrayList<>();

        String templateId;
        for (Service service : services) {
            templateId = service.getTemplate().toString();
            if (templateId.contains(search) || searcherUtil.checkRegExp(search, templateId)) {
                result.add(service);
            }
        }

        return result;
    }

    private List<Service> searchServiceById(String search, Collection<Service> services) {
        List<Service> result = new ArrayList<>();

        String id;
        for (Service service : services) {
            id = service.getId().toString();
            if (id.equals(search) || searcherUtil.checkRegExp(search, id)) {
                result.add(service);
            }
        }

        return result;
    }
}
