import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class Feedback
{
    private String feedbackID,criteria,comment,traineeID;
    private int rating;
    
    private static ArrayList<Feedback> feedbackList = new ArrayList <> ();
    
    public Feedback(String feedbackID,String traineeID,String criteria,int rating,String comment)
    {
        if (feedbackID == null)//new feedback
        {
            try
            {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
                Statement statement = connection.createStatement();
                
                //read
                ResultSet result = statement.executeQuery("select COUNT(FeedbackID) AS LastFeedbackID from FEEDBACK");
                result.next();
                feedbackID = Integer.toString(result.getInt("LastFeedbackID")+1);
                
                //reformat feedbackID
                for (int i=1; i<=6; i++)
                {
                    if (feedbackID.length() == i)
                    {
                        String zeros = "";
                        for (int j=0; j<6-i; j++)
                            zeros = zeros + "0";
                        feedbackID = "FB" + zeros + feedbackID;
                    }
                }
                
                //write
                statement.executeUpdate("insert into FEEDBACK values ('" + feedbackID + "','" + traineeID + "','" + criteria + "','" + rating + "','" + comment + "')");
                
                connection.close();
            }
            catch(SQLException ex)
            {ex.printStackTrace();}
        }
        
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.comment = comment;
        this.criteria = criteria;
        this.traineeID = traineeID;
        feedbackList.add(this);
        
        Trainee t = Profile.getATrainee(traineeID);
        t.removeTodoFeedbackList(criteria);
    }
    
    public static ArrayList<Feedback> getAllFeedbackList()
    {return feedbackList;}
    
    public static ArrayList<Feedback> getFeedbackList(String criteria)
    {
        ArrayList<Feedback> list = new ArrayList <> ();
        
        for (int i=0; i<feedbackList.size(); i++)
        {
            if (feedbackList.get(i).getCriteria().equals(criteria))
                list.add(feedbackList.get(i));
        }
        
        return list;
    }
    
    public String getFeedbackID()
    {return feedbackID;}
    
    public String getCriteria()
    {return criteria;}
    
    public int getRating()
    {return rating;}
    
    public String getComment()
    {return comment;}
    
    public String getTraineeID()
    {return traineeID;}
}