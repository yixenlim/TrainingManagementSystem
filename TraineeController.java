import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

public class TraineeController
{
    public static JPanel panelTraineeMenu,panelSubMenu,panelDownload,panelInfo,panelClassManagement,panelTT,panelAttendance,panelRequest,panelFeedback;
    private static Trainee trainee = new Trainee();
       
    public TraineeController()
    {
        panelTraineeMenu = new JPanel(new BorderLayout());
        panelSubMenu = new JPanel(new BorderLayout());
        panelTT = new JPanel(new BorderLayout());
        panelInfo = new JPanel(new BorderLayout());
        panelAttendance = new JPanel(new BorderLayout());
        panelFeedback = new JPanel(new BorderLayout());
        panelRequest = new JPanel(new BorderLayout());
        panelDownload = new JPanel(new BorderLayout());
        panelClassManagement = new JPanel(new BorderLayout());
        
        arrangeMenu();
    }
    
    public TraineeController(String traineeID)
    {trainee = Profile.getATrainee(traineeID);}
    
    public void arrangeMenu()
    {
        JPanel panelTraineeMenu2 = new JPanel(new GridBagLayout());
        panelTraineeMenu.add(panelTraineeMenu2);
        panelTraineeMenu2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonSMM = new ButtonFormat("Subject Materials Management");
        buttonSMM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeSubMenu();
                View.cardLayout.show(View.cardsPanel,"traineeSubMenu");
            }
        });
        
        ButtonFormat buttonCM = new ButtonFormat("Class Management");
        buttonCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeClassManagement();
                View.cardLayout.show(View.cardsPanel,"traineeClassManagement");
            }
        });
        
        ButtonFormat buttonFB = new ButtonFormat("Provide Feedback");
        buttonFB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainee.getAttendedSubjectNum() == 5)
                {
                    if (trainee.getTodoFeedbackList().size() != 0)
                    {
                        provideFeedback();
                        View.cardLayout.show(View.cardsPanel,"traineeFeedback");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Thank you! You have provided feedback for all subjects and trainer!");
                }
                else
                    JOptionPane.showMessageDialog(null,"You need to complete all the subjects in order to provide feedback.");
            }
        });
        
        ButtonFormat buttonVC = new ButtonFormat("View Certificate");
        buttonVC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {viewCert();}
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelTraineeMenu2.add(buttonSMM,c);
        
        c.gridy = 1;
        panelTraineeMenu2.add(buttonCM,c);
        
        c.gridy = 2;
        panelTraineeMenu2.add(buttonFB,c);
        
        c.gridy = 3 ;
        panelTraineeMenu2.add(buttonVC,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Log out");
        buttonBack.setPreferredSize(new Dimension(150,50));
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int result = JOptionPane.showConfirmDialog(null,"Are you sure you want log out?", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION)
                {
                    new LoginController();
                    View.cardLayout.show(View.cardsPanel,"loginMenu");
                }
            }
        });
        panelTraineeMenu.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeSubMenu()
    {
        panelSubMenu.removeAll();
        JPanel panelSubMenu2 = new JPanel(new GridBagLayout());
        panelSubMenu.add(panelSubMenu2);
        panelSubMenu2.setBackground(new Color(1,121,111));
        
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        String[] subjectList3 = new String[subjectList2.size()];
        for (int i=0; i<subjectList2.size(); i++)
            subjectList3[i] = subjectList2.get(i).getSubName();
        JList<String> list = new JList <> (subjectList3);
        list.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        
        ButtonFormat buttonDownload = new ButtonFormat("Download Materials");
        buttonDownload.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (checkMaterials(subjectList2.get(list.getSelectedIndex()).getSubID()))
                {
                    downloadMaterial(subjectList2.get(list.getSelectedIndex()).getSubID());
                    View.cardLayout.show(View.cardsPanel,"traineeDownload");
                }
            }
        });
        
        ButtonFormat buttonViewInfo = new ButtonFormat("View Information");
        buttonViewInfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                viewInfo(subjectList2.get(list.getSelectedIndex()).getInfo());
                View.cardLayout.show(View.cardsPanel,"subInfo");
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        panelSubMenu2.add(list,c);
        
        c.gridwidth = 1;
        c.ipadx = 40;
        c.ipady = 30;
        c.insets = new Insets(20,20,20,20);
        c.gridy = 1;
        panelSubMenu2.add(buttonDownload,c);
        
        c.gridx = 1;
        c.gridy = 1;
        panelSubMenu2.add(buttonViewInfo,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeMenu");}
        });
        panelSubMenu.add(panelBack,BorderLayout.SOUTH);
    }       
    
    public void arrangeClassManagement()
    {
        panelClassManagement.removeAll();
        JPanel panelClassManagement2 = new JPanel(new GridBagLayout());
        panelClassManagement.add(panelClassManagement2);
        panelClassManagement2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonTT = new ButtonFormat("View Timetable");
        buttonTT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainee.getClassNum() == 0)
                    JOptionPane.showMessageDialog(null,"You have no class yet.");
                else
                {
                    viewTimetable();
                    View.cardLayout.show(View.cardsPanel,"traineeTimetable");
                }
            }
        });
        
        ButtonFormat buttonTMP = new ButtonFormat("View Attendance");
        buttonTMP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainee.getClassNum() == 0)
                    JOptionPane.showMessageDialog(null,"You have no class yet.");
                else
                {
                    viewAttendance();
                    View.cardLayout.show(View.cardsPanel,"traineeAttendance");
                }
            }
        });
        
        ButtonFormat buttonRR = new ButtonFormat("Request Replacement Class");
        buttonRR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainee.getAttendedSubjectNum() == 5)
                    JOptionPane.showMessageDialog(null,"You have completed all the subjects.");
                else if (trainee.getClassNum() == 0)
                    JOptionPane.showMessageDialog(null,"You have no class yet.");
                else if (trainee.getRequestableClassNum() == 0)//admin havent register class or trainee requested all classes
                    JOptionPane.showMessageDialog(null,"You have no more class to be requested.");
                else
                {
                    requestReplacement();
                    View.cardLayout.show(View.cardsPanel,"traineeRequest");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelClassManagement2.add(buttonTT,c);
        
        c.gridy = 1;
        panelClassManagement2.add(buttonTMP,c);
        
        c.gridy = 2;
        panelClassManagement2.add(buttonRR,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeMenu");}
        });
        panelClassManagement.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void downloadMaterial(String subID)
    {
        panelDownload.removeAll();
        JPanel panelDownload2 = new JPanel(new GridBagLayout());
        panelDownload.add(panelDownload2);
        panelDownload2.setBackground(new Color(1,121,111));
        
        Subject s = Subject.getASubject(subID);
        JList<String> dList = new JList <> (s.getMaterials());
        dList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dList.setSelectedIndex(0);
        dList.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        JScrollPane sp = new JScrollPane(dList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension (600,300));
        
        ButtonFormat view = new ButtonFormat ("View");
        view.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e)
            {
                Object[] selectedFiles = dList.getSelectedValues();
                for (int i=0; i<selectedFiles.length; i++)
                {
                    try  
                    {  
                        String files = selectedFiles[i].toString();
                        File file = new File ("Subject Materials/" + subID + "/Materials/" + files);
                        if(!Desktop.isDesktopSupported())
                        {  
                            System.out.println("not supported");  
                            return;  
                        } 
                        
                        if(file.exists())
                            Desktop.getDesktop().open(file);
                    }  
                    catch(Exception ex)
                    {ex.printStackTrace();}
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(0,0,20,0);
        c.gridx = 0;
        c.gridy = 0;
        panelDownload2.add(sp,c);
        
        c.ipadx = 40;
        c.ipady = 30;
        c.gridy = 1;
        panelDownload2.add(view,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeSubMenu");}
        });
        panelDownload.add(panelBack,BorderLayout.SOUTH);
    }
    
    public boolean checkMaterials(String subID)
    {
        Subject s = Subject.getASubject(subID);
        if (s.getMaterials().length == 0)
        {
            JOptionPane.showMessageDialog(null,"No materials yet.");
            return false;
        }
        else
            return true;
    }
    
    public void viewInfo(ArrayList<String> subInfo)
    {
        panelInfo.removeAll();
        JPanel panelInfo2 = new JPanel(new GridBagLayout());
        panelInfo2.setBackground(new Color(1,121,111));
        panelInfo.add(panelInfo2,BorderLayout.CENTER);
        
        JPanel allContentPanel = new JPanel();
        allContentPanel.setBackground(Color.WHITE);
        allContentPanel.setLayout(new BoxLayout(allContentPanel, BoxLayout.Y_AXIS));
        
        ArrayList<JTextArea> info = new ArrayList <> ();
        for (int i=0; i<8; i++)
            info.add(new JTextArea(subInfo.get(i)));
        
        infoFormat(new JTextArea("Learning Outcome"),"title",allContentPanel);
        for (int i=0; i<7; i++)
        {
            infoFormat(new JTextArea("  "),"spacing",allContentPanel);
            infoFormat(info.get(i),"content",allContentPanel);
        }
        infoFormat(new JTextArea("  \n  "),"content",allContentPanel);
        infoFormat(new JTextArea("Subject Introduction"),"title",allContentPanel);
        infoFormat(new JTextArea("  "),"spacing",allContentPanel);
        infoFormat(info.get(7),"content",allContentPanel);
        
        JScrollPane scrollpane = new JScrollPane(allContentPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setPreferredSize(new Dimension(1000,400));
        scrollpane.setViewportView(allContentPanel);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelInfo2.add(scrollpane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeSubMenu");}
        });
        panelInfo.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewCert()
    {
        if (trainee.getAttendedSubjectNum() != 5)
            JOptionPane.showMessageDialog(null,"You need to complete all the subjects in order to view the certificate.");
        else
        {
            Certificate c = Certificate.getACertOfTrainee(trainee.getTraineeID());
            if (!c.getCertStatus())
                JOptionPane.showMessageDialog(null,"Please wait for the Administrator to upload your certificate.");
            else
            {
                try
                {Desktop.getDesktop().open(new File("Certificate/" + trainee.getTraineeID() + "/"));}
                catch(IOException ex)
                {ex.printStackTrace();}
            }
        }
    }
    
    public void provideFeedback()
    {
        panelFeedback.removeAll();
        JPanel panelFeedback2 = new JPanel(new GridBagLayout());
        panelFeedback.add(panelFeedback2);
        panelFeedback2.setBackground(new Color(1,121,111));
        
        ArrayList <String> criterias = trainee.getTodoFeedbackList();
        String[] s = new String[criterias.size()+1];
        s[0] = "Please select a subject or a trainer";
        for (int i=0; i<criterias.size(); i++)
            s[i+1] = criterias.get(i);

        JComboBox<String> menu = new JComboBox <> (s);
        menu.setPreferredSize(new Dimension(600,40));
        menu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        menu.setSelectedIndex(0); 
        
        GridBagConstraints c = new GridBagConstraints();
        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setPreferredSize(new Dimension(600,30));
        
        JRadioButton[] radioButtons = new JRadioButton[5];
        ButtonGroup bg = new ButtonGroup();
        for (int i=0; i<5; i++)
        {
            radioButtons[i] = new JRadioButton(Integer.toString(i+1));
            radioButtons[i].setFont(new Font(Font.DIALOG,Font.PLAIN,20));
            ratingPanel.add(radioButtons[i]);
            bg.add(radioButtons[i]);
            c.gridx++;
        }
        
        JTextArea comment = new JTextArea();
        comment.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(comment);
        sp.setPreferredSize(new Dimension(600,400));
        
        ButtonFormat submit = new ButtonFormat("Submit");
        submit.setPreferredSize(new Dimension(200,50));
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int rating = 0;
                for (int i=0; i<5; i++)
                {
                    if (radioButtons[i].isSelected())
                        rating = Integer.parseInt(radioButtons[i].getText());
                }
                
                if (rating == 0 || comment.getText().isEmpty() || menu.getSelectedItem().equals(s[0]))
                    JOptionPane.showMessageDialog(null,"Please select a criteria, choose a rating and write your feedback.");
                else if (comment.getText().length() > 5000)
                    JOptionPane.showMessageDialog(null,"Please shorten your feedback.");
                else
                {
                    trainee.removeTodoFeedbackList(menu.getSelectedItem().toString());
                    Feedback f= new Feedback(null,trainee.getTraineeID(),menu.getSelectedItem().toString(),rating,comment.getText());
                    
                    JOptionPane.showMessageDialog(null,"Feedback successfully sent!");
                    View.cardLayout.show(View.cardsPanel,"traineeMenu");
                }
            }
        });
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        panelFeedback2.add(menu,c);

        c.gridy = 1;
        panelFeedback2.add(ratingPanel,c);
         
        c.gridy = 2;
        panelFeedback2.add(sp,c);
        
        c.insets = new Insets(30,0,0,0);
        c.fill = GridBagConstraints.NONE;
        c.gridy = 3;
        panelFeedback2.add(submit,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeMenu");}
        });
        panelFeedback.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewTimetable()
    {
        panelTT.removeAll();
        JPanel panelTT2 = new JPanel(new GridBagLayout());
        panelTT.add(panelTT2);
        panelTT2.setBackground(new Color(1,121,111));
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;;
        c.ipadx = 10;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 0;
        
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        
        ArrayList<Request> requestList2 = Request.getRequestList();
        ArrayList<Class> requestedClass2 = trainee.getRequestedClass();
        ArrayList<Class> timetable2 = trainee.getTimetable();
        ArrayList<Subject> attendedSubject2 = trainee.getAttendedSubject();
        for (int i=0; i<timetable2.size();i++)
        {
            JTextPane textPane = new JTextPane();
            StyledDocument style = textPane.getStyledDocument();
            SimpleAttributeSet align= new SimpleAttributeSet();
            StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
            style.setParagraphAttributes(0, style.getLength(), align, false);
            textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            textPane.setEditable(false); 
            textPane.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
            
            boolean inside = false;
            
            for (int j=requestList2.size()-1; j>=0; j--)
            {
                if (requestList2.get(j).getTraineeID().equals(trainee.getTraineeID()) && requestList2.get(j).getClassID().equals(timetable2.get(i).getClassID()))
                {
                    if (requestList2.get(j).getRequestStatus().equals("Pending"))
                    {
                        inside = true;
                        textPane.setText("\n\n" + timetable2.get(i).getClassID() + "\n" + timetable2.get(i).getSubID() + "\n" + 
                                    timetable2.get(i).getSubName() + "\n\n" + date.format(timetable2.get(i).getDate()) + "\n 11:00AM - 05:00PM \nRequest pending\n");
                        break;
                    }
                    else if (requestList2.get(j).getRequestStatus().equals("Decline"))
                    {
                        inside = true;
                        textPane.setText("\n\n" + timetable2.get(i).getClassID() + "\n" + timetable2.get(i).getSubID() + "\n" + 
                                    timetable2.get(i).getSubName() + "\n\n" + date.format(timetable2.get(i).getDate()) + "\n 11:00AM - 05:00PM \nRequest declined\n");
                        break;
                    }
                }
            }
            
            if (inside == false)
            {
                textPane.setText("\n\n" + timetable2.get(i).getClassID() + "\n" + timetable2.get(i).getSubID() + "\n" + 
                                timetable2.get(i).getSubName() + "\n\n" + date.format(timetable2.get(i).getDate()) + "\n 11:00AM - 05:00PM \n\n\n");
            }
            
            c.gridx++;
            panelTT2.add(textPane,c);
        }
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.setPreferredSize(new Dimension(100,50));
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeClassManagement");}
        });
        panelTT.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void requestReplacement()
    {
        panelRequest.removeAll();
        JPanel panelRequest2 = new JPanel(new GridBagLayout());
        panelRequest.add(panelRequest2);
        panelRequest2.setBackground(new Color(1,121,111));
        
        ArrayList <Class> requestableClass2 = trainee.getRequestableClass();
        
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String[] s = new String[requestableClass2.size()+1];
        s[0] = "Please select a class";
        for (int i=0; i<requestableClass2.size(); i++)
            s[i+1] = requestableClass2.get(i).getClassID() + " (" + date.format(requestableClass2.get(i).getDate()) + ")";
        
        JComboBox<String> menu = new JComboBox <> (s);
        menu.setPreferredSize(new Dimension(600,40));
        menu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        menu.setSelectedIndex(0); 
        
        JTextArea reason = new JTextArea();
        reason.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        reason.setLineWrap(true);
        reason.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane (reason);
        sp.setPreferredSize(new Dimension(600,400));
        
        ButtonFormat submit = new ButtonFormat("Submit");
        submit.setPreferredSize(new Dimension(200,50));
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (reason.getText().isEmpty() && menu.getSelectedItem().equals(s[0]))
                    JOptionPane.showMessageDialog(null,"Please choose a class and write your reason.");
                else if (menu.getSelectedItem().equals(s[0]))
                    JOptionPane.showMessageDialog(null,"Please choose a class.");
                else if (reason.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Please write your reason.");
                else if (reason.getText().length() > 5000)
                    JOptionPane.showMessageDialog(null,"Please shorten your reason.");
                else
                {
                    Class c = new Class();
                    for (int i=0; i<requestableClass2.size(); i++)
                    {
                        if (menu.getSelectedIndex() == (i+1))
                            c = requestableClass2.get(i);
                    }
                    
                    Request r = new Request(null,trainee.getTraineeID(),c.getClassID(),reason.getText(),"Pending",null);
                    JOptionPane.showMessageDialog(null,"Request successfully sent!");
                    View.cardLayout.show(View.cardsPanel,"traineeClassManagement");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panelRequest2.add(menu,c);
        
        c.gridy = 1;
        panelRequest2.add(sp,c);
        
        c.fill = GridBagConstraints.NONE;
        c.gridy = 2;
        panelRequest2.add(submit,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeClassManagement");}
        });
        panelRequest.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewAttendance()
    {
        panelAttendance.removeAll();
        JPanel panelAttendance2 = new JPanel(new GridBagLayout());
        panelAttendance.add(panelAttendance2);
        panelAttendance2.setBackground(new Color(1,121,111));
        
        TrackAttendanceLabel subjectTitle = new TrackAttendanceLabel("Subject");
        TrackAttendanceLabel attendanceTitle = new TrackAttendanceLabel("Attendance");
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 50;
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 0;
        panelAttendance2.add(subjectTitle,c);
        
        c.gridx = 1;
        panelAttendance2.add(attendanceTitle,c);
        
        c.gridx = 0;
        c.gridy = 0;
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        
        for (int i=0; i<subjectList2.size(); i++)
        {
            c.gridy++;
            TrackAttendanceLabel sub = new TrackAttendanceLabel(subjectList2.get(i).getSubName());
            panelAttendance2.add(sub,c);
        }
        
        c.gridx = 1;
        c.gridy = 0;
        ArrayList<Subject> attendedSubject = trainee.getAttendedSubject();
        for (int i=0; i<subjectList2.size(); i++)
        {
            c.gridy++;
            TrackAttendanceLabel checkbox;
            if (attendedSubject.contains(subjectList2.get(i)))
                checkbox = new TrackAttendanceLabel("Attended");
            else
                checkbox = new TrackAttendanceLabel("Not attended");
            panelAttendance2.add(checkbox,c);
        }
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"traineeClassManagement");}
        });
        panelAttendance.add(panelBack,BorderLayout.SOUTH);
    }

    public void infoFormat(JTextArea text,String type,JPanel allContentPanel)
    {
        if (type.equals("title"))
            text.setFont(new Font(Font.DIALOG,Font.BOLD,30));
        else if (type.equals("content"))
            text.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        else if (type.equals("spacing"))
            text.setFont(new Font(Font.DIALOG,Font.PLAIN,10));
        
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        allContentPanel.add(text);
    }
}

class TimetableLabel extends JButton
{
    public TimetableLabel()
    {}
    
    public TimetableLabel(String s)
    {
        setRolloverEnabled(false);
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200,200));
        setModel(new FixedStateButtonModel());
        setText(s);
    }
}

class TrackAttendanceLabel extends JButton
{
    public TrackAttendanceLabel(String s)
    {
        setRolloverEnabled(false);
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(300,80));
        setModel(new FixedStateButtonModel());
        setText(s);
        
        if (s.equals("Subject") || s.equals("Attendance"))
        {
            setFont(new Font(Font.DIALOG,Font.BOLD,20));
            setBackground(new Color(216,216,216));
        }
        else if (s.equals("Complete"))
            setFont(new Font(Font.DIALOG,Font.BOLD,20));
        else
            setFont(new Font(Font.DIALOG,Font.PLAIN,20));
    }
}

class FixedStateButtonModel extends DefaultButtonModel    
{
    public boolean isPressed() 
    {return false;}

    public boolean isRollover()
    {return false;}

    public void setRollover(boolean b) 
    {}
}