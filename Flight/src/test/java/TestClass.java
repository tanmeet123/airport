import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.Ticket;
import org.example.airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;

public class TestClass {
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase db = mongoClient.getDatabase("tickets");
    airport a = new airport();
    @Test
    void addBookingTest(){
        //Document document = new Document();
        //document.append("id",101).append("title", "test").append("price", "1000f").append("category", "test");
        //InsertOneResult expected = db.getCollection("ticket").insertOne(document);
        Ticket ticket = new Ticket(101, "test", 1000f, "test");
        InsertOneResult actual = a.addBooking(ticket);
        Assertions.assertEquals(true, actual.wasAcknowledged());
    }
    @Test
    void FindBookingTest(){
        MongoCollection<Document> mongoCollection = db.getCollection("ticket");
        Bson filter = Filters.eq("id", 101);
        //Bson projection = Projections.fields(Projections.include("id","title","price","category"));
        FindIterable<Document> document = mongoCollection.find(filter);//.projection(projection);
        MongoCursor<Document> cursor = document.iterator();
        Document expected = cursor.next();
        Document actual = a.findBookingById(101);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void removeBookingTest(){
        DeleteResult expected = db.getCollection("ticket").deleteOne(eq("id", 101));
        Document document = new Document();
        document.append("id",101).append("title", "test").append("price", "1000").append("category", "test");
        DeleteResult actual = a.removeBooking(101);
        Assertions.assertEquals(expected, actual);
    }

}
