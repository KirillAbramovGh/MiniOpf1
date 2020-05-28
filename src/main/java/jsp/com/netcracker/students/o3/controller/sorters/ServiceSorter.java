package jsp.com.netcracker.students.o3.controller.sorters;

import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByActivationDate;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByAreas;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByCost;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByCustomerId;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesById;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByName;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByStatus;
import jsp.com.netcracker.students.o3.controller.comparators.service.ComparatorServicesByTemplateId;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType;
import jsp.com.netcracker.students.o3.model.services.Service;

import java.util.Comparator;
import java.util.List;

/**
 * class sort services
 */
public class ServiceSorter {
    private static ServiceSorter instance;

    private ServiceSorter(){}

    private Comparator<Service> defineSortType(ServiceSortType type) {
        switch (type){
            case UpById:
                return new ComparatorServicesById(true);
            case DownById:
                return new ComparatorServicesById(false);
            case UpByCost:
                return new ComparatorServicesByCost(true);
            case UpByName:
                return new ComparatorServicesByName(true);
            case UpByAreas:
                return new ComparatorServicesByAreas(true);
            case DownByCost:
                return new ComparatorServicesByCost(false);
            case DownByName:
                return  new ComparatorServicesByName(false);
            case UpByStatus:
                return new ComparatorServicesByStatus(true);
            case DownByAreas:
                return new ComparatorServicesByAreas(false);
            case DownByStatus:
                return new ComparatorServicesByStatus(false);
            case UpByCustomerId:
                return new ComparatorServicesByCustomerId(true);
            case UpByTemplateId:
                return new ComparatorServicesByTemplateId(true);
            case DownByCustomerId:
                return new ComparatorServicesByCustomerId(false);
            case DownByTemplateId:
                return new ComparatorServicesByTemplateId(false);
            case UpByActivationDate:
                return new ComparatorServicesByActivationDate(true);
            case DownByActivationDate:
                return new ComparatorServicesByActivationDate(false);
        }
        return new ComparatorServicesByName(false);
    }

    /**
     * sort services
     */
    public void sort(List<Service> services, ServiceSortType type) {
        Comparator<Service> serviceComparator = defineSortType(type);
        System.out.println(serviceComparator);
        services.sort(serviceComparator);
    }

    public static ServiceSorter getInstance() {
        if (instance == null) {
            instance = new ServiceSorter();
        }
        return instance;
    }
}
