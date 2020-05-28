package jsp.com.netcracker.students.o3.controller.comparators.template;

import jsp.com.netcracker.students.o3.model.templates.Template;

import java.util.Comparator;

public class ComparatorTemplatesByAreas implements Comparator<Template>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorTemplatesByAreas(boolean isUp)
    {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Template o1, final Template o2)
    {
        int res = o1.getPossibleAreas().toString().compareTo(o2.getPossibleAreas().toString());

        if (isUp)
        {
            return res;
        }
        else
        {
            return (-1) * res;
        }
    }
}
