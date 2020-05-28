package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class IncorrectPasswordException extends IOException
{
    public IncorrectPasswordException()
    {
    }

    public IncorrectPasswordException(final String message)
    {
        super(message);
    }
}
