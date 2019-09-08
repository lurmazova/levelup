package utils;

public class Constants {
    public Constants() {
    }

    public static final String RESOURCE_PATH = "./src/main/resources/results/";
    public static final String BOOKING_JSON_NAME = "booking.json";
    public static final String PASSENGER_JSON_NAME = "passenger.json";
    public static final String BOOKING_MULTIPLE_THREADS_NAME = "booking_threads.txt";

    public static final int SERVER_PORT = 5000;
    public static final int THREADS_COUNT = 100;

    public static final String DB_URL = "jdbc:h2:~/test";
    public static final String TABLE_NAME = "Messages";
    public static final String THREAD_NUMBER_FIELD = "THREAD_NUMBER";
    public static final String CLIENT_NAME_FIELD = "CLIENT_NAME";
    public static final String MESSAGE_FIELD = "MESSAGE";

}
