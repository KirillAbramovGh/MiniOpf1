package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class NullInputException extends IOException
{
    public NullInputException()
    {
    }

    public NullInputException(final String message)
    {
        super(message);
    }
}
