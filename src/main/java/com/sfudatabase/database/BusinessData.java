package main.java.com.sfudatabase.database;

public class BusinessData {
    
    private String businessID;
    private String name;
    private String address;
    private String city;
    private String stars;

    public BusinessData(String businessID, String name, String address, String city, String stars) {
        this.businessID = businessID;
        this.name = name;
        this.address = address;
        this.city = city;
        this.stars = stars;
    }

    public String getBusinessID() {
        return businessID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getStars() {
        return stars;
    }
}
