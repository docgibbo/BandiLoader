package it.lozza;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class CollectionManager {
    private String name = null;
    private Properties mongoProps = null;
    private MongoClient mongo = null;
    private MongoCollection<Document> collection = null;

    public CollectionManager(String name) {
        this.name = name;
    }

    public void connect() throws Exception {
        InputStream input = new FileInputStream("mongo.properties");
        mongoProps = new Properties();
        mongoProps.load(input);
        mongo = new MongoClient(mongoProps.getProperty("mongo.host"), Integer.parseInt(mongoProps.getProperty("mongo.port")));
        MongoDatabase database = mongo.getDatabase(mongoProps.getProperty("mongo.db"));
        collection = database.getCollection(this.name);
    }

    public void disconnect() {
        if (mongo != null) mongo.close();
    }

    public void writeBando(ArrayList<Bando> bandi) {
        if (bandi.size() > 0) this.collection.insertMany(bandi);
    }

    public void clear() {
        this.collection.deleteMany(new Bando());
    }

    public long size() {
        return this.collection.countDocuments();
    }

    public String createTextIndex(ArrayList<String> fields) {
        ArrayList<Bson> indexes = new ArrayList<Bson>();
        for (int i = 0; i < fields.size(); i++) indexes.add(Indexes.text(fields.get(i)));
        return collection.createIndex(Indexes.compoundIndex(indexes));
    }

    public void dropTextIndex(String name) {
        this.collection.dropIndex(name);
    }

    public void dropAllIndexes() {
        this.collection.dropIndexes();
    }
}
