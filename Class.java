import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class Class
{
    private String classID,subjectID,subjectName;
    private java.util.Date date;
    private ArrayList<String> nameList = new ArrayList <> ();
    private ArrayList<String> attendedList = new ArrayList <> ();
    
    private static ArrayList<Class> classList = new ArrayList <> ();
    
    public Class()
    {}
    
    public Class(String classID,java.util.Date date,String subID,String subName)
    {
        this.classID = classID;
        this.date = date;
        subjectID = subID;
        subjectName = subName;
        classList.add(this);
    }
    
    //from database
    public static void initAddClass(String subID,String subName,String classID,int year,int month,int day)
    {
        month--;
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day,17,0);
        java.util.Date d = cal.getTime();
        
        Class c = new Class(classID,d,subID,subName);
        
        ArrayList<Trainer> trainerList2 = Profile.getTrainerList();
        for (int i=0; i<trainerList2.size(); i++)
        {
            if (trainerList2.get(i).getSubID().equals(subID))
                trainerList2.get(i).addClass(c);
        }
    }
    
    public static void createNewClass(String subID,String subName,Calendar cal)
    {   
        String classID = "";
        
        cal.set(Calendar.HOUR_OF_DAY,17);
        java.util.Date d = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = date.format(d);
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            Statement statement = connection.createStatement();
            
            //read classID
            ResultSet result = statement.executeQuery("select COUNT(ClassID) AS LastClassNum from CLASS");
            result.next();
            classID = Integer.toString(result.getInt("LastClassNum")+1);
            
            //reformat classID
            for (int i=1; i<=4; i++)
            {
                if (classID.length() == i)
                {
                    String zeros = "";
                    for (int j=0; j<4-i; j++)
                        zeros = zeros + "0";
                    classID = "CL" + zeros + classID;
                }
            }
            
            //write
            statement.executeUpdate("insert into CLASS values ('" + classID + "','" + subID + "','" + dateString + "',null,null)");
            
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
        
        Class c = new Class(classID,d,subID,subName);
        
        ArrayList<Trainer> trainerList2 = Profile.getTrainerList();
        for (int i=0; i<trainerList2.size(); i++)
        {
            if (trainerList2.get(i).getSubID().equals(subID))
                trainerList2.get(i).addClass(c);
        }
    }
    
    public static Class getAClass(String classID)
    {
        Class c = new Class();
        
        for (int i=0; i<classList.size(); i++)
        {
            if (classID.equals(classList.get(i).getClassID()))
                c = classList.get(i);
        }
        
        return c;
    }
    
    public static boolean checkRepeatedClass(String subID, Calendar cal)
    {
        java.util.Date d = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = date.format(d);
        
        for (int i=0; i<classList.size(); i++)
        {
            if (date.format(classList.get(i).getDate()).equals(dateString) && classList.get(i).getSubID().equals(subID))
                return true;
        }
        
        return false;
    }
    
    //for admin createNewClass() display
    public static ArrayList<Class> getUpcomingClass(String subID)
    {
        ArrayList<Class> classes = new ArrayList <> ();
        
        for (int i=0; i<classList.size(); i++)
        {
            if (classList.get(i).getDate().compareTo(new java.util.Date())>0 && classList.get(i).getSubID().equals(subID))
                classes.add(classList.get(i));
        }
        
        arrangeClass(classes);
        return classes;
    }
    
    //for admin assignTrainee() 
    public static ArrayList<Class> getNonFullClass(String subID)
    {
        ArrayList<Class> classes = new ArrayList <> ();
        
        for (int i=0; i<classList.size(); i++)
        {
            if (classList.get(i).getDate().compareTo(new java.util.Date())>0 && classList.get(i).getCapacity() != 50 && classList.get(i).getSubID().equals(subID))
                classes.add(classList.get(i));
        }
        
        return classes;
    }
    
    public static void arrangeClass(ArrayList<Class> classes)
    {
        for (int i=0; i<classes.size(); i++)
        {
            for (int j=0; j<classes.size()-1; j++)
            {
                java.util.Date date = classes.get(j).getDate();
                if (date.compareTo(classes.get(j+1).getDate()) > 0)
                {
                    Class c = classes.get(j);
                    classes.set(j,classes.get(j+1));
                    classes.set(j+1,c);
                }
            }
        }
    }
    
    public String getClassID()
    {return classID;}
    
    public String getSubID()
    {return subjectID;}
    
    public String getSubName()
    {return subjectName;}
    
    public java.util.Date getDate()
    {return date;}
    
    //database can,new can
    public void addTraineeToNameList(String traineeID,boolean inDatabase)
    {
        nameList.add(traineeID);
        Trainee t = Profile.getATrainee(traineeID);
        t.addClass(this);
        
        if (!inDatabase)//new trainee
        {
            String newNameList = "";
            for (int i=0; i<nameList.size(); i++)
                newNameList = newNameList + nameList.get(i) + " ";
        
            try
            {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
                
                //write
                Statement statement = connection.createStatement();
                statement.executeUpdate("update CLASS set NameList = '" + newNameList + "' where ClassID = '" + classID + "'");
                connection.close();
            }
            catch(SQLException ex)
            {ex.printStackTrace();}
        }
    }
    
    public void removeTraineeFromNameList(String traineeID)
    {
        nameList.remove(traineeID);
        
        String newNameList = "";
        for (int i=0; i<nameList.size(); i++)
            newNameList = newNameList + nameList.get(i) + " ";
        
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
            
            //write
            Statement statement = connection.createStatement();
            statement.executeUpdate("update CLASS set NameList = '" + newNameList + "' where ClassID = '" + classID + "'");
            connection.close();
        }
        catch(SQLException ex)
        {ex.printStackTrace();}
    }
    
    public void addTraineeToAttendedList(String traineeID,boolean inDatabase)
    {
        attendedList.add(traineeID);
        Trainee t = Profile.getATrainee(traineeID);
        Subject s = Subject.getASubject(getSubID());
        t.addAttendedSubject(s);
        
        String newAttendedList = "";
        for (int i=0; i<attendedList.size(); i++)
            newAttendedList = newAttendedList + attendedList.get(i) + " ";
        
        if (!inDatabase)
        {
            try
            {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigbraintms?serverTimezone=UTC","bigbrain","password");
                
                //write
                Statement statement = connection.createStatement();
                statement.executeUpdate("update CLASS set AttendedList = '" + newAttendedList + "' where ClassID = '" + classID + "'");
                connection.close();
            }
            catch(SQLException ex)
            {ex.printStackTrace();}
        }
    }
    
    public ArrayList<String> getNameList()
    {return nameList;}
    
    public int getCapacity()
    {return nameList.size();}
    
    public ArrayList<String> getAttendedList()
    {return attendedList;}
    
    public int getAttendedNum()
    {return attendedList.size();}
}