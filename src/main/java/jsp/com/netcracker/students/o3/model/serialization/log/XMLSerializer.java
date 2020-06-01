package jsp.com.netcracker.students.o3.model.serialization.log;

import org.xml.sax.SAXException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XMLSerializer
{
    private static final Class[] classes = new Class[]{
            XMLRequestsWrapper.class, XMLRequest.class,
    };

    public void serializeObjectToXML(XMLRequestsWrapper o, String fileName)
    {
        try
        {
            Writer writer = new FileWriter(fileName);


            JAXBContext context = JAXBContext.newInstance(classes);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(o, writer);

            schemeValidation(fileName,"C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\logs\\shema.xsd");
        }
        catch (Exception ignore)
        {}
    }

    public boolean schemeValidation(String pathXml, String pathXsd) throws IOException
    {
        try
        {
            SchemaFactory factory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));
            return true;
        }
        catch (SAXException e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
