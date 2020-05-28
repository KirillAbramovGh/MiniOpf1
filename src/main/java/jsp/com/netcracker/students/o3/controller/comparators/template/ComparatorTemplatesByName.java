package jsp.com.netcracker.students.o3.controller.comparators.template;

import jsp.com.netcracker.students.o3.model.templates.Template;

import java.util.Comparator;

public class ComparatorTemplatesByName implements Comparator<Template>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorTemplatesByName(boolean isUp){
        this.isUp = isUp;
    }

    @Override
    public int compare(final Template o1, final Template o2)
    {
        int res = o1.getName().compareTo(o2.getName());

        if(isUp) return res;
        else return (-1)*res;
    }
}
