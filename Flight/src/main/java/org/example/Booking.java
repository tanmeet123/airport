package org.example;

public interface Booking {
    public void addBooking(Ticket tickets);
    public void removeBooking(int id);
    public void findBookingById(int id);
}
