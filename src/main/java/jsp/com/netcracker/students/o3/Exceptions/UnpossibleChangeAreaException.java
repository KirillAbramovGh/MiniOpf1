package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class UnpossibleChangeAreaException extends IOException
{
    public UnpossibleChangeAreaException()
    {
    }

    public UnpossibleChangeAreaException(final String message)
    {
        super(message);
    }
}
