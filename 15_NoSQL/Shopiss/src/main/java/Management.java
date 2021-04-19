import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
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

    public Management(String uri, int port, String dbName) {
        this.URI = uri;
        this.PORT = port;
        this.DB_NAME = dbName;
    }

    public void initialize() {
        DB = getDbConnection();
        shopCollection = DB.getCollection(Constants.SHOPS);
        productCollection = DB.getCollection(Constants.PRODUCTS);
    }

    private MongoDatabase getDbConnection() {
        return new MongoClient(URI, PORT).getDatabase(DB_NAME);
    }

    public void addShop(String name) {
        Document doc = new Document(Constants.NAME, name)
                .append(Constants.PRODUCTS, new ArrayList<>());
        shopCollection.insertOne(doc);
        System.out.println(Constants.SUCCESS);
    }

    public void addProduct(String name, double price) {
        Document doc = new Document(Constants.NAME, name).append(Constants.PRICE, price);
        productCollection.insertOne(doc);
        System.out.println(Constants.SUCCESS);
    }

    public void exposeProduct(String productName, String shopName) {
        Bson filter = Filters.eq(Constants.NAME, shopName);
        Set<String> list = new HashSet<>(getProductsByShopName(shopName));
        list.add(productName);

        Document docUpd = new Document(Constants.$SET, new Document(Constants.PRODUCTS, list));
        shopCollection.updateOne(filter, docUpd);

    }

    private List<String> getProductsByShopName(String shopName) {
        BsonDocument query = BsonDocument.parse("{" + Constants.NAME + ": \"" + shopName + "\"}");
        List<String> list = new ArrayList<>();

        for (Document doc : shopCollection.find(query)) {
            list = doc.getList(Constants.PRODUCTS, String.class);
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
    }

    private void printCountProducts() {
        Bson unwind = Aggregates.unwind(Constants.$PRODUCTS);
        Bson group = Aggregates
                .group(Constants.$NAME, Accumulators.sum(Constants.COUNT_PRODUCTS, 1));

        System.out.println(Constants.COUNT_PRODUCTS_IN_SHOPS);
        shopCollection.aggregate(Arrays.asList(unwind, group))
                .forEach((Consumer<Document>) System.out::println);
    }

    private void printAvgPrice() {
        Bson unwind = Aggregates.unwind(Constants.$PRODUCTS);
        Bson lookup = Aggregates.lookup(Constants.PRODUCTS, Constants.PRODUCTS, Constants.NAME,
                Constants.PRODUCTS_LIST);
        Bson unwindProducts = Aggregates.unwind(Constants.$PRODUCTS_LIST);
        Bson group = Aggregates.group(Constants.$NAME,
                Accumulators.avg(Constants.AVG_PRICE, Constants.$PRODUCTS_LIST_PRICE));

        System.out.println(Constants.AVERAGE_PRICE);
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, group))
                .forEach((Consumer<Document>) System.out::println);
    }

    private void printMinMaxPrice() {
        Bson unwind = Aggregates.unwind(Constants.$PRODUCTS);
        Bson lookup = Aggregates.lookup(Constants.PRODUCTS, Constants.PRODUCTS, Constants.NAME,
                Constants.PRODUCTS_LIST);
        Bson unwindProducts = Aggregates.unwind(Constants.$PRODUCTS_LIST);
        Bson minGroup = Aggregates.group(Constants.$NAME,
                Accumulators.min(Constants.MIN_PRICE, Constants.$PRODUCTS_LIST_PRICE));
        Bson maxGroup = Aggregates.group(Constants.$NAME,
                Accumulators.max(Constants.MAX_PRICE, Constants.$PRODUCTS_LIST_PRICE));

        System.out.println(Constants.MINIMUM_PRICE);
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, minGroup))
                .forEach((Consumer<Document>) System.out::println);

        System.out.println(Constants.MAXIMUM_PRICE);
        shopCollection.aggregate(Arrays.asList(unwind, lookup, unwindProducts, maxGroup))
                .forEach((Consumer<Document>) System.out::println);
    }

    private void printCountProductsLessValue() {
        Bson unwind = Aggregates.unwind(Constants.$PRODUCTS);
        Bson lookup = Aggregates.lookup(Constants.PRODUCTS, Constants.PRODUCTS, Constants.NAME,
                Constants.PRODUCTS_LIST);
        Bson unwindProducts = Aggregates.unwind(Constants.$PRODUCTS_LIST);
        Bson match = Aggregates.match(Filters.lt(Constants.PRODUCTS_LIST_PRICE, 100));
        Bson group = Aggregates
                .group(Constants.$NAME, Accumulators.sum(Constants.COUNT_PRODUCTS, 1));

        System.out.println(Constants.COUNT_PRODUCTS_LESS_100);
        AggregateIterable<Document> list = shopCollection
                .aggregate(Arrays.asList(unwind, lookup, unwindProducts, match, group));
        list.forEach((Consumer<Document>) System.out::println);
    }
}