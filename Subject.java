import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

public class Subject
{
    private String subjectID,subjectName;
    private ArrayList<String> info = new ArrayList <> ();
    
    private static ArrayList<Subject> subjectList = new ArrayList <> ();
    
    public Subject()
    {}
    
    public Subject(String subjectID,String subjectName)
    {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        
        subjectList.add(this);
    }
    
    public static ArrayList<Subject> getSubjectList()
    {return subjectList;}
    
    public static Subject getASubject(String subID)
    {
        Subject s = new Subject();
        
        for (int i=0; i<subjectList.size(); i++)
        {
            if (subID.equals(subjectList.get(i).getSubID()))
                s = subjectList.get(i);
        }
        
        return s;
    }
    
    public String getSubID()
    {return subjectID;}
    
    public String getSubName()
    {return subjectName;}
    
    public void addInfo(String s)
    {info.add(s);}
    
    public ArrayList<String> getInfo()
    {return info;}
    
    public String [] getMaterials()
    {
    	File file = new File ("Subject Materials/" + subjectID + "/Materials");
    	String [] m = file.list();
    	return m;	
    }
}