package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.model.area.Area;

import java.util.*;

public class AreaSearcher extends EntitySearcher<Area>
{
    private static AreaSearcher instance;
    private SearcherUtil searcherUtil;

    private AreaSearcher() {
        searcherUtil = SearcherUtil.getInstance();
    }

    public static AreaSearcher getInstance() {
        if (instance == null) {
            instance = new AreaSearcher();
        }

        return instance;
    }

    private List<Area> searchById(String search, Collection<Area> areas) {
        List<Area> result = new ArrayList<>();

        String id;
        for (Area area : areas) {
            id = area.getId().toString();
            if (id.equals(search)) {
                result.add(area);
            }
        }

        return result;
    }

    private List<Area> searchByName(String search, Collection<Area> areas) {
        List<Area> result = new ArrayList<>();

        String name;
        for (Area area : areas) {
            name = area.getName();
            if (name.contains(search) || searcherUtil.checkRegExp(search, name)) {
                result.add(area);
            }
        }

        return result;
    }

    private List<Area> searchByDescription(String search, Collection<Area> areas) {
        List<Area> result = new ArrayList<>();

        String description;
        for (Area area : areas) {
            description = area.getDescription();
            if (description.contains(search) || searcherUtil.checkRegExp(search, description)) {
                result.add(area);
            }
        }

        return result;
    }


    public List<Area> search(String search, String field, Collection<Area> areas) {
        switch (field) {
            case "Id":
                return searchById(search, areas);
            case "Name":
                return searchByName(search, areas);
            case "Description":
                return searchByDescription(search, areas);
            case "all":
                Set<Area> res = new HashSet<>(searchById(search, areas));

                res.addAll(searchByName(search, areas));
                res.addAll(searchByDescription(search, areas));

                return new ArrayList<>(res);
        }

        return new ArrayList<>();
    }
}
