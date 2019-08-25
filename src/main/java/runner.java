import multithreading.ThreadsMaker;
import utils.JsonWriter;

import java.io.IOException;
import java.sql.Date;

public class runner {
    public static void main(String args[]) throws IOException, InterruptedException {
        JsonWriter jsonWriter = new JsonWriter();
        jsonWriter.bookingFromJson();
        jsonWriter.passengerFromJson();

        ThreadsMaker threadsMaker = new ThreadsMaker();
        threadsMaker.runJsonWriterInSeveralThreads();

    }
}
