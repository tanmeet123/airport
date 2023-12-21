package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.mongodb.client.*;
import com.mongodb.MongoCredential;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static java.util.logging.Level.ALL;

public class airport implements Booking {
    InputStreamReader isr;
    BufferedReader buff;
    Connection connection;
    Statement statement;
    MongoDatabase database;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public airport() {
        try {
            if (isr == null) {
                isr = new InputStreamReader(System.in);
            }
            if (buff == null) {
                buff = new BufferedReader(isr);
            }

            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            logger.log(Level.INFO,"Successfully Connected to the database");
            database = mongoClient.getDatabase("tickets");
            database.createCollection("ticket");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workingDB", "root", "tanmeet");
            //this.statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ResultSet resultSet;
    Ticket ticket = null;

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        airport portal = new airport();
        try {
            ticket = new Ticket();
            MongoCollection<Document> collection = portal.database.getCollection("ticket");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int exit = 0;

        while (true) {
            portal.logger.log(Level.INFO,"\t1. Add Booking\n\t2. Remove Booking\n\t3. Find Bookings");

            int choose = 0;
            try {
                choose = Integer.parseInt(portal.buff.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int id = 0;
            switch (choose) {
                case 1:
                    String title = "", category = "";

                    float price = 0.0f;
                    portal.logger.log(Level.INFO,"Please enter your the following details:");
                    portal.logger.log(Level.INFO,"ID:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.logger.log(Level.INFO,"place:");
                    try {
                        title = (portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.logger.log(Level.INFO,"price:");
                    try {
                        price = Float.parseFloat(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.logger.log(Level.INFO,"Category:");
                    try {
                        category = (portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ticket = new Ticket(id, title, price, category);
                    portal.addBooking(ticket);
                    portal.logger.log(Level.INFO,"ticket added successfully!");
                    break;
                case 2:
                    portal.logger.log(Level.INFO,"Please enter ID of the ticket to find:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.removeBooking(id);
                    portal.logger.log(Level.INFO,"ticket removed successfully!");
                    break;
                case 3:
                    portal.logger.log(Level.INFO,"Please enter ID of the ticket to find:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.findBookingById(id);
                    break;
            }
            portal.logger.log(Level.INFO,"Exit? (1)");
            try {
                exit = Integer.parseInt(portal.buff.readLine());
            } catch (Exception e) {

            }
            if (exit == 1)
                break;
        }
    }

    public InsertOneResult addBooking(Ticket ticket) {

        InsertOneResult result;
        try {
            Document document = new Document();
            document.append("id", ticket.getID());
            document.append("title", ticket.getTitle());
            document.append("price", ticket.getPrice());
            document.append("category", ticket.getCategory());

            result = database.getCollection("ticket").insertOne(document);
            //PreparedStatement preparedStatement = connection.prepareStatement("insert into tickets values(?, ?, ?, ?)");
            //preparedStatement.setInt(1, ticket.getID());
            //preparedStatement.setString(2, ticket.getTitle());
            //preparedStatement.setFloat(3, ticket.getPrice());
            //preparedStatement.setString(4, ticket.getCategory());
            //preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public DeleteResult removeBooking(int id) {
        DeleteResult resultSet;
        try {
            resultSet = database.getCollection("ticket").deleteOne(eq("id", id));
            //PreparedStatement preparedStatement = connection.prepareStatement("delete from tickets where id=(?)");
            //preparedStatement.setInt(1, id);
            //preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public Document findBookingById(int id) {
        Document result;
        try {
            //PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets where price=(?)");
            //preparedStatement.setInt(1, id);
            //resultSet = preparedStatement.executeQuery();
            MongoCollection<Document> mongoCollection = database.getCollection("ticket");
            Bson filter = Filters.eq("id", id);
            //Bson projection = Projections.fields(Projections.include("id","title","price","category"));
            FindIterable<Document> document = mongoCollection.find(filter);//.projection(projection);
            MongoCursor<Document> cursor = document.iterator();
            result = cursor.next();
            while (cursor.hasNext()) {
                //Ticket ticket = new Ticket();resultSet.next()
                //ticket.setID(resultSet.getInt(1));
                //ticket.setTitle(resultSet.getString(3));
                //ticket.setPrice(resultSet.getFloat(4));
                //ticket.setCategory(resultSet.getString(5));
                logger.log(ALL, String.valueOf(cursor.next()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}