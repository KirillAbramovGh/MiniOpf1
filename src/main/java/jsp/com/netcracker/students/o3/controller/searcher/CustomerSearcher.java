package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.users.Customer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class CustomerSearcher extends EntitySearcher<Customer>
{
    private static CustomerSearcher instance;
    private SearcherUtil searcherUtil;

    private CustomerSearcher() {
        searcherUtil = SearcherUtil.getInstance();
    }

    public static CustomerSearcher getInstance() {
        if (instance == null) {
            instance = new CustomerSearcher();
        }

        return instance;
    }

    public List<Customer> search(String search, String field, Collection<Customer> customers) {
        switch (field) {
            case "Id":
                return searchById(search, customers);
            case "Name":
                return searchByName(search, customers);
            case "Login":
                return searchByLogin(search, customers);
            case "Balance":
                return searchByBalance(search, customers);
            case "Area":
                return searchByArea(search, customers);
            case "ConnectedServices":
                return searchByConnectedServices(search, customers);
            case "all":
                Set<Customer> res = new HashSet<>(searchById(search, customers));

                res.addAll(searchByName(search, customers));
                res.addAll(searchByLogin(search, customers));
                res.addAll(searchByBalance(search, customers));
                res.addAll(searchByArea(search, customers));
                res.addAll(searchByConnectedServices(search, customers));

                return new ArrayList<>(res);
        }

        return new ArrayList<>();
    }

    private List<Customer> searchByConnectedServices(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        for (Customer customer : customers) {
            for (Service service : customer.getConnectedServices()) {
                if (checkService(search, service.getId())) {
                    result.add(customer);
                }
            }
        }

        return result;
    }

    private boolean checkService(String search, BigInteger serviceId) {
        return serviceId.toString().equals(search) ||
                searcherUtil.checkRegExp(search, serviceId.toString()) ||
                ControllerImpl.getInstance().getServiceName(serviceId).contains(search);
    }

    private List<Customer> searchByArea(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        for (Customer customer : customers) {
            if (searcherUtil.checkArea(search, customer.getArea())) {
                result.add(customer);
            }
        }

        return result;
    }


    private List<Customer> searchByBalance(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        BigDecimal balance;
        for (Customer customer : customers) {
            balance = customer.getMoneyBalance();
            if (searcherUtil.isCostInDiapason(balance, search, 50) || searcherUtil.checkRegExp(search, balance.toString())) {
                result.add(customer);
            }
        }

        return result;
    }

    private List<Customer> searchByLogin(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        String login;
        for (Customer customer : customers) {
            login = customer.getLogin();
            if (customer.getLogin().contains(search) || searcherUtil.checkRegExp(search, login)) {
                result.add(customer);
            }
        }

        return result;
    }

    private List<Customer> searchByName(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        String name;
        for (Customer customer : customers) {
            name = customer.getName();
            if (name.contains(search) || searcherUtil.checkRegExp(search, name)) {
                result.add(customer);
            }
        }

        return result;
    }

    private List<Customer> searchById(String search, Collection<Customer> customers) {
        List<Customer> result = new ArrayList<>();

        String id;
        for (Customer customer : customers) {
            id = customer.getId().toString();
            if (id.equals(search)) {
                result.add(customer);
            }
        }

        return result;
    }

}
