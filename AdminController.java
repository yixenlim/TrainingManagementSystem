import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.filechooser.*; 
import javax.swing.table.*;
import net.sourceforge.jdatepicker.impl.*;

public class AdminController
{
    public static JPanel panelAdminMenu,panelCertManagement,panelViewCompletedList,panelUploadCert,panelFeedbackMenu,panelFeedback,panelProfileManagement,panelRegister,panelViewProfile,panelClassManagement,panelCreateClass,panelManageRequest,panelAssignSub,panelAssign;
    
    private static Administrator admin = new Administrator();
    
    public AdminController()
    {
        panelAdminMenu = new JPanel(new BorderLayout());
        panelCertManagement = new JPanel(new BorderLayout());
        panelViewCompletedList = new JPanel(new BorderLayout());
        panelUploadCert = new JPanel(new BorderLayout());
        panelFeedbackMenu = new JPanel(new BorderLayout());
        panelFeedback = new JPanel(new BorderLayout());
        panelProfileManagement = new JPanel(new BorderLayout());
        panelRegister = new JPanel(new BorderLayout());
        panelViewProfile = new JPanel(new BorderLayout());
        panelClassManagement = new JPanel(new BorderLayout());
        panelCreateClass = new JPanel(new BorderLayout());
        panelManageRequest = new JPanel(new BorderLayout());
        panelAssignSub = new JPanel(new BorderLayout());
        panelAssign = new JPanel(new BorderLayout());
        
        arrangeMenu();
    }
    
    public AdminController(String adminID)
    {admin = Profile.getAnAdmin(adminID);}
    
    public void arrangeMenu()
    {
        JPanel panelAdminMenu2 = new JPanel(new GridBagLayout());
        panelAdminMenu.add(panelAdminMenu2);
        panelAdminMenu2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonCM = new ButtonFormat("Certificate Management");
        buttonCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeCertManagement();
                View.cardLayout.show(View.cardsPanel,"certManagement");
            }
        });
        
        ButtonFormat buttonFB = new ButtonFormat("View Feedback");
        buttonFB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeFeedbackMenu();
                View.cardLayout.show(View.cardsPanel,"feedbackMenu");
            }
        });
        
        ButtonFormat buttonPM = new ButtonFormat("Profile Management");
        buttonPM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeProfileManagement();
                View.cardLayout.show(View.cardsPanel,"profileManagement");
            }
        });
        
        ButtonFormat buttonCLM = new ButtonFormat("Class Management");
        buttonCLM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeClassManagement();
                View.cardLayout.show(View.cardsPanel,"classManagement");
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelAdminMenu2.add(buttonCM,c);
        
        c.gridy = 1;
        panelAdminMenu2.add(buttonFB,c);
        
        c.gridy = 2;
        panelAdminMenu2.add(buttonPM,c);
        
        c.gridy = 3;
        panelAdminMenu2.add(buttonCLM,c);
        
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
        panelAdminMenu.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeCertManagement()
    {
        panelCertManagement.removeAll();
        JPanel panelCertManagement2 = new JPanel(new GridBagLayout());
        panelCertManagement.add(panelCertManagement2);
        panelCertManagement2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonVCTL = new ButtonFormat("View Completed Trainee List");//who doesnt have cert yet ONLY
        buttonVCTL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if((Profile.getCompletedTraineeList().size() == 0))
                    JOptionPane.showMessageDialog(null, "No trainee completed all subjects at this moment.");
                else
                {
                    viewCompletedTraineeList();
                    View.cardLayout.show(View.cardsPanel,"viewCompletedList");
                }
            }
        });
        
        ButtonFormat buttonUC = new ButtonFormat("Upload Certificate");
        buttonUC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (Profile.getNoCertTrainee().size() == 0)
                    JOptionPane.showMessageDialog(null,"No certificate needs to be uploaded.");
                else
                {
                    uploadCertificate();
                    View.cardLayout.show(View.cardsPanel,"uploadCert");
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
        panelCertManagement2.add(buttonVCTL,c);
        
        c.gridy = 1;
        panelCertManagement2.add(buttonUC,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"adminMenu");}
        });
        panelCertManagement.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeProfileManagement()
    {
        panelProfileManagement.removeAll();
        JPanel panelProfileManagement2 = new JPanel(new GridBagLayout());
        panelProfileManagement.add(panelProfileManagement2);
        panelProfileManagement2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonRNT = new ButtonFormat("Register New Trainee");
        buttonRNT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                registerTrainee();
                View.cardLayout.show(View.cardsPanel,"register");
            }
        });
        
        ButtonFormat buttonVAP = new ButtonFormat("View All Profile");
        buttonVAP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                viewAllProfile();
                View.cardLayout.show(View.cardsPanel,"viewAllProfile");
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelProfileManagement2.add(buttonRNT,c);
        
        c.gridy = 1;
        panelProfileManagement2.add(buttonVAP,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"adminMenu");}
        });
        panelProfileManagement.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeClassManagement()
    {
        panelClassManagement.removeAll();
        JPanel panelClassManagement2 = new JPanel(new GridBagLayout());
        panelClassManagement.add(panelClassManagement2);
        panelClassManagement2.setBackground(new Color(1,121,111));
        
        ButtonFormat buttonCNC = new ButtonFormat("Create New Class");
        buttonCNC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                createNewClass();
                View.cardLayout.show(View.cardsPanel,"createClass");
            }
        });
        
        ButtonFormat buttonMR = new ButtonFormat("Manage Request");
        buttonMR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (Request.getUnmanagedRequestList().size() == 0)
                    JOptionPane.showMessageDialog(null,"No request.");
                else
                {
                    manageRequest();
                    View.cardLayout.show(View.cardsPanel,"manageRequest");
                }
            }
        });
        
        ButtonFormat buttonAT = new ButtonFormat("Assign Trainee");
        buttonAT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeAssignSub();
                View.cardLayout.show(View.cardsPanel,"assignTraineeSub");
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelClassManagement2.add(buttonCNC,c);
        
        c.gridy = 1;
        panelClassManagement2.add(buttonMR,c);
        
        c.gridy = 2;
        panelClassManagement2.add(buttonAT,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"adminMenu");}
        });
        panelClassManagement.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeAssignSub()
    {
        panelAssignSub.removeAll();
        JPanel panelAssignSub2 = new JPanel(new GridBagLayout());
        panelAssignSub.add(panelAssignSub2);
        panelAssignSub2.setBackground(new Color(1,121,111));
        
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        String[] subjectList3 = new String[subjectList2.size()];
        for (int i=0; i<subjectList2.size(); i++)
            subjectList3[i] = subjectList2.get(i).getSubName();
        JList<String> list = new JList <> (subjectList3);
        list.setPreferredSize(new Dimension(300,140));
        list.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        
        ButtonFormat buttonSelect = new ButtonFormat("Select");
        buttonSelect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (checkAvailability(subjectList2.get(list.getSelectedIndex()).getSubID()))
                {
                    assignTrainee(subjectList2.get(list.getSelectedIndex()).getSubID());
                    View.cardLayout.show(View.cardsPanel,"assignTrainee");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();  
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        panelAssignSub2.add(list,c);
        
        c.ipadx = 50;
        c.ipady = 20;
        c.gridy = 1;
        panelAssignSub2.add(buttonSelect,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"classManagement");}
        });
        panelAssignSub.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeFeedbackMenu()
    {
        panelFeedbackMenu.removeAll();
        JPanel panelFeedbackMenu2 = new JPanel(new GridBagLayout());
        panelFeedbackMenu.add(panelFeedbackMenu2);
        panelFeedbackMenu2.setBackground(new Color(1,121,111));
        
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        ArrayList<Trainer> trainerList2 = Profile.getTrainerList();
        
        String[] s = new String[subjectList2.size() + trainerList2.size() + 1];
        s[0] = "Please select a subject or a trainer";
        for (int i=0; i<subjectList2.size(); i++)
            s[i+1] = subjectList2.get(i).getSubID();//s[1 to 5] is subjectID
        
        for (int i=0; i<trainerList2.size(); i++)
            s[i+6] = trainerList2.get(i).getTrainerID();//s[6 to 10] is trainerID
        
        JComboBox<String> menu = new JComboBox <> (s);
        menu.setPreferredSize(new Dimension(400,40));
        menu.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        menu.setSelectedIndex(0);
        
        ButtonFormat buttonSelect = new ButtonFormat("View");
        buttonSelect.setPreferredSize(new Dimension(200,50));
        buttonSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (menu.getSelectedItem().equals(s[0]))
                    JOptionPane.showMessageDialog(null,"Please select a Subject or Trainer to view the feedback.");
                else if (Feedback.getFeedbackList((String)menu.getSelectedItem()).size() == 0)//no feedback for this criteria
                    JOptionPane.showMessageDialog(null,"No feedback for this criteria.");
                else
                {
                    viewFeedback(menu.getSelectedItem().toString());
                    View.cardLayout.show(View.cardsPanel,"viewAllFeedback");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20,20,20,20);
        c.gridx = 0;
        c.gridy = 0;
        panelFeedbackMenu2.add(menu,c);

        c.gridy = 1;
        panelFeedbackMenu2.add(buttonSelect,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"adminMenu");}
        });
        panelFeedbackMenu.add(panelBack,BorderLayout.SOUTH);
    }
    
    //use in assignTrainee()
    public boolean checkAvailability(String subID)
    {
        if (Profile.getNonFullClassTrainee(subID).size() == 0)
        {
            JOptionPane.showMessageDialog(null,"All trainees have this subject in their timetable.");
            return false;
        }
        else if (Class.getNonFullClass(subID).size() == 0)
        {
            JOptionPane.showMessageDialog(null,"All classes of this subject are full. Please create a new class.");
            return false;
        }
        return true;
    }
    
    public void viewCompletedTraineeList()
    {
        panelViewCompletedList.removeAll();
        JPanel panelViewCompletedList2 = new JPanel(new GridBagLayout());
        panelViewCompletedList.add(panelViewCompletedList2);
        panelViewCompletedList2.setBackground(new Color(1,121,111));
        
        ArrayList<Trainee> traineeList2 = Profile.getCompletedTraineeList();
        ArrayList<Certificate> certList2 = Certificate.getCertOfTrainees(traineeList2);
        
        String [] column = {"Trainee ID", "Trainee Name","Certificate Status","Administrator ID"};
        Object[][] data = new Object[traineeList2.size()][4];

        for(int i=0; i<traineeList2.size(); i++)
        {
            data[i][0] = traineeList2.get(i).getTraineeID();
            data[i][1] = traineeList2.get(i).getTraineeName();
        }
        
        for(int i=0; i<certList2.size(); i++)
        {
            if (certList2.get(i).getCertStatus() == true)
                data[i][2] = "Uploaded";
            else
                data[i][2] = "Not uploaded";
            
            data[i][3] = certList2.get(i).getAdminID();
        }
        
        JTable table = new JTable(data,column);
        table.setEnabled(false);
        table.setPreferredScrollableViewportSize(new Dimension(1000,500));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.PLAIN,15));
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight()+8);

        table.getColumn(column[0]).setPreferredWidth(100);//traineeID
        table.getColumn(column[1]).setPreferredWidth(500);//traineeName
        table.getColumn(column[2]).setPreferredWidth(100);//certStatus
        table.getColumn(column[3]).setPreferredWidth(100);//AdminID
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        GridBagConstraints c = new GridBagConstraints();
        panelViewCompletedList2.add(scrollPane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"certManagement");}
        });
        panelViewCompletedList.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void uploadCertificate()
    {
        panelUploadCert.removeAll();
        JPanel panelUploadCert2 = new JPanel(new GridBagLayout());
        panelUploadCert.add(panelUploadCert2);
        panelUploadCert2.setBackground(new Color(1,121,111));
        
        ArrayList<Trainee> traineeList2 = Profile.getNoCertTrainee();
        ArrayList<Certificate> certList2 = Certificate.getCertOfTrainees(traineeList2); 

        JButton[] buttonUpload = new JButton[traineeList2.size()];
        int heightT = 600;
        if ((traineeList2.size()<= 10))
            heightT = 50 + 50*(traineeList2.size());

        Label headerID = new Label("Trainee ID");
        Label headerID2 = new Label("Certificate ID");
        Label headerCert = new Label("Certificate");

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panelUploadCert2.add(headerID,c);

        c.gridx = 1;
        c.gridy = 0;
        panelUploadCert2.add(headerID2,c);

        c.gridx = 2;
        c.gridy = 0;
        panelUploadCert2.add(headerCert,c);

        c.gridx = 0;
        c.gridy = 0;
        for (int i=0; i<traineeList2.size(); i++)
        {
            c.gridy++;
            Label traineeIDs = new Label(traineeList2.get(i).getTraineeID());
            panelUploadCert2.add(traineeIDs,c);
        }

        c.gridx = 1;
        c.gridy = 0;
        for (int i=0; i<certList2.size(); i++)
        {
            c.gridy++;
            Label certIDs = new Label(certList2.get(i).getCertID());
            panelUploadCert2.add(certIDs,c);
        }

        c.gridx = 2;
        c.gridy = 0;
        for (int i=0; i<traineeList2.size(); i++)
        {
            c.gridy++;
            buttonUpload[i] = new JButton("Upload");
            buttonUpload[i].setName(traineeList2.get(i).getTraineeID());
            buttonUpload[i].setPreferredSize(new Dimension(200,50));
            buttonUpload[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    JButton btn = (JButton)e.getSource();
                    String traineeID = btn.getName();//get the traineeID 
                    
                    JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    fc.setDialogTitle("Upload Certificate");
                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fc.setMultiSelectionEnabled(false);
                    fc.setApproveButtonText("Select");

                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    { 
                        try
                        {
                            File traineeCertFile = new File("Certificate/" + traineeID + "/");
                            traineeCertFile.mkdir();
                            
                            traineeCertFile = new File("Certificate/" + traineeID + "/" + fc.getSelectedFile().getName());
                            Files.copy(fc.getSelectedFile().toPath(), traineeCertFile.toPath()); 
                            JOptionPane.showMessageDialog(null, "Successfully uploaded certificate.");
                            
                            btn.setEnabled(false);
                            
                            Certificate cert = Certificate.getACertOfTrainee(traineeID);
                            cert.setCertStatus(true,admin.getAdminID()); 
                            if (Profile.getNoCertTrainee().size() == 0)
                            {
                                JOptionPane.showMessageDialog(null, "All cetificates uploaded.");
                                View.cardLayout.show(View.cardsPanel,"certManagement");
                            }
                        }
                        catch (IOException ex) 
                        {ex.printStackTrace();}
                    }
                }
            });
            panelUploadCert2.add(buttonUpload[i],c); 
        }
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"certManagement");}
        });
        panelUploadCert.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewFeedback(String criteria)
    {
        panelFeedback.removeAll();
        JPanel panelFeedback2 = new JPanel(new GridBagLayout());
        panelFeedback.add(panelFeedback2);
        panelFeedback2.setBackground(new Color(1,121,111));
        
        ArrayList<Feedback> feedbackList2 = Feedback.getFeedbackList(criteria);
        String[] column = {"FeedbackID","TraineeID","Rating","Comment"};
        Object [][] data = new Object[feedbackList2.size()][4];

        for(int i = 0; i<feedbackList2.size(); i++)
        {
            data[i][0] = feedbackList2.get(i).getFeedbackID();
            data[i][1] = feedbackList2.get(i).getTraineeID();
            data[i][2] = feedbackList2.get(i).getRating();
            data[i][3] = feedbackList2.get(i).getComment();
        }
        
        JTable table = new JTable(data,column);
        table.setEnabled(false);
        table.setPreferredScrollableViewportSize(new Dimension(1000,500));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.PLAIN,15));
        table.setFillsViewportHeight(true);
        
        table.getColumn(column[0]).setPreferredWidth(100);//feedbackID
        table.getColumn(column[1]).setPreferredWidth(100);//traineeID
        table.getColumn(column[2]).setPreferredWidth(50);//rating
        table.getColumn(column[3]).setPreferredWidth(600);//comment
        table.getColumn(column[3]).setCellRenderer(new MultiLineCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        
        GridBagConstraints c = new GridBagConstraints();
        panelFeedback2.add(scrollPane,c);
            
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"feedbackMenu");}
        });
        panelFeedback.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void registerTrainee()
    {
        panelRegister.removeAll();
        JPanel panelRegister2 = new JPanel(new GridBagLayout());
        panelRegister.add(panelRegister2);
        panelRegister2.setBackground(new Color(1,121,111));
        
        JLabel label = new JLabel("Trainee Registration",SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN,40));
        
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Arial", Font.PLAIN,20));

        JTextField nameText = new JTextField();
        nameText.setPreferredSize(new Dimension(500,25));
        nameText.setFont(new Font("Arial", Font.PLAIN,15));
        
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setForeground(Color.white);
        genderLabel.setFont(new Font("Arial", Font.PLAIN,20));

        String[] gender = {"Please select a gender","Male","Female"};
        JComboBox<String> genderMenu = new JComboBox <> (gender);
        genderMenu.setPreferredSize(new Dimension(500,25));
        genderMenu.setFont(new Font("Arial", Font.PLAIN,15));
        genderMenu.setSelectedIndex(0);
        
        JLabel contactLabel = new JLabel("Contact: ");
        contactLabel.setForeground(Color.white);
        contactLabel.setFont(new Font("Arial", Font.PLAIN,20));
        
        JTextField  contactText = new JTextField ();
        contactText.setPreferredSize(new Dimension(500,25));
        contactText.setFont(new Font("Arial", Font.PLAIN,15));
        
        ButtonFormat registerButton = new ButtonFormat("Register");
        registerButton.setPreferredSize(new Dimension(150,50));
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {   
                if (nameText.getText().isEmpty() || genderMenu.getSelectedIndex() == 0 || contactText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, "Please fill in all the information");
                else 
                {
                    try
                    {
                        int contactNum = Integer.parseInt(contactText.getText());
                        
                        if (contactText.getText().charAt(0) != '0' || contactText.getText().charAt(1) != '1' || contactText.getText().charAt(2) == '4' || contactText.getText().charAt(2) == '5' || contactText.getText().charAt(2) == '9' || (contactText.getText().charAt(2) == '1' && contactText.getText().length() != 11) || (contactText.getText().charAt(2) != '1' && contactText.getText().length() != 10))
                            JOptionPane.showMessageDialog (null, "Please enter a valid contact number.");
                        else if (Profile.duplicateContact(contactText.getText()))
                            JOptionPane.showMessageDialog (null, "This contact number existed in our database.");
                        else
                        {
                            ArrayList<String> loginCredentials = Profile.addTrainee(nameText.getText(),genderMenu.getSelectedItem().toString(),contactText.getText());
                            String username = loginCredentials.get(0);
                            String password = loginCredentials.get(1);
                            JOptionPane.showMessageDialog(null, "Please record the username and password \n Username: " + username + "\n Password: " + password );
                            nameText.setText("");
                            genderMenu.setSelectedIndex(0);
                            contactText.setText("");
                        }
                    }
                    catch (NumberFormatException ex)
                    {JOptionPane.showMessageDialog (null, "Please enter a valid contact number.");}
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 80;//make this component tall
        c.gridwidth = 2;
        panelRegister2.add(label,c);
        
        c = new GridBagConstraints();
        c.insets = new Insets(20,5,5,5);       
        c.gridx = 0;
        c.gridy = 1;       
        panelRegister2.add(nameLabel,c);
        
        c.gridx = 1;
        c.gridy = 1;
        panelRegister2.add(nameText,c);
        
        c.gridx = 0;
        c.gridy = 2;
        panelRegister2.add(genderLabel,c);
        
        c.gridx = 1;
        c.gridy = 2;
        panelRegister2.add(genderMenu,c);
        
        c.gridx = 0;
        c.gridy = 3;
        panelRegister2.add(contactLabel,c);
        
        c.gridx = 1;
        c.gridy = 3;
        panelRegister2.add(contactText,c);
        
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        panelRegister2.add(registerButton,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"profileManagement");}
        });
        panelRegister.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewAllProfile()
    {
        panelViewProfile.removeAll();
        JPanel panelViewProfile2 = new JPanel(new GridBagLayout());
        panelViewProfile.add(panelViewProfile2);
        panelViewProfile2.setBackground(new Color(1,121,111));
        
        ArrayList<Administrator> adminList = Profile.getAdminList();
        ArrayList<Trainer> trainerList = Profile.getTrainerList();
        ArrayList<Trainee> traineeList = Profile.getTraineeList();
        
        String[] columnNames = {"ID", "Name","Gender", "Contact", "Password"};
        Object [][] data = new Object[adminList.size() + trainerList.size() + traineeList.size()][5];
        int rowCount = 0;
        
        for (int i=0; i<adminList.size(); i++)
        {
            data[rowCount][0] = adminList.get(i).getAdminID();
            data[rowCount][1] = adminList.get(i).getAdminName();
            data[rowCount][2] = adminList.get(i).getAdminGender();
            data[rowCount][3] = adminList.get(i).getAdminContact();
            data[rowCount][4] = adminList.get(i).getAdminPassword();
            rowCount++;
        }
        
        for (int i=0; i<trainerList.size(); i++)
        {
            data[rowCount][0] = trainerList.get(i).getTrainerID();
            data[rowCount][1] = trainerList.get(i).getTrainerName();
            data[rowCount][2] = trainerList.get(i).getTrainerGender();
            data[rowCount][3] = trainerList.get(i).getTrainerContact();
            data[rowCount][4] = trainerList.get(i).getTrainerPassword();
            rowCount++;
        }
        
        for (int i=0; i<traineeList.size(); i++)
        {
            data[rowCount][0] = traineeList.get(i).getTraineeID();
            data[rowCount][1] = traineeList.get(i).getTraineeName();
            data[rowCount][2] = traineeList.get(i).getTraineeGender();
            data[rowCount][3] = traineeList.get(i).getTraineeContact();
            data[rowCount][4] = traineeList.get(i).getTraineePassword();
            rowCount++;
        }

        JTable table = new JTable(data,columnNames);
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.PLAIN,15));
        table.setPreferredScrollableViewportSize(new Dimension(1000,500));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getColumn(columnNames[0]).setPreferredWidth(200);//ID
        table.getColumn(columnNames[1]).setPreferredWidth(1250);//Name
        table.getColumn(columnNames[2]).setPreferredWidth(150);//Gender
        table.getColumn(columnNames[3]).setPreferredWidth(250);//Contact
        table.getColumn(columnNames[4]).setPreferredWidth(200);//Password
        table.setRowHeight(table.getRowHeight()+8);
        
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints c = new GridBagConstraints();
        panelViewProfile2.add(scrollPane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"profileManagement");}
        });
        panelViewProfile.add(panelBack,BorderLayout.SOUTH);
    }
    
    public String [] getAllClass (String sub)
    {
        ArrayList <Class> classList2 = Class.getUpcomingClass(sub);
        String [] allClass = new String [classList2.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (int i=0; i<allClass.length; i++)
            allClass[i] = classList2.get(i).getClassID() + " on " + sdf.format(classList2.get(i).getDate());
        
        return allClass;
    }
    
    public void createNewClass()
    {
        panelCreateClass.removeAll();
        JPanel panelCreateClass2 = new JPanel(new GridBagLayout());
        panelCreateClass.add(panelCreateClass2);
        panelCreateClass2.setBackground(new Color(1,121,111));
        
        JList<String> cList = new JList <> ();
        cList.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        cList.setEnabled(false);
        JScrollPane sp = new JScrollPane(cList);
        sp.setPreferredSize(new Dimension(300,300));
        
        ArrayList<Subject> subjectList2 = Subject.getSubjectList();
        String[] allSubjects = new String[subjectList2.size() + 1];
        allSubjects[0] = "Choose a subject";
        for (int i=0; i<subjectList2.size(); i++)
            allSubjects[i+1] = subjectList2.get(i).getSubID();//s[1 to 5] is subjectID
            
        JComboBox cb = new JComboBox(allSubjects);
        cb.setSelectedIndex(0);
        cb.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        cb.addItemListener(new ItemListener () {
            public void itemStateChanged (ItemEvent e)
            {
                if (cb.getSelectedIndex() != 0)
                    cList.setListData(getAllClass(cb.getSelectedItem().toString()));
                else
                {
                    cList.setListData(new String[0]);
                    sp.repaint();
                    sp.revalidate();
                }
            }
        });
        
        UtilCalendarModel model = new UtilCalendarModel();
        JDatePanelImpl calPanel = new JDatePanelImpl(model);
        JDatePickerImpl calPicker = new JDatePickerImpl(calPanel);
        
        ButtonFormat addClassButton = new ButtonFormat("Add Class");
        addClassButton.setPreferredSize(new Dimension(200,50));
        addClassButton.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e)
            {
                Calendar selectedDate = (Calendar) calPicker.getModel().getValue();
                Calendar now = Calendar.getInstance();
                
                if (cb.getSelectedIndex() == 0 && (selectedDate == null || selectedDate.compareTo(now)<=0))
                    JOptionPane.showMessageDialog(null, "Please select a subject and a valid date.");
                else if (cb.getSelectedIndex() == 0)
                    JOptionPane.showMessageDialog(null, "Please select a subject.");
                else if (selectedDate == null || selectedDate.compareTo(now)<=0)
                    JOptionPane.showMessageDialog(null, "Please select a valid date.");
                else if (Class.checkRepeatedClass(cb.getSelectedItem().toString(),selectedDate))
                    JOptionPane.showMessageDialog(null, "There is already a class of this subject on this day. Please reselect the subject or date.");
                else
                {
                    Class.createNewClass(cb.getSelectedItem().toString(), Subject.getASubject(cb.getSelectedItem().toString()).getSubName(), selectedDate);
                    cList.setListData(getAllClass(cb.getSelectedItem().toString()));
                    JOptionPane.showMessageDialog(null, "Class added.");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(20,20,20,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        panelCreateClass2.add(cb,c);
        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        panelCreateClass2.add(sp,c);
        
        c.gridx = 1;
        c.gridy = 1;
        panelCreateClass2.add(calPicker,c);
        
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        panelCreateClass2.add(addClassButton,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"classManagement");}
        });
        panelCreateClass.add(panelBack,BorderLayout.SOUTH);
    }
    
    public ArrayList <Request> getCheckedList (JTable table)
    {
        ArrayList <Request> list = new ArrayList<>();
        
        for (int i=table.getRowCount()-1; i>=0; i--)
        {
            Boolean checked = Boolean.valueOf(table.getValueAt(i, 0).toString());
            if (checked)
            {
                list.add(Request.getARequest(table.getValueAt(i, 1).toString()));
                ((DefaultTableModel)table.getModel()).removeRow(i);
            }
        }
        
        if (list.size() == 0)
            JOptionPane.showMessageDialog(null, "Please select request(s).");
        
        return list;
    }
    
    public void manageRequest()
    {
        panelManageRequest.removeAll();
        JPanel panelManageRequest2 = new JPanel(new GridBagLayout());
        panelManageRequest.add(panelManageRequest2);
        panelManageRequest2.setBackground(new Color(1,121,111));
        
        String [] column = {"Select", "Request ID", "Trainee ID", "Class ID", "Reason"};
        ArrayList<Request> list = Request.getUnmanagedRequestList();
        Object [][] data = new Object[list.size()][column.length-1];
        
        for (int i=0; i<data.length; i++)
        {
            data[i][0] = list.get(i).getRequestID();
            data[i][1] = list.get(i).getTraineeID();
            data[i][2] = list.get(i).getClassID();
            data[i][3] = list.get(i).getReason();
        }
        
        JTable table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(1000,450));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("ARIAL", Font.BOLD, 15));
        table.setFont(new Font("ARIAL", Font.PLAIN, 15));
        table.setCellSelectionEnabled(false);
        table.setFocusable(false);
        table.setFillsViewportHeight(true);

        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                if (column == 0)
                    return true;
                return false;
            }
            
            public java.lang.Class<?> getColumnClass(int column)
            {
                if (column == 0)
                    return Boolean.class;
                else
                    return String.class;
            }
        };
        table.setModel(model);
        
        for (int i=0; i<column.length; i++)
            model.addColumn(column[i]);
        
        for (int i=0; i<data.length; i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            for (int j=0; j<column.length-1; j++)//4
                model.setValueAt(data[i][j], i, j+1);
        }
        
        table.getColumn(column[0]).setPreferredWidth(50);//checkbox//50
        table.getColumn(column[1]).setPreferredWidth(100);//requestID//100
        table.getColumn(column[2]).setPreferredWidth(100);//TraineeID
        table.getColumn(column[3]).setPreferredWidth(100);//ClassID
        table.getColumn(column[4]).setPreferredWidth(500);//Reason//500
        table.getColumn(column[4]).setCellRenderer(new MultiLineCellRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setViewportView(table);
        
        ButtonFormat accept = new ButtonFormat ("Accept");
        accept.setPreferredSize(new Dimension(250,50));
        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList <Request> checkedList = getCheckedList(table);
                
                if (checkedList.size() != 0)
                {
                    for (int i=0; i<checkedList.size(); i++)
                        checkedList.get(i).setRequestStatus("Accept",admin.getAdminID());
                    
                    //refresh
                    sp.revalidate();
                    sp.repaint();
                    JOptionPane.showMessageDialog(null, "Request(s) accepted.");
                    
                    if (Request.getUnmanagedRequestList().size() == 0)
                    {
                        JOptionPane.showMessageDialog(null, "No more request.\nYou will be redirected back to Class Management.");
                        View.cardLayout.show(View.cardsPanel,"classManagement");
                    } 
                }
            }
        });
        
        ButtonFormat decline = new ButtonFormat ("Decline");
        decline.setPreferredSize(new Dimension(250,50));
        decline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList <Request> checkedList = getCheckedList(table);
                
                if (checkedList.size() != 0)
                {
                    for (int i=0; i<checkedList.size(); i++)
                        checkedList.get(i).setRequestStatus("Decline",admin.getAdminID());
    
                    //refresh
                    sp.revalidate();
                    sp.repaint();
                    JOptionPane.showMessageDialog(null, "Request(s) declined.");
                    
                    if (Request.getUnmanagedRequestList().size() == 0)
                    {
                        JOptionPane.showMessageDialog(null, "No more request.\nYou will be redirected back to Class Management.");
                        View.cardLayout.show(View.cardsPanel,"classManagement");
                    }
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        panelManageRequest2.add(sp,c);
        
        c.insets = new Insets(20,100,0,40);
        c.gridwidth = 1;
        c.gridy = 1;
        panelManageRequest2.add(accept,c);
        
        c.gridx = 1;
        panelManageRequest2.add(decline,c);
    
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"classManagement");}
        });
        panelManageRequest.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void assignTrainee(String subID)
    {
        panelAssign.removeAll();
        JPanel panelAssign2 = new JPanel(new GridBagLayout());
        panelAssign2.setBackground(new Color(1,121,111));
        panelAssign.add(panelAssign2,BorderLayout.CENTER);
        
        ArrayList<Trainee> trainees = Profile.getNonFullClassTrainee(subID);//get trainee that has no class of this sub
        ArrayList<Class> classes = Class.getNonFullClass(subID);//get class of this sub that has no enough trainee
        JButton[][] buttons = new JButton[trainees.size()][classes.size()];
        
        JPanel tablePanel = new JPanel(new GridBagLayout());
        Label nameLabel = new Label("Trainee/Class ID");
                
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridx = 0;
        c.gridy = 0;
        tablePanel.add(nameLabel,c);
        
        int widthT = 1000;
        int heightT = 600;
        if (trainees.size() <= 10)
            heightT = 50 + (50*trainees.size());
        if (classes.size() <= 3)
            widthT = 200 + (200*classes.size());
        tablePanel.setPreferredSize(new Dimension(widthT,heightT));
        tablePanel.setBackground(Color.WHITE);
        
        //set available class header
        c.gridy = 0;
        for (int i=0; i<classes.size(); i++)
        {
            c.gridx++;
            Label classLabel = new Label(classes.get(i).getClassID());
            tablePanel.add(classLabel,c);
        }
        
        //set traineeID row
        c.gridx = 0;
        for (int i=0; i<trainees.size(); i++)
        {
            c.gridy++;
            Label classLabel = new Label(trainees.get(i).getTraineeID());
            tablePanel.add(classLabel,c);
        }
        
        //set enroll buttons
        c.gridy = 0;
        for (int i=0; i<trainees.size(); i++)
        {
            c.gridy++;
            c.gridx = 0;
            for (int j=0; j<classes.size(); j++)
            {
                c.gridx++;
                buttons[i][j] = new JButton("Enroll");
                buttons[i][j].setPreferredSize(new Dimension(200,50));
                buttons[i][j].setName(Integer.toString(i) + Integer.toString(j));
                tablePanel.add(buttons[i][j],c);
                
                if (trainees.get(i).getRequestedClass().contains(classes.get(j)))
                    buttons[i][j].setEnabled(false);
                else
                {
                    buttons[i][j].addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            JButton btn = (JButton)e.getSource();
                            btn.setEnabled(false);
                            
                            int a = Character.getNumericValue(btn.getName().charAt(0));
                            int b = Character.getNumericValue(btn.getName().charAt(1));
                            
                            classes.get(b).addTraineeToNameList(trainees.get(a).getTraineeID(),false);
                            for (int c=0; c<classes.size(); c++)//disable same row enroll button
                                buttons[a][c].setEnabled(false);
                                
                            if (classes.get(b).getCapacity() == 50)//disable same column enroll button
                            {
                                for (int c=0; c<trainees.size(); c++)
                                    buttons[c][b].setEnabled(false);
                            }
                            
                            boolean noThisSubject = false;
                            for (int c=0; c<trainees.size(); c++)
                            {
                                if (!trainees.get(c).haveClass(subID))
                                    noThisSubject = true;
                            }
                            
                            boolean availability = false;
                            for (int c=0; c<classes.size(); c++)
                            {
                                if (classes.get(c).getCapacity() != 50)
                                    availability = true;
                            }
                            
                            if (!noThisSubject || !availability)
                            {
                                if (!noThisSubject)
                                    JOptionPane.showMessageDialog(null,"All trainees have been assigned in this subject!");
                                else if (!availability)
                                    JOptionPane.showMessageDialog(null,"No more empty class!");
                            
                                View.cardLayout.show(View.cardsPanel,"assignTraineeSub");
                            }
                        }
                    });
                }
            }
            c.gridx++;
        }
        
        JScrollPane scrollpane = new JScrollPane(tablePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        int widthS = 1000;
        int heightS = 600;
        if (widthT < widthS)
            widthS = widthT;
        if (heightT < heightS)
            heightS = heightT;
        scrollpane.setPreferredSize(new Dimension(widthS+20,heightS+20));
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelAssign2.add(scrollpane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"assignTraineeSub");}
        });
        panelAssign.add(panelBack,BorderLayout.SOUTH);
    }
}

class Label extends JButton
{
    public Label(String s)
    {
        setRolloverEnabled(false);
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200,50));
        setModel(new FixedStateButtonModel());
        setText(s);
        setFont(new Font(Font.DIALOG,Font.PLAIN,20));
    }
}

class MultiLineCellRenderer extends JTextArea implements TableCellRenderer
{
    public MultiLineCellRenderer()
    {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
        setFont(table.getFont());
        setText((value == null) ? "" : value.toString());
        
        setSize(table.getColumnModel().getColumn(column).getWidth(),getPreferredSize().height);
        table.setRowHeight(row, (int)getPreferredSize().getHeight()+8);
        
        return this;
    }
}