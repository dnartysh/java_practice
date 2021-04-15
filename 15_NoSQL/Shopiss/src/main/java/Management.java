import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

public class Management {
    private final String URI;
    private final int PORT;
    private final String DB_NAME;
    private MongoDatabase DB;
    private MongoCollection<Document> shopCollection;
    private MongoCollection<Document> productCollection;

    public Management (String uri, int port, String dbName) {
        this.URI = uri;
        this.PORT = port;
        this.DB_NAME = dbName;
    }

    public void initialize() {
        DB = getDbConnection();
        shopCollection = DB.getCollection("shops");
        productCollection = DB.getCollection("products");
    }

    private MongoDatabase getDbConnection() {
        return new MongoClient(URI, PORT).getDatabase(DB_NAME);
    }

    public void addShop(String name) {
        Document doc = new Document("name", name).append("products", new ArrayList<>());
        shopCollection.insertOne(doc);
        System.out.println("Adding " + name + " successful");
    }

    public void addProduct(String name, double price) {
        Document doc = new Document("name", name).append("price", price);
        productCollection.insertOne(doc);
        System.out.println("Adding " + name + " successful");
    }

    public void exposeProduct(String productName, String shopName) {
        Bson filter = Filters.eq("name", shopName);
        Set<String> list = new HashSet<>(getProductsByShopName(shopName));
        list.add(productName);

        Document docUpd = new Document("$set", new Document("products", list));
        shopCollection.updateOne(filter, docUpd);

    }

    private List<String> getProductsByShopName(String shopName) {
        BsonDocument query = BsonDocument.parse("{name: \"" + shopName + "\"}");
        List<String> list = new ArrayList<>();

        for (Document doc : shopCollection.find(query)) {
            list = doc.getList("products", String.class);
        }

        return list;
    }

    public void dropAll() {
        shopCollection.drop();
        productCollection.drop();
    }

    public void productsStatistics() {
        printCountProducts();
        printAvgPrice();
        printMinMaxPrice();
        printCountProductsLessValue();

        System.out.println("Products");
        productCollection.find().forEach((Consumer<Document>) System.out::println);
        System.out.println("Shops");
        shopCollection.find().forEach((Consumer<Document>) System.out::println);
    }

    private void printCountProducts() {
        Bson unwind = Aggregates.unwind("$products");
        Bson group = Aggregates.group("$name", Accumulators.sum("count_products", 1));

        System.out.println("Count products in shops");
        shopCollection.aggregate(Arrays.asList(unwind, group)).forEach((Consumer<Document>) System.out::println);
    }

    private void printAvgPrice() {
        Bson unwind = Aggregates.unwind("$products");
        Bson lookup = Aggregates.lookup("products", "products", "name", "products_list");
        Bson unwindProducts = Aggregates.unwind("$products_list");
        Bson group = Aggregates.group("$name", Accumulators.avg("avg_price", "$products_list.price"));

        System.out.println("Average price products");
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, group))
                .forEach((Consumer<Document>) System.out::println);
    }

    private void printMinMaxPrice() {
        Bson unwind = Aggregates.unwind("$products");
        Bson lookup = Aggregates.lookup("products", "products", "name", "products_list");
        Bson unwindProducts = Aggregates.unwind("$products_list");
        Bson minGroup = Aggregates.group("$name", Accumulators.min("min_price", "$products_list.price"));
        Bson maxGroup = Aggregates.group("$name", Accumulators.max("min_price", "$products_list.price"));

        System.out.println("Minimum product price");
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, minGroup))
                .forEach((Consumer<Document>) System.out::println);

        System.out.println("Maximum product price");
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, maxGroup))
                .forEach((Consumer<Document>) System.out::println);
    }

    private void printCountProductsLessValue() {
        Bson unwind = Aggregates.unwind("$products");
        Bson lookup = Aggregates.lookup("products", "products", "name", "products_list");
        Bson unwindProducts = Aggregates.unwind("$products_list");
        Bson group = Aggregates.group("$name", Accumulators.addToSet("price", "$products_list.price"));
        Bson filter = Filters.lt("products_list.price", 100);

        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, group))
                .forEach((Consumer<Document>) System.out::println);
    }
}




















