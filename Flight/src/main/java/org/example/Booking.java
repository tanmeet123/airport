package org.example;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public interface Booking {
    public InsertOneResult addBooking(Ticket tickets);
    public DeleteResult removeBooking(int id);
    public Document findBookingById(int id);
}
