package jsp.com.netcracker.students.o3.controller.searcher;

import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.templates.Template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class search templates and services
 */
public class TemplatesSearcher extends EntitySearcher<Template>
{
    private static TemplatesSearcher instance;
    private SearcherUtil searcherUtil;

    private TemplatesSearcher()
    {
        searcherUtil = SearcherUtil.getInstance();
    }

    /**
     * search templates by area name
     */
    public List<Template> searchTemplatesByArea(Collection<Template> templates, String areaName)
    {
        List<Template> results = new ArrayList<>();

        for (Template template : templates)
        {
            for (Area area : template.getPossibleAreas())
            {
                if (area.getName().contains(areaName) || searcherUtil.checkRegExp(areaName, area.getName()))
                {
                    results.add(template);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * search templates by cost
     */
    public List<Template> searchTemplatesByCost(Collection<Template> templates, String search)
    {
        List<Template> result = new ArrayList<>();

        BigDecimal cost;
        for (Template template : templates)
        {
            cost = template.getCost();
            if (searcherUtil.isCostInDiapason(cost, search, 50) ||
                    searcherUtil.checkRegExp(search, cost.toString()))
            {
                result.add(template);
            }
        }

        return result;
    }


    /**
     * search templates by name
     */
    public List<Template> searchTemplatesByName(Collection<Template> templates, String search)
    {
        List<Template> result = new ArrayList<>();

        String name;
        for (Template template : templates)
        {
            name = template.getName();
            if (name.contains(search) || searcherUtil.checkRegExp(search, name))
            {
                result.add(template);
            }
        }

        return result;
    }


    /**
     * search templates by description
     */
    public List<Template> searchTemplatesByDescription(Collection<Template> templates, String search)
    {
        List<Template> result = new ArrayList<>();

        String description;
        for (Template template : templates)
        {
            description = template.getDescription().toLowerCase();
            if (description.contains(search) || searcherUtil.checkRegExp(search, description))
            {
                result.add(template);
            }
        }

        return result;
    }


    public static TemplatesSearcher getInstance()
    {
        if (instance == null)
        {
            instance = new TemplatesSearcher();
        }

        return instance;
    }

    public List<Template> search(String search, String field, Collection<Template> templates)
    {
        switch (field)
        {
            case "Id":
                return searchTemplatesById(search, templates);
            case "Name":
                return searchTemplatesByName(templates, search);
            case "Description":
                return searchTemplatesByDescription(templates, search);
            case "Cost":
                return searchTemplatesByCost(templates, search);
            case "Area":
                return searchTemplatesByArea(templates, search);
            case "all":
                Set<Template> res = new HashSet<>(searchTemplatesById(search, templates));

                res.addAll(searchTemplatesByName(templates, search));
                res.addAll(searchTemplatesByDescription(templates, search));
                res.addAll(searchTemplatesByCost(templates, search));
                res.addAll(searchTemplatesByArea(templates, search));

                return new ArrayList<>(res);
        }
        return new ArrayList<>();
    }

    private List<Template> searchTemplatesById(String search, Collection<Template> templates)
    {
        List<Template> result = new ArrayList<>();

        String id;
        for (Template template : templates)
        {
            id = template.getId().toString();
            if (id.equals(search) || searcherUtil.checkRegExp(search, id))
            {
                result.add(template);
            }
        }

        return result;
    }

    public List<Template> searchTemplatesByAllFields(Collection<Template> templates, String req)
    {
        Set<Template> result = new HashSet<>();

        templates.addAll(searchTemplatesByArea(templates, req));
        templates.addAll(searchTemplatesByName(templates, req));
        templates.addAll(searchTemplatesByDescription(templates, req));
        templates.addAll(searchTemplatesByCost(templates, req));

        return new ArrayList<>(result);
    }
}
