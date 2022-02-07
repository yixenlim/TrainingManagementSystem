import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.File;

public class View extends JFrame
{
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel cardsPanel;
    
    private static InitController initCon = new InitController();//read database
    private static TraineeController traineeCon = new TraineeController();
    private static TrainerController trainerCon = new TrainerController();
    private static AdminController adminCon = new AdminController();
    private JPanel mainPanel;
    
    public View()
    {
        super("Training Management System");
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        new LoginController(); 
        
        cardsPanel = new JPanel();
        cardsPanel.setLayout(cardLayout);
        this.add(cardsPanel);
        
        cardsPanel.add(LoginController.panelLogin,"loginMenu");
        
        cardsPanel.add(traineeCon.panelTraineeMenu,"traineeMenu");
        cardsPanel.add(traineeCon.panelSubMenu,"traineeSubMenu");
        cardsPanel.add(traineeCon.panelDownload,"traineeDownload");
        cardsPanel.add(traineeCon.panelInfo,"subInfo");
        cardsPanel.add(traineeCon.panelClassManagement,"traineeClassManagement");
        cardsPanel.add(traineeCon.panelTT,"traineeTimetable");
        cardsPanel.add(traineeCon.panelAttendance,"traineeAttendance");
        cardsPanel.add(traineeCon.panelRequest,"traineeRequest");
        cardsPanel.add(traineeCon.panelFeedback,"traineeFeedback");
        
        cardsPanel.add(trainerCon.panelTrainerMenu,"trainerMenu");
        cardsPanel.add(trainerCon.panelClassManagement,"trainerClassManagement");
        cardsPanel.add(trainerCon.panelUpload,"trainerUpload");
        cardsPanel.add(trainerCon.panelTT,"trainerTimetable");
        cardsPanel.add(trainerCon.panelUpdateMenu,"updateAttendanceMenu");
        cardsPanel.add(trainerCon.panelUpdate,"updateAttendance");
        cardsPanel.add(trainerCon.panelFeedbackCriteria,"trainerViewFeedbackCriteria");
        cardsPanel.add(trainerCon.panelFeedback,"trainerViewFeedback");
        
        cardsPanel.add(adminCon.panelAdminMenu,"adminMenu");
        cardsPanel.add(adminCon.panelCertManagement,"certManagement");
        cardsPanel.add(adminCon.panelViewCompletedList,"viewCompletedList");
        cardsPanel.add(adminCon.panelUploadCert,"uploadCert");
        cardsPanel.add(adminCon.panelFeedbackMenu,"feedbackMenu");
        cardsPanel.add(adminCon.panelFeedback,"viewAllFeedback");
        cardsPanel.add(adminCon.panelProfileManagement,"profileManagement");
        cardsPanel.add(adminCon.panelRegister,"register");
        cardsPanel.add(adminCon.panelViewProfile,"viewAllProfile");
        cardsPanel.add(adminCon.panelClassManagement,"classManagement");
        cardsPanel.add(adminCon.panelCreateClass,"createClass");
        cardsPanel.add(adminCon.panelManageRequest,"manageRequest");
        cardsPanel.add(adminCon.panelAssignSub,"assignTraineeSub");
        cardsPanel.add(adminCon.panelAssign,"assignTrainee");
        
        setVisible(true);
    }
    
    public static void isTrainee(String traineeID)
    {traineeCon = new TraineeController(traineeID);}
    
    public static void isTrainer(String trainerID)
    {trainerCon = new TrainerController(trainerID);}
    
    public static void isAdmin(String adminID)
    {adminCon = new AdminController(adminID);}
    
    public static void main(String[] args)
    {new View();}
}

class ButtonFormat extends JButton
{
    private Font buttonFont = new Font(Font.DIALOG,Font.PLAIN,20);    
    
    public ButtonFormat(String name)
    {
        setText(name);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFont(buttonFont);
        setFocusPainted(false);
        
        if (name.equals("Back"))
            setPreferredSize(new Dimension(100,50));
    }
}