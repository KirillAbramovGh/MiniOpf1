package jsp.com.netcracker.students.o3.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsp.com.netcracker.students.o3.model.ModelJson;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SerializerImpl implements Serializer
{

    public void serializeEntityStorage(JsonEntitiesStorage jsonEntitiesStorage) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\entities.json");
        objectMapper.writeValue(file, jsonEntitiesStorage);
    }

    @Override
    public void serializeModel(final ModelJson modelJson) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\entities.json");
        objectMapper.writeValue(file, modelJson);
    }

    public void deserializeEntityStorage(JsonEntitiesStorage jsonEntitiesStorage) throws IOException
    {
        if(validate())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\entities.json");

            JsonEntitiesStorage newJsonEntitiesStorage = objectMapper.readValue(file, JsonEntitiesStorage.class);

            jsonEntitiesStorage.setCustomers(newJsonEntitiesStorage.getCustomers());
            jsonEntitiesStorage.setEmployees(newJsonEntitiesStorage.getEmployees());
            jsonEntitiesStorage.setOrders(newJsonEntitiesStorage.getOrders());
            jsonEntitiesStorage.setTemplates(newJsonEntitiesStorage.getTemplates());
            jsonEntitiesStorage.setAreas(newJsonEntitiesStorage.getAreas());
            jsonEntitiesStorage.setServices(newJsonEntitiesStorage.getServices());
            //jsonEntitiesStorage.setLastId(newJsonEntitiesStorage.getLastId());
        }
    }

    @Override
    public void deserializeModel(final ModelJson modelJson) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\entities.json");

        ModelJson newModelJson = objectMapper.readValue(file, ModelJson.class);

        modelJson.setCustomers(newModelJson.getCustomers());
        modelJson.setEmployees(newModelJson.getEmployees());
        modelJson.setOrders(newModelJson.getOrders());
        modelJson.setTemplates(newModelJson.getTemplates());
        modelJson.setAreas(newModelJson.getAreas());
        modelJson.setServices(newModelJson.getServices());
        modelJson.setLastId(newModelJson.getLastId());
    }

    public String serializeToString(Object object) throws IOException
    {
        return new ObjectMapper().writeValueAsString(object);
    }

    private boolean validate()
    {
        File schemaFile = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\schema.json");
        File entitiesFile = new File("C:\\Users\\Kirill\\IdeaProjects\\MiniOPF\\entities.json");
        try(InputStream schemaInput = new FileInputStream(schemaFile);
            InputStream entitiesInput = new FileInputStream(entitiesFile);)
        {
            JSONObject jsonSchema = new JSONObject(
                    new JSONTokener(schemaInput));
            JSONObject jsonSubject = new JSONObject(
                    new JSONTokener(entitiesInput));

            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonSubject);
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("File not found");
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
