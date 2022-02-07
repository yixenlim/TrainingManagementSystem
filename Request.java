import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class Request
{
    private String requestID,traineeID,reason,classID,adminID;
    private String requestStatus = "Pending";
    
    private static ArrayList <Request> requestList = new ArrayList<> ();
    
    public Request()
    {}
    
    public Request(String requestID,String traineeID,String classID,String reason,String requestStatus,String adminID)
    {
        if (requestID == null)//new request
        {
            try
            {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
                Statement statement = connection.createStatement();
                
                //read
                ResultSet result = statement.executeQuery("select COUNT(RequestID) AS LastRequestID from REQUEST");
                result.next();
                requestID = Integer.toString(result.getInt("LastRequestID")+1);
                
                //reformat requestID
                for (int i=1; i<=6; i++)
                {
                    if (requestID.length() == i)
                    {
                        String zeros = "";
                        for (int j=0; j<6-i; j++)
                            zeros = zeros + "0";
                        requestID = "RQ" + zeros + requestID;
                    }
                }
                
                //write
                statement.executeUpdate("insert into REQUEST values ('" + requestID + "','Pending','" + traineeID + "','" + classID + "',null,'" + reason + "')");
                
                connection.close();
            }
            catch(SQLException ex)
            {ex.printStackTrace();}
        }
        
        this.requestID = requestID;
        this.traineeID = traineeID;
        this.reason = reason;
        this.classID = classID;
        this.requestStatus = requestStatus;
        this.adminID = adminID;
        requestList.add(this);
        Trainee t = Profile.getATrainee(traineeID);
        t.addRequestedClass(Class.getAClass(classID));
        
        if (requestStatus.equals("Decline"))
            t.addRequestableClass(Class.getAClass(classID));
    }
    
    public static ArrayList<Request> getRequestList()
    {return requestList;}
    
    public static Request getARequest(String reqID)
    {
        Request r = new Request();
        
        for (int i=0; i<requestList.size(); i++)
        {
            if (requestList.get(i).getRequestID().equals(reqID))
                r =  requestList.get(i);
        }
        return r;
    }
    
    public static ArrayList<Request> getUnmanagedRequestList()
    {
        ArrayList<Request> list = new ArrayList <> ();
        
        for (int i=0; i<requestList.size(); i++)
        {
            if (requestList.get(i).getRequestStatus().equals("Pending"))
                list.add(requestList.get(i));
        }
        
        return list;
    }
    
    public String getRequestID()
    {return requestID;}
    
    public String getTraineeID()
    {return traineeID;}
    
    public String getReason()
    {return reason;}
    
    public String getClassID()
    {return classID;}
    
    public void setRequestStatus(String status,String adminID)
    {
        requestStatus = status;
        this.adminID = adminID;
        
        Trainee t = Profile.getATrainee(traineeID);
        Class c = Class.getAClass(getClassID());
        if (status.equals("Accept"))//accept request
            t.dropClass(c);
        else if (status.equals("Decline"))//decline request
            t.addRequestableClass(c);
        
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            
            //write
            Statement statement = connection.createStatement();
            statement.executeUpdate("update REQUEST set RequestStatus = '" + status + "' where RequestID = '" + requestID + "'");
            statement.executeUpdate("update REQUEST set AdminID = '" + adminID + "' where RequestID = '" + requestID + "'");
            connection.close();
        }
        catch(SQLException e)
        {e.printStackTrace();}
    }
    
    public String getRequestStatus()
    {return requestStatus;}
}