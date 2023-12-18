package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import com.mongodb.client.*;
import com.mongodb.MongoCredential;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class airport implements Booking {
    InputStreamReader isr;
    BufferedReader buff;
    Connection connection;
    Statement statement;
    MongoDatabase database;

    airport() {
        try {
            if (isr == null) {
                isr = new InputStreamReader(System.in);
            }
            if (buff == null) {
                buff = new BufferedReader(isr);
            }

            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("Successfully Connected to the database");
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
        Ticket ticket = new Ticket();airport portal = new airport();
        try {
            ticket = new Ticket();
            MongoCollection<Document> collection = portal.database.getCollection("ticket");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int exit = 0;

        while (true) {
            System.out.println("\t1. Add Booking\n\t2. Remove Booking\n\t3. Find Bookings");

            int choose = 0;
            try {
                choose = Integer.parseInt(portal.buff.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int id = 0;
            switch (choose) {
                case 1:
                    portal.addBooking(ticket);
                    System.out.println("ticket added successfully!");
                    break;
                case 2:
                    System.out.println("Please enter ID of the ticket to find:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.removeBooking(id);
                    System.out.println("ticket removed successfully!");
                    break;
                case 3:
                    System.out.println("Please enter ID of the ticket to find:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.findBookingById(id);
                    break;
            }
            System.out.println("Exit? (1)");
            try {
                exit = Integer.parseInt(portal.buff.readLine());
            } catch (Exception e) {

            }
            if (exit==1)
                break;
        }
    }

    public void addBooking(Ticket ticket) {
        String title = "", category = "";
        int id = 0;
        float price = 0.0f;
        System.out.println("Please enter your the following details:");
        System.out.println("ID:");
        try {
            id = Integer.parseInt(buff.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("place:");
        try {
            title = (buff.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("price:");
        try {
            price = Float.parseFloat(buff.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Category:");
        try {
            category = (buff.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ticket = new Ticket(id, title, price, category);

        try {
            Document document = new Document();
            document.append("id",ticket.getID());
            document.append("title", ticket.getTitle());
            document.append("price", ticket.getPrice());
            document.append("category", ticket.getCategory());
            database.getCollection("ticket").insertOne(document);
            //PreparedStatement preparedStatement = connection.prepareStatement("insert into tickets values(?, ?, ?, ?)");
            //preparedStatement.setInt(1, ticket.getID());
            //preparedStatement.setString(2, ticket.getTitle());
            //preparedStatement.setFloat(3, ticket.getPrice());
            //preparedStatement.setString(4, ticket.getCategory());
            //preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBooking(int id) {
        try {
            database.getCollection("ticket").deleteOne(eq("id", id));
            //PreparedStatement preparedStatement = connection.prepareStatement("delete from tickets where id=(?)");
            //preparedStatement.setInt(1, id);
            //preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void findBookingById(int id) {
        try {
            //PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets where price=(?)");
            //preparedStatement.setInt(1, id);
            //resultSet = preparedStatement.executeQuery();
            MongoCollection<Document> mongoCollection = database.getCollection("ticket");
            Bson filter = Filters.eq("id", id);
            //Bson projection = Projections.fields(Projections.include("id","title","price","category"));
            FindIterable<Document> document = mongoCollection.find(filter);//.projection(projection);
            MongoCursor<Document> cursor = document.iterator();
            while (cursor.hasNext()) {
                //Ticket ticket = new Ticket();resultSet.next()
                //ticket.setID(resultSet.getInt(1));
                //ticket.setTitle(resultSet.getString(3));
                //ticket.setPrice(resultSet.getFloat(4));
                //ticket.setCategory(resultSet.getString(5));
                System.out.println(cursor.next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}