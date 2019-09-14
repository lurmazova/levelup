package data;

import utils.Constants;

import javax.persistence.*;

@Entity
@Table(name = Constants.TABLE_NAME)
public class Messages {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "message")
    public String message;

    @Column(name = "sender")
    public String sender;

    @Column(name = "thread_number", unique = true)
    public String threadNumber;

    public Messages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(String threadNumber) {
        this.threadNumber = threadNumber;
    }
}
