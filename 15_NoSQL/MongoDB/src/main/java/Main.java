import java.io.IOException;

public class Main {
    private static final String COLLECTION_NAME = "students";

    public static void main(String[] args) throws IOException {
        MongoExchange mongo = new MongoExchange("localhost", 27017, "local");

        mongo.writeToCollection("src/files/mongo.csv", COLLECTION_NAME);

        mongo.printAllDocs(COLLECTION_NAME);
        mongo.printCountDocs(COLLECTION_NAME);
        mongo.printCountDocsByAge(COLLECTION_NAME, 40);
        mongo.printDocsByAge(COLLECTION_NAME, false, 1);
        mongo.printDocsByCourses(COLLECTION_NAME, true, 1);
    }

}
