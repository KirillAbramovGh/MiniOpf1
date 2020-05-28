package jsp.com.netcracker.students.o3.model.serialization.log;

public class XMLLogController
{
    private static XMLLogController instance;

    private XMLRequestsWrapper wrapper;
    private XMLSerializer serializer;
    private int numOfLogs;
    private static final String rootName = "log.xml";

    private XMLLogController()
    {
        numOfLogs = 1;
        wrapper = new XMLRequestsWrapper();
        serializer = new XMLSerializer();
    }

    public void addRequest(String sqlReq)
    {
        try
        {
            wrapper.addRequests(new XMLRequest(sqlReq));
            checkWrapperSize();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkWrapperSize()
    {
        final int fileSize = 50;
        if (wrapper.getNumOfRequests() > fileSize)
        {
            serializer.serializeObjectToXML(wrapper,
                    "C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\logs\\" + numOfLogs + rootName);
            wrapper = new XMLRequestsWrapper();
            numOfLogs++;
        }
    }

    public static XMLLogController getInstance()
    {
        if (instance == null)
        {
            instance = new XMLLogController();
        }

        return instance;
    }
}
