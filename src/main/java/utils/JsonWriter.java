package utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import data.Booking;
import data.Passenger;

import java.io.*;

import static utils.Constants.*;

public class JsonWriter {
    public JsonWriter() {
    }

    public String booking2Json(String flightNumber, String flightDateTime, String bookingNumber) {
            String filePath = RESOURCE_PATH + BOOKING_JSON_NAME;

            Booking booking = new Booking(flightNumber, bookingNumber, flightDateTime);
            booking.setFlightNumber(flightNumber);
            booking.setFlightDateTime(flightDateTime);
            booking.setBookingNumber(bookingNumber);

            Gson gson = new Gson();
            return gson.toJson(booking);

    }

    public void passenger2Json(String firstName, String lastName, String bDate, String passNumber) {
        try {
            String filePath = RESOURCE_PATH + PASSENGER_JSON_NAME;

            Passenger passenger = new Passenger(firstName, lastName, bDate, passNumber);
            passenger.setbDate(bDate);
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setPassNumber(passNumber);

            createFileIfNotExists(filePath);
            FileWriter fileWriter = new FileWriter(filePath);
            Gson gson = new Gson();
            gson.toJson(passenger, fileWriter);
            fileWriter.close();
            System.out.println(gson.toJson(passenger));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) file.createNewFile();
    }

    public void bookingFromJson() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(RESOURCE_PATH + BOOKING_JSON_NAME);
        Booking bookingFromJson = gson.fromJson(fileReader, Booking.class);
        System.out.println("Booking Number is " + bookingFromJson.getBookingNumber());
        System.out.println("Flight Date and time is " + bookingFromJson.getFlightDateTime());
        System.out.println("Flight number is " + bookingFromJson.getFlightNumber());
    }

    public void passengerFromJson() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(RESOURCE_PATH + PASSENGER_JSON_NAME);
        Passenger passengerFromJson = gson.fromJson(fileReader, Passenger.class);
        System.out.println("First Name is " + passengerFromJson.getFirstName());
        System.out.println("Last Name is " + passengerFromJson.getLastName());
        System.out.println("Pass number is " + passengerFromJson.getPassNumber());
        System.out.println("BDay is " + passengerFromJson.getbDate());
    }
}

