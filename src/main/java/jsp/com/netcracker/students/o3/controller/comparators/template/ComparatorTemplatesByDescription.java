package jsp.com.netcracker.students.o3.controller.comparators.template;

import jsp.com.netcracker.students.o3.model.templates.Template;

import java.util.Comparator;

public class ComparatorTemplatesByDescription implements Comparator<Template>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorTemplatesByDescription(boolean isUp)
    {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Template o1, final Template o2)
    {
        int res = o1.getDescription().compareTo(o2.getDescription());

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
