package data;

public class Booking {

    public String flightNumber;
    public String bookingNumber;
    public String flightDateTime;

    public Booking(String flightNumber, String bookingNumber, String flightDateTime) {
        this.flightNumber = flightNumber;
        this.bookingNumber = bookingNumber;
        this.flightDateTime = flightDateTime;
    }

    public String getFlightDateTime() {
        return flightDateTime;
    }

    public void setFlightDateTime(String flightDateTime) {
        this.flightDateTime = flightDateTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }


}
