package multithreading;

import com.sun.tools.javac.Main;
import utils.FileManager;
import utils.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static utils.Constants.BOOKING_MULTIPLE_THREADS_NAME;
import static utils.Constants.RESOURCE_PATH;

public class ThreadsMaker {
    public ThreadsMaker() {
    }

    Main main = new Main();
    JsonWriter jsonWriter = new JsonWriter();
    String result = "";

    public void runJsonWriterInSeveralThreads() throws InterruptedException, IOException {
        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (main) {
                    result = result +
                            jsonWriter.booking2Json("J30", "2019-01-01 00:00", "343sdf");
                }

            }
        };

        ArrayList<Thread> children = new ArrayList<Thread>();

        for(int i=0; i<10; ++i) {
            Thread thread = new Thread(runnable, "thread " + i);
            thread.start();
            children.add(thread);
        }

        for(Thread child : children){
            child.join();
        }

        result2File(RESOURCE_PATH + BOOKING_MULTIPLE_THREADS_NAME, result);

        }

        private void result2File(String filePath, String result) throws IOException {
            FileManager.createFileIfNotExists(filePath);
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.append(result);
            fileWriter.close();
            System.out.println("Results are saved to " + filePath);
    }
}
