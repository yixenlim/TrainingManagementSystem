import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class Certificate
{
    private String certificateID,traineeID,adminID;
    private boolean certStatus = false;
    
    private static ArrayList<Certificate> certList = new ArrayList <> ();
    
    public Certificate()
    {}
    
    public Certificate(String certID,String certS,String traineeID,String adminID)
    {
        certificateID = certID;
        this.traineeID = traineeID;
        this.adminID = adminID;
        if (certS.equals("false"))
            certStatus = false;
        else
            certStatus = true;
        certList.add(this);
    }
    
    public static ArrayList<Certificate> getAllCert()
    {return certList;}
    
    public static ArrayList<Certificate> getCertOfTrainees(ArrayList<Trainee> trainees)
    {
        ArrayList<Certificate> certList2 = new ArrayList <> ();
        
        for (int i=0; i<trainees.size(); i++)
        {
            for (int j=0; j<certList.size(); j++)
            {
                if (trainees.get(i).getTraineeID().equals(certList.get(j).getTraineeID()))
                    certList2.add(certList.get(j));
            }
        }
        
        return certList2;//2
    }
    
    public static Certificate getACertOfTrainee(String traineeID)
    {
        Certificate c = new Certificate();
        for (int i=0; i<certList.size(); i++)
        {
            if (traineeID.equals(certList.get(i).getTraineeID()))
                c = certList.get(i);
        }
        return c;
    }
    
    public String getCertID()
    {return certificateID;}
    
    public String getAdminID()
    {return adminID;}
    
    public String getTraineeID()
    {return traineeID;}
    
    //when admin upload cert
    public void setCertStatus(boolean b,String adminID)
    {
        certStatus = b;
        this.adminID = adminID;
        
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            
            //write
            Statement statement = connection.createStatement();
            statement.executeUpdate("update CERTIFICATE set CertificateStatus = '" + Boolean.toString(certStatus) + "' where CertificateID = '" + certificateID + "'");
            statement.executeUpdate("update CERTIFICATE set AdminID = '" + adminID + "' where CertificateID = '" + certificateID + "'");
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
    }
    
    public boolean getCertStatus()
    {return certStatus;}
}