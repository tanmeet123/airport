package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class AirportPortal implements Booking {
    InputStreamReader isr;
    BufferedReader buff;
    Connection connection;
    Statement statement;

    AirportPortal() {
        try {
            if (isr == null) {
                isr = new InputStreamReader(System.in);
            }
            if (buff == null) {
                buff = new BufferedReader(isr);
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workingDB", "root", "tanmeet");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ResultSet resultSet;
    Ticket ticket = null;

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        try {
            ticket = new Ticket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AirportPortal portal = new AirportPortal();
        int exit = 0;

        while (true) {
            System.out.println("\t1. Add Booking\n\t2. Remove Booking\n\t4. Find Bookings");

            int choose = 0;
            try {
                choose = Integer.parseInt(portal.buff.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (choose) {
                case 1:
                    portal.addBooking(ticket);
                    System.out.println("ticket added successfully!");
                    break;
                case 2:
                    int id = 2;
                    portal.removeBooking(id);
                    System.out.println("ticket removed successfully!");
                    break;
                case 3:
                    int idd = 2;
                    System.out.println("Please enter ID of the ticket to find:");
                    try {
                        id = Integer.parseInt(portal.buff.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    portal.removeBooking(idd);
                    System.out.println("ticket removed successfully");
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
            PreparedStatement preparedStatement = connection.prepareStatement("insert into tickets values(?, ?, ?, ?)");
            preparedStatement.setInt(1, ticket.getID());
            preparedStatement.setString(2, ticket.getTitle());
            preparedStatement.setFloat(3, ticket.getPrice());
            preparedStatement.setString(4, ticket.getCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBooking(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from tickets where id=(?)");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findBookingById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from tickets where price=(?)");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setID(resultSet.getInt(1));
                ticket.setTitle(resultSet.getString(3));
                ticket.setPrice(resultSet.getFloat(4));
                ticket.setCategory(resultSet.getString(5));
                System.out.println(ticket.getID() + " , " + ticket.getTitle() + " , " + ticket.getPrice() + " , " + ticket.getCategory());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}