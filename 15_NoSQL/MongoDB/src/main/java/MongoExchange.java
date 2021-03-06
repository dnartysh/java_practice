import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.Document;

public class MongoExchange {
    private final String URI;
    private final int PORT;
    private final String DB_NAME;
    private final MongoDatabase DATABASE;

    public MongoExchange(String uri, int port, String dbName) {
        this.URI = uri;
        this.PORT = port;
        this.DB_NAME = dbName;
        this.DATABASE = getDbConnection();
    }

    private MongoDatabase getDbConnection() {
        return new MongoClient(URI, PORT).getDatabase(DB_NAME);
    }

    private String getName(String value) {
        return value.substring(0, value.indexOf(',')).trim();
    }

    private int getAge(String value) {
        return Integer.parseInt(value.substring(value.indexOf(',') + 1,
                value.indexOf(',', value.indexOf(',') + 1)).trim());
    }

    private List<String> getCourses(String value) {
        return Arrays.asList(value.substring(value.indexOf('\"') + 1,
                value.length() - 1).split(",+"));
    }

    private List<Document> getListOfDocuments(String filePath) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(filePath));
        List<Document> listDocs = new ArrayList<>();

        list.forEach(s -> {
            Document doc = new Document("name", getName(s))
                    .append("age", getAge(s))
                    .append("courses", getCourses(s));
            listDocs.add(doc);
        });

        return listDocs;
    }

    public void writeToCollection(String filePath, String collectionName) throws IOException {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);
        collection.drop();

        collection.insertMany(getListOfDocuments(filePath));
    }

    public void printCountDocs(String collectionName) {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);

        System.out.printf("?????????? ???????????????????? ?????????????????? ?? ???????? - %d\n", collection.countDocuments());
    }

    public void printCountDocsByAge(String collectionName, int age) {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);
        BsonDocument query = BsonDocument.parse("{age: {$gt: " + age + "}}");

        System.out.println("???????????????????? ?????????????????? ???????????? " + age + " ?????? - " +
                collection.countDocuments(query));
    }

    public void printDocsByAge(String collectionName, boolean desc, int limit) {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);

        BsonDocument query = BsonDocument.parse("{age: " + (desc? -1 : 1) + "}");

        collection.find().sort(query).limit(limit).forEach((Consumer<Document>) s -> System.out
                        .printf("?????????? " + (desc? " ???????????? " : " ?????????????? ") + "?????????????? - %s\n",
                                BsonDocument.parse(s.toJson()).getString("name").getValue()));
    }

    public void printDocsByCourses(String collectionName, boolean desc, int limit) {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);

        BsonDocument query = BsonDocument.parse("{age: " + (desc? -1 : 1) + "}");

        collection.find().sort(query).limit(limit).forEach((Consumer<Document>) s -> System.out
                .printf("?????????? ????????????" + (desc? " ?????????????? ": " ???????????????? ") + "???????????????? - %s\n",
                        BsonDocument.parse(s.toJson()).getArray("courses").getValues()));
    }

    public void printAllDocs(String collectionName) {
        MongoCollection<Document> collection = DATABASE.getCollection(collectionName);

        for (Document doc : collection.find()) {
            System.out.println(doc);
        }
    }
}



























