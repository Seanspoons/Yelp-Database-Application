package database;

public class UserData {

    private String userID;
    private String name;
    private String reviewCount;
    private String useful;
    private String funny;
    private String cool;
    private String averageStars;
    private String signupDate;

    public UserData(String userID, String name, String reviewCount, String useful, String funny, String cool, String averageStars, String signupDate) {
        this.userID = userID;
        this.name = name;
        this.reviewCount = reviewCount;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.averageStars = averageStars;
        this.signupDate = signupDate;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public String getUseful() {
        return useful;
    }

    public String getFunny() {
        return funny;
    }

    public String getCool() {
        return cool;
    }

    public String getAverageStars() {
        return averageStars;
    }

    public String getSignupDate() {
        return signupDate;
    }
    
}
