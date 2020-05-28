package jsp.com.netcracker.students.o3.model.dao;

import jsp.com.netcracker.students.o3.model.Entity;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T extends Entity>
{
    List<T> getAll()throws SQLException;

    T getEntity(BigInteger id)throws SQLException;

    void update(T entity)throws SQLException;

    void delete(BigInteger id)throws SQLException;

    void delete(T entity)throws SQLException;

    void create(T entity)throws SQLException;
}
