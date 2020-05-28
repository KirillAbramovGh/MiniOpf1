package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class RegisterException extends IOException
{
    public RegisterException()
    {
    }

    public RegisterException(final String message)
    {
        super(message);
    }
}
