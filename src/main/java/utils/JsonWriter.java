package utils;

import com.google.gson.Gson;
import data.Booking;
import data.Passenger;

import java.io.*;
import java.util.Date;

import static utils.Constants.*;

public class JsonWriter {
    public JsonWriter() throws IOException {
    }

    public void booking2Json(String flightNumber, String flightDateTime, String bookingNumber) throws IOException {
        String filePath = resourcePath + bookingJsonName;

        Booking booking = new Booking();
        booking.setFlightNumber(flightNumber);
        booking.setFlightDateTime(flightDateTime);
        booking.setBookingNumber(bookingNumber);

        createFileIfNotExists(filePath);
        FileWriter fileWriter = new FileWriter(filePath);
        Gson gson = new Gson();
        gson.toJson(booking, fileWriter);
        fileWriter.flush();
        System.out.println(gson.toJson(booking));
    }

    public void passenger2Json(String firstName, String lastName, String bDate, String passNumber) throws IOException {
        String filePath = resourcePath + passengerJsonName;

        Passenger passenger = new Passenger();
        passenger.setbDate(bDate);
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setPassNumber(passNumber);

        createFileIfNotExists(filePath);
        FileWriter fileWriter = new FileWriter(filePath);
        Gson gson = new Gson();
        gson.toJson(passenger, fileWriter);
        fileWriter.flush();
        System.out.println(gson.toJson(passenger));
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) file.createNewFile();
    }

    public void bookingFromJson() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(resourcePath + bookingJsonName);
        Booking bookingFromJson = gson.fromJson(fileReader, Booking.class);
        System.out.println("Booking Number is " + bookingFromJson.getBookingNumber());
        System.out.println("Flight Date and time is " + bookingFromJson.getFlightDateTime());
        System.out.println("Flight number is " + bookingFromJson.getFlightNumber());
    }

    public void passengerFromJson() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(resourcePath + passengerJsonName);
        Passenger passengerFromJson = gson.fromJson(fileReader, Passenger.class);
        System.out.println("First Name is " + passengerFromJson.getFirstName());
        System.out.println("Last Name is " + passengerFromJson.getLastName());
        System.out.println("Pass number is " + passengerFromJson.getPassNumber());
        System.out.println("BDay is " + passengerFromJson.getbDate());
    }
}

