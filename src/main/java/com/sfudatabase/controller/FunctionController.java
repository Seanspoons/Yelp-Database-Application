package main.java.com.sfudatabase.controller;

import java.sql.Connection;

public class FunctionController {
    
    Connection con;
    public static Boolean loggedIn = false;
    String userID;

    public FunctionController(Connection con) {
        this.con = con;
    }

}
