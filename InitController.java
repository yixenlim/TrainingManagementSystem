import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class InitController
{
    public InitController()
    {
        //add subject
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from SUBJECT");
            
            while (result.next())
            {
                String subjectID = result.getString("SubjectID");
                String subjectName = result.getString("SubjectName");
                
                Subject s = new Subject(subjectID,subjectName);
            }
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
        
        //add subject info
        ArrayList <Subject> subjectList2 = Subject.getSubjectList();
        for (int i=0; i<subjectList2.size(); i++)
        {
            File infoLocation = new File("Subject Materials/" + subjectList2.get(i).getSubID() +"/Info.txt");
            try
            {
                Scanner sc = new Scanner(infoLocation);
                sc.useDelimiter(System.getProperty("line.separator"));
                while(sc.hasNext())
                    subjectList2.get(i).addInfo(sc.next());
            }
            catch(Exception ex)
            {ex.printStackTrace();}
        }
        
        //add admin
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from ADMINISTRATOR");
            
            while (result.next())
            {
                String adminID = result.getString("AdminID");
                String adminName = result.getString("AdminName");
                String gender = result.getString("Gender");
                String contact = result.getString("Contact");
                String password = result.getString("AdminPassword");
                
                Profile.addAdmin(adminID,adminName,gender,contact,password);
            }
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
        
        //add trainer
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");            
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from TRAINER");
            
            while (result.next())
            {
                String trainerID = result.getString("TrainerID");
                String trainerName = result.getString("TrainerName");
                String gender = result.getString("Gender");
                String contact = result.getString("Contact");
                String password = result.getString("TrainerPassword");
                String subID = result.getString("SubjectID");
                
                Profile.addTrainer(trainerID,trainerName,subID,gender,contact,password);
            }
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
        
        //add trainee
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from TRAINEE");
            
            while (result.next())
            {
                String traineeID = result.getString("TraineeID");
                String traineeName = result.getString("TraineeName");
                String gender = result.getString("Gender");
                String contact = result.getString("Contact");
                String password = result.getString("TraineePassword");
                
                Profile.addTrainee(traineeID,traineeName,gender,contact,password);
            }
            connection.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
        
        //add cert
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from CERTIFICATE");
            
            while (result.next())
            {
                String certID = result.getString("CertificateID");
                String certStatus = result.getString("CertificateStatus");
                String traineeID = result.getString("TraineeID");
                String adminID = result.getString("AdminID");
                
                Certificate c = new Certificate(certID,certStatus,traineeID,adminID);
            }
            connection.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
        
        //add class
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from CLASS");
            
            while (result.next())
            {
                String classID = result.getString("ClassID");
                String subID = result.getString("SubjectID");
                String date = result.getString("ClassDate");
                
                int year = Integer.parseInt(date.substring(0,4));
                int month = Integer.parseInt(date.substring(5,7));
                int day = Integer.parseInt(date.substring(8,10));
                
                Subject s = Subject.getASubject(subID);
                Class.initAddClass(subID,s.getSubName(),classID,year,month,day);
                Class c = Class.getAClass(classID);
                
                String nameList2 = result.getString("NameList");
                if (nameList2 != null)
                {
                    ArrayList <String> nameList = new ArrayList<String>(Arrays.asList(nameList2.split(" ")));
                    for (int i=0; i<nameList.size(); i++)
                        c.addTraineeToNameList(nameList.get(i),true);
                }
                
                String attendedList2 = result.getString("AttendedList");
                if (attendedList2 != null)
                {
                    ArrayList <String> attendedList = new ArrayList<String>(Arrays.asList(attendedList2.split(" ")));
                    for (int i=0; i<attendedList.size(); i++)
                        c.addTraineeToAttendedList(attendedList.get(i),true);
                }
            }
            connection.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
        
        //add request
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from REQUEST");
            
            while (result.next())
            {
                String requestID = result.getString("RequestID");
                String requestStatus = result.getString("RequestStatus");
                String traineeID = result.getString("TraineeID");
                String classID = result.getString("ClassID");
                String reason = result.getString("Reason");
                String adminID = result.getString("AdminID");
                
                Request r = new Request(requestID,traineeID,classID,reason,requestStatus,adminID);
            }
            connection.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
        
        //add feedback
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from FEEDBACK");
            
            while (result.next())
            {
                String feedbackID = result.getString("FeedbackID");
                String traineeID = result.getString("TraineeID");
                String criteria = result.getString("Criteria");
                int rating = result.getInt("Rating");
                String comment = result.getString("FeedbackComment");
                
                Feedback f = new Feedback(feedbackID,traineeID,criteria,rating,comment);
            }
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
    }
}