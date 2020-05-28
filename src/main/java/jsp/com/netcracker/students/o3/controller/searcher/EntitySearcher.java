package jsp.com.netcracker.students.o3.controller.searcher;

import java.util.Collection;
import java.util.List;

public abstract class EntitySearcher<T>
{

    public abstract List<T> search(String search, String field, Collection<T> entities);
}
