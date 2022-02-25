package wood.mike.misc;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import wood.mike.helper.Person;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * JSON creation/parsing with gson
 */
public class JsonHandling {

    private static final String FILE = "src/main/resources/person.json";

    public static void main(String[] args) {
        var jh = new JsonHandling();
        jh.jsonFromObjectToFile();
        jh.jsonFromFileToObjects();
    }

    /**
     * Create objects, convert to JSON & write to file
     */
    private void jsonFromObjectToFile() {
        try (FileWriter writer = new FileWriter( FILE )) {
            getGson().toJson( cyclists(), writer );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return list of Person
     */
    private List<Person> cyclists() {
        return List.of(
            new Person.PersonBuilder("Bradley", "Wiggins" ).dob( "1980-04-28" ).build(),
            new Person.PersonBuilder("Jens", "Voigt" ).dob( "1971-09-17" ).build(),
            new Person.PersonBuilder( "Fränk", "Schleck" ).dob( "1980-04-15" ).build()
        );
    }

    /**
     * Read JSON from file & create objects
     */
    private void jsonFromFileToObjects() {
        try ( Reader reader = Files.newBufferedReader(Paths.get( FILE )) ){
            List<Person> cyclists = getGson().fromJson(reader, new TypeToken<List<Person>>() {}.getType());
            cyclists.forEach(System.out::println);
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }

    private Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    /**
     * Class to assist with serializing and deserializing Person dob & dod which is a LocalDate
     * We convert it to/from yyyy-mm-dd
     */
    static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            return LocalDate.parse( jsonElement.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE );
        }
    }
}
