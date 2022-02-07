import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

public class Administrator
{
    private String adminID,adminName,gender,contact,password;
    
    public Administrator()
    {}
    
    public Administrator(String adminID,String adminName,String gender,String contact,String password)
    {
        this.adminID = adminID;
        this.adminName = adminName;
        this.gender = gender;
        this.contact = contact;
        this.password = password;
    }
    
    public String getAdminID()
    {return adminID;}
    
    public String getAdminName()
    {return adminName;}
    
    public String getAdminGender()
    {return gender;}
    
    public String getAdminContact()
    {return contact;}
    
    public String getAdminPassword()
    {return password;}
}