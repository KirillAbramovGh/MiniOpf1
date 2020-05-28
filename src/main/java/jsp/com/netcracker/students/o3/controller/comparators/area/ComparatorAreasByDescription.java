package jsp.com.netcracker.students.o3.controller.comparators.area;

import jsp.com.netcracker.students.o3.model.area.Area;

import java.util.Comparator;

public class ComparatorAreasByDescription implements Comparator<Area> {
    /**
     * define sort up or down
     */
    private boolean isUp;

    public ComparatorAreasByDescription(boolean isUp) {
        this.isUp = isUp;
    }

    @Override
    public int compare(final Area o1, final Area o2) {
        int res = o1.getDescription().compareTo(o2.getDescription());

        if (isUp) return res;
        else return (-1) * res;
    }
}
