package mongodb;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;

import java.util.Iterator;
import java.util.List;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Created by PerkinsZhu on 2018/3/14 11:11
 **/
public class MongodbTest {

    //    MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
    //    MongoClientOptions option =  MongoClientOptions.builder().build();
    //    MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1"),option);
    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://root:root@192.168.10.158:27017/admin?authMode=scram-sha1"));

    @Test
    public void testConnect() {
        // 1.数据库列表
        for (String s : mongoClient.listDatabaseNames()) {
            out.println("DatabaseName=" + s);
        }
        MongoDatabase db = mongoClient.getDatabase("Student");
        Iterator iterator = db.listCollectionNames().iterator();
        while (iterator.hasNext()) {
            out.println(iterator.next());
        }
    }

    @Test
    public void testOpLog() {
        MongoCollection collection = mongoClient.getDatabase("test").getCollection("Student");
        List<Bson> pipeline = singletonList(Aggregates.match(Filters.or(Document.parse("{'fullDocument.username': 'alice'}"))));
        MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch(pipeline).fullDocument(FullDocument.UPDATE_LOOKUP).iterator();
        while(cursor.hasNext()){
            ChangeStreamDocument<Document> next = cursor.next();
            out.println(next);
        }
    }
    @Test
    public  void testChangeStream(){
        MongoCollection collection = mongoClient.getDatabase("test").getCollection("Student");
        List<Bson> pipeline = singletonList(Aggregates.match(Filters.or(Document.parse("{'fullDocument.username': 'alice'}"))));
        MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch(pipeline).fullDocument(FullDocument.UPDATE_LOOKUP).iterator();
        while(cursor.hasNext()){
            ChangeStreamDocument<Document> next = cursor.next();
            out.println("接收到推送数据:"+next);
        }
    }



}
