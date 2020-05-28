package jsp.com.netcracker.students.o3.model.hibernate;

import java.io.*;
public class SqlExecutor
{

    public static void execute() {
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                    ("psql -U username -d dbname -h serverhost -f scripts.sql");
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}