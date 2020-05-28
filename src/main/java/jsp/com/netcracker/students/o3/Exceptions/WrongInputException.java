package jsp.com.netcracker.students.o3.Exceptions;

import java.io.IOException;

public class WrongInputException extends IOException
{
    public WrongInputException(final String s)
    {
        super(s);
    }

    public WrongInputException()
    {
    }
}
