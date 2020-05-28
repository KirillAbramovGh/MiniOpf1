package jsp.com.netcracker.students.o3.model.users;

import jsp.com.netcracker.students.o3.model.Entity;

/**
 * user
 */

public interface User extends Entity
{
    /**
     * @return user name
     */
    String getName();

    /**
     * set user name
     */
    void setName(final String name);

    /**
     * @return user login
     */
    String getLogin();

    /**
     * set user login
     */
    void setLogin(final String login);

    /**
     * @return user password
     */
    String getPassword();

    /**
     * set user password
     */
    void setPassword(final String password);
}
