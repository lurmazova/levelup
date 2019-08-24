package data;

public class Passenger {
    public Passenger(String firstName, String lastName, String bDate, String passNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bDate = bDate;
        this.passNumber = passNumber;
    }

    public String firstName;
    public String lastName;
    public String bDate;
    public String passNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }
}
