package jsp.com.netcracker.students.o3.controller.comparators.service;

import jsp.com.netcracker.students.o3.model.services.Service;

import java.util.Comparator;

public class ComparatorServicesByAreas implements Comparator<Service>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorServicesByAreas(boolean isUp){
        this.isUp = isUp;
    }

    @Override
    public int compare(final Service o1, final Service o2)
    {
        int res = o1.getTemplate().getPossibleAreas().toString().compareTo(
                o2.getTemplate().getPossibleAreas().toString());

        if(isUp) return res;
        else return (-1)*res;
    }
}
