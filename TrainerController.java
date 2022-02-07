import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.filechooser.*; 
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.io.*;
import java.nio.file.*;

public class TrainerController
{
    public static JPanel panelTrainerMenu,panelClassManagement,panelUpload,panelUpdateMenu,panelUpdate,panelTT,panelFeedback,panelFeedbackCriteria;
    private static Trainer trainer = new Trainer();
    
    public TrainerController()
    {
        panelTrainerMenu = new JPanel(new BorderLayout());
        panelClassManagement = new JPanel(new BorderLayout());
        panelUpload = new JPanel(new BorderLayout());
        panelUpdateMenu = new JPanel(new BorderLayout());
        panelUpdate = new JPanel(new BorderLayout());
        panelTT = new JPanel(new BorderLayout());
        panelFeedback = new JPanel(new BorderLayout());
        panelFeedbackCriteria = new JPanel(new BorderLayout());
        
        arrangeMenu();
    }
    
    public TrainerController(String trainerID)
    {trainer = Profile.getATrainer(trainerID);}
    
    public void arrangeMenu()
    {
        JPanel panelTrainerMenu2 = new JPanel(new GridBagLayout());
        panelTrainerMenu2.setBackground(new Color(1,121,111));
        panelTrainerMenu.add(panelTrainerMenu2);
        
        ButtonFormat buttonUSM = new ButtonFormat("Upload Subject Materials");
        buttonUSM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                uploadMaterial();
                View.cardLayout.show(View.cardsPanel,"trainerUpload");
            }
        });
        
        ButtonFormat buttonCM = new ButtonFormat("Class Management");
        buttonCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeClassManagement();
                View.cardLayout.show(View.cardsPanel,"trainerClassManagement");
            }
        });
        
        ButtonFormat buttonVF = new ButtonFormat("View Feedback");
        buttonVF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                arrangeFeedbackMenu();
                View.cardLayout.show(View.cardsPanel,"trainerViewFeedbackCriteria");
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.insets = new Insets(12,12,12,12);
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 40;
        c.ipady = 30;
        c.gridx = 0;
        c.gridy = 0;
        panelTrainerMenu2.add(buttonUSM,c);
        
        c.gridy = 1;
        panelTrainerMenu2.add(buttonCM,c);
        
        c.gridy = 2;
        panelTrainerMenu2.add(buttonVF,c);
        
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
        panelTrainerMenu.add(panelBack,BorderLayout.SOUTH);
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
                if (trainer.getTimetable().size() == 0)
                    JOptionPane.showMessageDialog(null,"No upcoming class.");
                else
                {
                    viewTimetable();
                    View.cardLayout.show(View.cardsPanel,"trainerTimetable");
                }
            }
        });
        
        ButtonFormat buttonUCA = new ButtonFormat("Update Class Attendance");
        buttonUCA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainer.getTimetable().size() == 0)
                    JOptionPane.showMessageDialog(null,"No upcoming class.");
                else
                {
                    updateAttendanceMenu();
                    View.cardLayout.show(View.cardsPanel,"updateAttendanceMenu");
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
        panelClassManagement2.add(buttonUCA,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"trainerMenu");}
        });
        panelClassManagement.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void arrangeFeedbackMenu()
    {
        panelFeedbackCriteria.removeAll();
        JPanel panelFeedbackCriteria2 = new JPanel(new GridBagLayout());
        panelFeedbackCriteria2.setBackground(new Color(1,121,111));
        panelFeedbackCriteria.add(panelFeedbackCriteria2);
        
        ButtonFormat buttonSub = new ButtonFormat(trainer.getSubID());
        buttonSub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (Feedback.getFeedbackList(trainer.getSubID()).size() == 0)//no feedback for this criteria
                    JOptionPane.showMessageDialog(null,"No feedback for this criteria.");
                else
                {
                    viewFeedback(trainer.getSubID());
                    View.cardLayout.show(View.cardsPanel,"trainerViewFeedback");
                }
            }
        });
        
        ButtonFormat buttonTrainer = new ButtonFormat(trainer.getTrainerID());
        buttonTrainer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (Feedback.getFeedbackList(trainer.getTrainerID()).size() == 0)//no feedback for this criteria
                    JOptionPane.showMessageDialog(null,"No feedback for this criteria.");
                else
                {
                    viewFeedback(trainer.getTrainerID());
                    View.cardLayout.show(View.cardsPanel,"trainerViewFeedback");
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
        panelFeedbackCriteria2.add(buttonSub,c);
        
        c.gridy = 1;
        panelFeedbackCriteria2.add(buttonTrainer,c);
        
        JPanel panelBackCriteria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBackCriteria.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBackCriteria.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"trainerMenu");}
        });
        panelFeedbackCriteria.add(panelBackCriteria,BorderLayout.SOUTH);
    }
    
    public void uploadMaterial()
    {
        panelUpload.removeAll();
        JPanel panelUpload2 = new JPanel(new GridBagLayout());
        panelUpload2.setBackground(new Color(1,121,111));
        panelUpload.add(panelUpload2);
        
        Subject s = Subject.getASubject(trainer.getSubID());
        JList<String> list = new JList <> (s.getMaterials());
        list.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane sp = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension (600,300));
        
        ButtonFormat upload = new ButtonFormat ("Upload");
        upload.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fc.setDialogTitle("Upload Materials");
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(true);
                fc.setApproveButtonText("Select");
                  
                boolean uploaded = false;
                if (fc.showOpenDialog(upload) == JFileChooser.APPROVE_OPTION)
                { 
                    File[] uploadFiles = fc.getSelectedFiles();
                    for (int i=0; i<uploadFiles.length; i++)
                    {
                        File materialLocation = new File("Subject Materials/" + trainer.getSubID() + "/Materials/");
                        String[] materials = materialLocation.list();
                        boolean repeat = false;
                        for (int j=0; j<materials.length; j++)
                        {
                            if (uploadFiles[i].getName().equals(materials[j]))
                                repeat = true;
                        }
                        
                        if (repeat)
                            JOptionPane.showMessageDialog(null,uploadFiles[i].getName() + " already existed in the material database.");
                        else
                        {
                            try
                            {
                                File f = new File ("Subject Materials/" + trainer.getSubID() + "/Materials/" + uploadFiles[i].getName());
                                Files.copy(uploadFiles[i].toPath(), f.toPath());//act as upload material(s) to database
                                uploaded = true;
                            }
                            catch (IOException ex) 
                            {ex.printStackTrace();}
                        }
                    }
                    
                    if (uploaded)
                        JOptionPane.showMessageDialog(null, "Successfully uploaded material(s).");
                }
                
                list.setListData(s.getMaterials());
                sp.revalidate();
                sp.repaint();
            }
        });
        
        ButtonFormat delete = new ButtonFormat ("Delete");
        delete.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent e)
            {
                Object[] selectedFiles = list.getSelectedValues();
                if (selectedFiles.length == 0)
                    JOptionPane.showMessageDialog(null,"Please select a file.");
                else if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,"Are you sure you want to delete the file(s)?", "Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE))
                {
                    for (int i=0; i<selectedFiles.length; i++)
                    {   
                        String file = selectedFiles[i].toString();
                        File f = new File ("Subject Materials/" + trainer.getSubID() + "/Materials/" + file);
                        f.delete();
                    }
                    list.setListData(s.getMaterials());
                    sp.revalidate();
                    sp.repaint();
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints(); 
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panelUpload2.add(sp,c);
        
        c.insets = new Insets(20,50,20,0);
        c.ipadx = 100;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panelUpload2.add(upload,c);
        
        c.gridx = 1;
        c.gridy = 1;
        panelUpload2.add(delete,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"trainerMenu");}
        });
        panelUpload.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewTimetable()
    {
        panelTT.removeAll();
        JPanel panelTT2 = new JPanel(new GridBagLayout());
        panelTT2.setBackground(new Color(1,121,111));
        panelTT.add(panelTT2);
        
        ArrayList<Class> timetable2 = trainer.getTimetable();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        
        JPanel panelTimetable = new JPanel(new GridBagLayout());
        int widthT = 205*timetable2.size();
        panelTimetable.setPreferredSize(new Dimension(widthT,230));
        panelTimetable.setBackground(Color.WHITE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        for (int i=0; i<timetable2.size(); i++)
        {
            JTextPane textPane = new JTextPane();
            StyledDocument style = textPane.getStyledDocument();
            SimpleAttributeSet align= new SimpleAttributeSet();
            StyleConstants.setAlignment(align, StyleConstants.ALIGN_CENTER);
            style.setParagraphAttributes(0, style.getLength(), align, false);
            textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
            textPane.setEditable(false); 
            textPane.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
            textPane.setText("\n\n" + timetable2.get(i).getSubID() + "\n" + timetable2.get(i).getClassID() + "\n" + date.format(timetable2.get(i).getDate()) 
                            + "\n 11:00AM - 05:00PM \n\n");
            panelTimetable.add(textPane,c);
            
            c.gridx++;
        }

        JScrollPane scrollpane = new JScrollPane(panelTimetable,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        int widthS = 1000;
        if (widthT < widthS)
            widthS = widthT;
        scrollpane.setPreferredSize(new Dimension(widthS,230));
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelTT2.add(scrollpane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"trainerClassManagement");}
        });
        panelTT.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void updateAttendanceMenu()
    {
        panelUpdateMenu.removeAll();
        JPanel panelUpdateMenu2 = new JPanel(new GridBagLayout());
        panelUpdateMenu2.setBackground(new Color(1,121,111));
        panelUpdateMenu.add(panelUpdateMenu2);
        
        ArrayList<Class> timetable2 = trainer.getTimetable();
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String[] classList = new String[timetable2.size()];
        for (int i=0; i<timetable2.size(); i++)
            classList[i] = timetable2.get(i).getClassID() + " (" + date.format(timetable2.get(i).getDate()) + ")";
        
        JList<String> list = new JList <> (classList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(500,500));
        list.setSelectedIndex(0);
        list.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
        JScrollPane scrollpane = new JScrollPane(list);
        
        ButtonFormat buttonSelect = new ButtonFormat("Select");
        buttonSelect.setPreferredSize(new Dimension(200,50));
        buttonSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                Class c = Class.getAClass(list.getSelectedValue().toString().substring(0,6));
                if (c.getCapacity() == 0)
                    JOptionPane.showMessageDialog(null,"This class has no trainees.");
                else
                {
                    updateAttendance(list.getSelectedValue().toString().substring(0,6));
                    View.cardLayout.show(View.cardsPanel,"updateAttendance");
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20,20,20,20);
        panelUpdateMenu2.add(scrollpane,c);
        
        c.gridx = 0;
        c.gridy = 1;
        panelUpdateMenu2.add(buttonSelect,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {View.cardLayout.show(View.cardsPanel,"trainerClassManagement");}
        });
        panelUpdateMenu.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void updateAttendance(String classID)
    {
        panelUpdate.removeAll();
        JPanel panelUpdate2 = new JPanel(new GridBagLayout());
        panelUpdate2.setBackground(new Color(1,121,111));
        panelUpdate.add(panelUpdate2);
        
        ArrayList<String> nameList2 = Class.getAClass(classID).getNameList();
        ArrayList<String> attendedList2 = Class.getAClass(classID).getAttendedList();
        
        JButton[] buttonAttend = new JButton[nameList2.size()];
        JPanel panelNameList = new JPanel(new GridBagLayout());
        int heightT = 600;
        if (nameList2.size() <= 10)
            heightT = 50 + 50*nameList2.size();
        
        Label headerID = new Label("Trainee ID");
        Label headerName = new Label("Trainee Name");
        Label headerAttendance = new Label("Attendance");
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panelNameList.add(headerID,c);
        
        c.gridx = 1;
        c.gridy = 0;
        headerName.setPreferredSize(new Dimension(400,50));
        panelNameList.add(headerName,c);
        
        c.gridx = 2;
        c.gridy = 0;
        panelNameList.add(headerAttendance,c);
        
        c.gridx = 0;
        c.gridy = 0;
        for (int i=0; i<nameList2.size(); i++)
        {
            c.gridy++;
            Label traineeIDs = new Label(nameList2.get(i));
            panelNameList.add(traineeIDs,c);
        }
        
        c.gridx = 1;
        c.gridy = 0;
        for (int i=0; i<nameList2.size(); i++)
        {
            c.gridy++;
            Trainee t = Profile.getATrainee(nameList2.get(i));
            Label traineeNames = new Label(t.getTraineeName());
            traineeNames.setPreferredSize(new Dimension(400,50));
            panelNameList.add(traineeNames,c);
        }
        
        c.gridx = 2;
        c.gridy = 0;
        for (int i=0; i<nameList2.size(); i++)
        {
            c.gridy++;
            buttonAttend[i] = new JButton("Attend");
            buttonAttend[i].setName(nameList2.get(i));//trainee ID on button
            buttonAttend[i].setPreferredSize(new Dimension(200,50));
            buttonAttend[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    JButton btn = (JButton)e.getSource();
                    btn.setEnabled(false);
                    if (!attendedList2.contains(btn.getName()))
                    {
                        Class c = Class.getAClass(classID);
                        c.addTraineeToAttendedList(btn.getName(),false);//trainee ID inside
                    }
                }
            });
            panelNameList.add(buttonAttend[i],c);
            
            if (attendedList2.contains(nameList2.get(i)))
                buttonAttend[i].setEnabled(false);
        }
        
        JScrollPane scrollpane = new JScrollPane(panelNameList,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        int heightS = 600;
        if (heightT < heightS)
            heightS = heightT;
        scrollpane.setPreferredSize(new Dimension(820,heightS));
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelUpdate2.add(scrollpane,c);
        
        JPanel panelBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBack.setBackground(new Color(1,121,111));
        ButtonFormat buttonBack = new ButtonFormat("Back");
        panelBack.add(buttonBack);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (trainer.getTimetable().size() != 0)
                {
                    updateAttendanceMenu();
                    View.cardLayout.show(View.cardsPanel,"updateAttendanceMenu");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No more upcoming class.\nRedirect back to menu.");
                    View.cardLayout.show(View.cardsPanel,"trainerMenu");
                }
            }
        });
        panelUpdate.add(panelBack,BorderLayout.SOUTH);
    }
    
    public void viewFeedback(String criteria)
    {
        panelFeedback.removeAll();
        JPanel panelFeedback2 = new JPanel(new GridBagLayout());
        panelFeedback2.setBackground(new Color(1,121,111));
        panelFeedback.add(panelFeedback2);
        
        ArrayList<Feedback> feedbackList2 = Feedback.getFeedbackList(criteria);
        String[] column = {"FeedbackID","Rating","Comment"};
        Object [][] data = new Object[feedbackList2.size()][3];

        for(int i=0; i<feedbackList2.size(); i++)
        {
            data[i][0] = feedbackList2.get(i).getFeedbackID();
            data[i][1] = feedbackList2.get(i).getRating();
            data[i][2] = feedbackList2.get(i).getComment();
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
        table.getColumn(column[1]).setPreferredWidth(50);//rating
        table.getColumn(column[2]).setPreferredWidth(700);//comment
        table.getColumn(column[2]).setCellRenderer(new MultiLineCellRenderer());
        
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
            {View.cardLayout.show(View.cardsPanel,"trainerViewFeedbackCriteria");}
        });
        panelFeedback.add(panelBack,BorderLayout.SOUTH);
    }
}