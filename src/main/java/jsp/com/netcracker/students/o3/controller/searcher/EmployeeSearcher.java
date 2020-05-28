package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.model.users.Employee;

import java.util.*;

public class EmployeeSearcher extends EntitySearcher<Employee>
{
    private static EmployeeSearcher instance;
    private SearcherUtil searcherUtil;

    private EmployeeSearcher(){
        searcherUtil = SearcherUtil.getInstance();
    }

    public static EmployeeSearcher getInstance(){
        if(instance == null){
            instance = new EmployeeSearcher();
        }

        return instance;
    }

    public List<Employee> search(String searchValue, String searchField, Collection<Employee> employees) {
        switch (searchField){
            case "Id": return searchById(searchValue,employees);
            case "Name": return searchByName(searchValue,employees);
            case "Login": return searchByLogin(searchValue,employees);
            case "all":
                Set<Employee> res = new HashSet<>(searchById(searchValue,employees));

                res.addAll(searchByName(searchValue,employees));
                res.addAll(searchByLogin(searchValue,employees));

                return new ArrayList<>(res);
        }

        return new ArrayList<>();
    }

    private List<Employee> searchByLogin(String search, Collection<Employee> employees) {
        List<Employee> result = new ArrayList<>();

        for(Employee employee : employees){
            if(employee.getLogin().contains(search) ||
                    searcherUtil.checkRegExp(search,employee.getLogin())){
                result.add(employee);
            }
        }

        return result;
    }

    private List<Employee> searchByName(String search, Collection<Employee> employees) {
        List<Employee> result = new ArrayList<>();

        for(Employee employee : employees){
            if(employee.getName().contains(search) ||
                    searcherUtil.checkRegExp(search,employee.getName())){
                result.add(employee);
            }
        }

        return result;
    }

    private List<Employee> searchById(String search, Collection<Employee> employees) {
        List<Employee> result = new ArrayList<>();

        for(Employee employee : employees){
            if(employee.getId().toString().equals(search)){
                result.add(employee);
            }
        }

        return result;
    }
}
