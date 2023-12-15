package org.example;

public interface Booking {
    public void addBook(Booking booking);
    public void updateBook(Booking booking);
    public void removeBook(Booking booking);
    public void findBookByName(String name);
    public void findBookByCategory(String category);
    public void findBookByCost(Float price);
    public void findBookByCostRange(Float startingPrice, Float endingPrice);
    public void findCheapestBook();
    public void findCostliestBook();
}
