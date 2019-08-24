import utils.JsonWriter;

import java.io.IOException;
import java.sql.Date;

public class runner {
    public static void main(String args[]) throws IOException {
        JsonWriter jsonWriter = new JsonWriter();
        jsonWriter.booking2Json("J30", "2019-01-01 00:00", "343sdf");
        jsonWriter.passenger2Json("John", "Smith","2000-05-05", "123 3434 112");
        jsonWriter.bookingFromJson();
        jsonWriter.passengerFromJson();

    }
}
