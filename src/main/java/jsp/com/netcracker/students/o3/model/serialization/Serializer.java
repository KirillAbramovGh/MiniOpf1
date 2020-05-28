package jsp.com.netcracker.students.o3.model.serialization;

import jsp.com.netcracker.students.o3.model.ModelJson;

import java.io.IOException;


/**
 * serialise model to json and write to file
 * deserialize model from json
 */
public interface Serializer
{
    /**
     * serialise model to json and write to file
     * @param jsonEntitiesStorage we need to serialize
     */
    void serializeEntityStorage(JsonEntitiesStorage jsonEntitiesStorage) throws IOException;

    void serializeModel(ModelJson modelJson) throws IOException;
    /**
     * read json from file, deserialize to model and set it to param
     * @param jsonEntitiesStorage we need deserialize to it
     */
    void deserializeEntityStorage(JsonEntitiesStorage jsonEntitiesStorage) throws IOException;
    void deserializeModel(ModelJson modelJson) throws IOException;

    String serializeToString(Object object) throws IOException;
}
