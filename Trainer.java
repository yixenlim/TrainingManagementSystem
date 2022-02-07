import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

public class Trainer
{
    private String trainerID,trainerName,subjectID,gender,contact,password;
    private ArrayList<Class> timetable = new ArrayList <> ();
    
    public Trainer()
    {}
    
    public Trainer(String trainerID,String trainerName,String subID,String gender,String contact,String password)
    {
        this.trainerID = trainerID;
        this.trainerName = trainerName;
        subjectID = subID;
        this.gender = gender;
        this.contact = contact;
        this.password = password;
    }
    
    public String getSubID()
    {return subjectID;}
    
    public String getTrainerID()
    {return trainerID;}
    
    public String getTrainerName()
    {return trainerName;}
    
    public String getTrainerGender()
    {return gender;}
    
    public String getTrainerContact()
    {return contact;}
    
    public String getTrainerPassword()
    {return password;}
    
    public void addClass(Class c)
    {
        timetable.add(c);
        arrangeTimetable();
    }
    
    public ArrayList<Class> getTimetable()//return upcoming class only
    {
        ArrayList<Class> classes = new ArrayList <> ();
        
        for (int i=0; i<timetable.size(); i++)
        {
            if (timetable.get(i).getDate().compareTo(new Date())>0)
                classes.add(timetable.get(i));
        }
        
        return classes;
    }
    
    public void arrangeTimetable()
    {
        for (int i=0; i<timetable.size(); i++)
        {
            for (int j=0; j<timetable.size()-1; j++)
            {
                Date date = timetable.get(j).getDate();
                if (date.compareTo(timetable.get(j+1).getDate()) > 0)
                {
                    Class c = timetable.get(j);
                    timetable.set(j,timetable.get(j+1));
                    timetable.set(j+1,c);
                }
            }
        }
    }
}