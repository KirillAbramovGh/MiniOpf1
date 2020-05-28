package jsp.com.netcracker.students.o3.controller.comparators.template;

import jsp.com.netcracker.students.o3.model.templates.Template;

import java.util.Comparator;

public class ComparatorTemplatesById implements Comparator<Template>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorTemplatesById(boolean isUp)
    {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Template o1, final Template o2)
    {
        int res = o1.getId().compareTo(o2.getId());

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
