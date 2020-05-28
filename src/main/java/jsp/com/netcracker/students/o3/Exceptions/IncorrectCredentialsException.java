package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class IncorrectCredentialsException extends IOException
{
    public IncorrectCredentialsException()
    {
        super();
    }

    public IncorrectCredentialsException(final String s)
    {
        super(s);
    }
}
