import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.sql.*;

public class Trainee
{
    private String traineeID,traineeName,gender,contact,password;
    
    private ArrayList<Class> timetable = new ArrayList <> ();
    private ArrayList<Subject> attendedSubject = new ArrayList <> ();
    private ArrayList<Class> requestedClass = new ArrayList <> ();
    private ArrayList<Class> requestableClass = new ArrayList <> ();
    private ArrayList<String> todoFeedbackList = new ArrayList <> ();
    
    public Trainee()
    {}
    
    public Trainee(String traineeID,String traineeName,String gender,String contact,String password)
    {
        this.traineeID = traineeID;
        this.traineeName = traineeName;
        this.gender = gender;
        this.contact = contact;
        this.password = password;
            
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        ArrayList<Trainer> trainerList2 = Profile.getTrainerList();
        for (int i=0; i<subjectList2.size(); i++)
            todoFeedbackList.add(subjectList2.get(i).getSubID());
        for (int i=0; i<trainerList2.size(); i++)
            todoFeedbackList.add(trainerList2.get(i).getTrainerID());
    }
    
    public String getTraineeID()
    {return traineeID;}
    
    public String getTraineeName()
    {return traineeName;}
    
    public String getTraineeGender()
    {return gender;}
    
    public String getTraineeContact()
    {return contact;}
    
    public String getTraineePassword()
    {return password;}
    
    public boolean haveClass(String subID)
    {
        boolean contain = false;
        for (int i=0; i<timetable.size(); i++)
        {
            if (subID.equals(timetable.get(i).getSubID()))
                contain = true;
        }
        return contain;
    }
    
    public void removeTodoFeedbackList(String criteria)
    {todoFeedbackList.remove(criteria);}
    
    public ArrayList<String> getTodoFeedbackList()
    {return todoFeedbackList;}
    
    public void addClass(Class c)
    {
        timetable.add(c);
        arrangeTimetable(timetable);
        addRequestableClass(c);
    }
    
    public void dropClass(Class c)
    {
        timetable.remove(c);
        c.removeTraineeFromNameList(getTraineeID());
        requestableClass.remove(c);
    }
    
    public ArrayList<Class> getTimetable()
    {return timetable;}
    
    public void arrangeTimetable(ArrayList<Class> classes)
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
    
    public int getClassNum()
    {return timetable.size();}
    
    public void addAttendedSubject(Subject s)
    {
        attendedSubject.add(s);
        for (int i=0; i<timetable.size(); i++)
        {
            if (timetable.get(i).getSubID().equals(s.getSubID()))
                requestableClass.remove(timetable.get(i));
        }
    }
    
    public ArrayList<Subject> getAttendedSubject()
    {return attendedSubject;}
    
    public int getAttendedSubjectNum()
    {return attendedSubject.size();}
    
    public void addRequestableClass(Class c)
    {
        if (!requestableClass.contains(c))
            requestableClass.add(c);
        arrangeTimetable(requestableClass);
    }
    
    public ArrayList<Class> getRequestableClass()
    {return requestableClass;}
    
    public int getRequestableClassNum()
    {return requestableClass.size();}
    
    public void addRequestedClass(Class c)
    {
        if (!requestedClass.contains(c))
            requestedClass.add(c);
        requestableClass.remove(c);
    }
    
    public ArrayList<Class> getRequestedClass()
    {return requestedClass;}
}