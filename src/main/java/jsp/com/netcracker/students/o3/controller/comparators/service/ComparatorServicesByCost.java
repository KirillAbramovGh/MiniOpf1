package jsp.com.netcracker.students.o3.controller.comparators.service;

import jsp.com.netcracker.students.o3.model.services.Service;

import java.util.Comparator;

public class ComparatorServicesByCost implements Comparator<Service>
{
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorServicesByCost(boolean isUp){
        this.isUp = isUp;
    }

    @Override
    public int compare(final Service o1, final Service o2)
    {
        int res = o1.templateGetCost().compareTo(o2.templateGetCost());

        if(isUp) return res;
        else return (-1)*res;
    }
}
