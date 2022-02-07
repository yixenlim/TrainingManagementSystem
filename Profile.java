import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class Profile
{
    private static ArrayList<Administrator> adminList = new ArrayList <> ();
    private static ArrayList<Trainer> trainerList = new ArrayList <> ();
    private static ArrayList <Trainee> traineeList = new ArrayList<> ();
    
    public static void addAdmin(String adminID,String adminName,String gender,String contact,String password)
    {
        Administrator a = new Administrator(adminID,adminName,gender,contact,password);
        adminList.add(a);
    }
    
    public static ArrayList<Administrator> getAdminList()
    {return adminList;}
    
    public static Administrator getAnAdmin(String ID)
    {
        Administrator a = new Administrator();
        
        for (int i=0; i<adminList.size(); i++)
        {
            if (adminList.get(i).getAdminID().equals(ID))
                a = adminList.get(i);
        }
        return a;
    }
    
    public static void addTrainer(String trainerID,String trainerName,String subID,String gender,String contact,String password)
    {
        Trainer t = new Trainer(trainerID,trainerName,subID,gender,contact,password);
        trainerList.add(t);
    }
    
    public static ArrayList<Trainer> getTrainerList()
    {return trainerList;}
    
    public static Trainer getATrainer(String trainerID)
    {
        Trainer t = new Trainer();
        for (int i=0; i<trainerList.size(); i++)
        {
            if (trainerID.equals(trainerList.get(i).getTrainerID()))
                t = trainerList.get(i);
        }  
        return t;
    }
    
    public static void addTrainee(String traineeID,String traineeName,String gender,String contact,String password)
    {
        Trainee t = new Trainee(traineeID,traineeName,gender,contact,password);
        traineeList.add(t);
    }
    
    public static ArrayList<Trainee> getTraineeList() 
    {return traineeList;}
    
    public static Trainee getATrainee(String traineeID)
    {
        Trainee t = new Trainee();
        for (int i=0; i<traineeList.size(); i++)
        {
            if (traineeID.equals(traineeList.get(i).getTraineeID()))
                t = traineeList.get(i);
        }
        return t;
    }
    
    public static ArrayList<Trainee> getCompletedTraineeList()
    {
        ArrayList<Trainee> list = new ArrayList <> ();
        
        for (int i=0; i<traineeList.size(); i++)
        {
            if (traineeList.get(i).getAttendedSubjectNum() == 5)
                list.add(traineeList.get(i));
        }
        
        return list;
    }
    
    public static ArrayList<Trainee> getNoCertTrainee()
    {
        ArrayList<Trainee> list = new ArrayList <> ();
        ArrayList<Certificate> certList = Certificate.getAllCert();
        
        for (int i=0; i<traineeList.size(); i++)
        {
            for (int j=0; j<certList.size(); j++)
            {
                if (traineeList.get(i).getAttendedSubjectNum() == 5 && certList.get(j).getTraineeID().equals(traineeList.get(i).getTraineeID()) && !certList.get(j).getCertStatus())
                    list.add(traineeList.get(i));
            }
        }
        
        return list;
    }
    
    public static ArrayList<Trainee> getNonFullClassTrainee(String subID)
    {
        ArrayList<Trainee> trainees = new ArrayList <> ();
        
        for (int i=0; i<traineeList.size(); i++)
        {
            if (!traineeList.get(i).haveClass(subID))
                trainees.add(traineeList.get(i));
        }
        
        return trainees;
    }
    
    public static ArrayList<String> addTrainee(String name,String gender,String contact)
    {
        ArrayList<String> credentials = new ArrayList <> ();
        String traineeID = "";
        String pswd = getPassword(8);
        
        String certID = Integer.toString(randInt());
        for (int i=1; i<=8; i++)
        {
            if (certID.length() == i)
            {
                String zeros = "";
                for (int j=0; j<8-i; j++)
                    zeros = zeros + "0";
                certID = "CT" + zeros + certID;
            }
        }
            
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            
            //read
            ResultSet result = statement.executeQuery("select COUNT(TraineeID) AS LastTraineeID from TRAINEE");
            result.next();
            traineeID = Integer.toString(result.getInt("LastTraineeID")+1);
            
            //reformat traineeID
            for (int i=1; i<=4; i++)
            {
                if (traineeID.length() == i)
                {
                    String zeros = "";
                    for (int j=0; j<4-i; j++)
                        zeros = zeros + "0";
                    traineeID = "TN" + zeros + traineeID;
                }
            }
                
            //write
            statement.executeUpdate("insert into TRAINEE values ('" + traineeID + "','" + name + "','" + gender + "','" + contact + "','" + pswd + "')");
            statement.executeUpdate("insert into CERTIFICATE values ('" + certID + "','false','" + traineeID + "',null)");
            
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
        
        addTrainee(traineeID,name,gender,contact,pswd);
        Certificate c = new Certificate(certID,"false",traineeID,null);
        
        credentials.add(traineeID);
        credentials.add(pswd);
        
        return credentials;
    }
    
    public static boolean duplicateContact(String contact)
    {
        for (int i=0; i<traineeList.size(); i++)
        {
            if (traineeList.get(i).getTraineeContact().equals(contact))
                return true;
        }
        return false;
    }
    
    public static int randInt()
    {
        Random rnd = new Random(System.currentTimeMillis());
        int randomNum = rnd.nextInt(999998) + 1;
        return randomNum;
    }
    
    //return "Admin" "Trainee" "Trainer" if username & password correct, return null if incorrect username/password
    public static String verifyLogin(String username,String pswd)
    {
        String user = null;
        
        if (username.length() == 6)
        {
            if (username.charAt(1) == 'D')
            {
                Administrator admin = Profile.getAnAdmin(username);
                if (admin.getAdminID() != null)
                {
                    if (pswd.equals(admin.getAdminPassword()))
                        user = "Admin";
                }
            }
            else if (username.charAt(1) == 'R')
            {
                Trainer trainer = Profile.getATrainer(username);
                if (trainer.getTrainerID() != null)
                {
                    if (pswd.equals(trainer.getTrainerPassword()))
                        user = "Trainer";
                }
            }
            else if (username.charAt(1) == 'N')
            {
                Trainee trainee = Profile.getATrainee(username);
                if (trainee.getTraineeID() != null)
                {
                    if (pswd.equals(trainee.getTraineePassword()))
                        user = "Trainee";
                }
            }
        }
        
        return user;
    }
    
    public static String getPassword(int n) 
    { 
        //choose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"; 
    
        //create StringBuffer size of AlphaNumericString 
        StringBuilder pswd = new StringBuilder(n); 
    
        for (int i = 0; i < n; i++)
        { 
            //generate a random number between 0 to AlphaNumericString variable length 
            int index = (int)(AlphaNumericString.length() * Math.random()); 

            //add Character one by one in end of sb 
            pswd.append(AlphaNumericString.charAt(index)); 
        } 
    
        return pswd.toString(); 
    }
}