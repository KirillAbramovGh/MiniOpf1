package jsp.com.netcracker.students.o3.controller.comparators.service;

import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.services.Service;

import java.util.Comparator;

public class ComparatorServicesByName implements Comparator<Service>
{
    /**
     * define sort up or down
     */
    private boolean isUp;
    private Controller controller = ControllerImpl.getInstance();

    public ComparatorServicesByName(boolean isUp){
        this.isUp = isUp;
    }

    @Override
    public int compare(final Service o1, final Service o2)
    {
        int res = controller.getServiceName(o1.getId()).compareTo(controller.getServiceName(o2.getId()));

        if(isUp) return res;
        else return (-1)*res;
    }
}
