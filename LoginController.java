import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.text.*;
import java.io.*;

public class LoginController
{
    public static JPanel panelLogin = new JPanel(new GridBagLayout());
    
    public LoginController()
    {
        panelLogin.removeAll();
        panelLogin.repaint();
        panelLogin.revalidate();
        panelLogin.setBackground(new Color(1,121,111));
        
        JLabel label = new JLabel("BIG BRAIN T.M.S",SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.PLAIN,40));
        
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.white);
        userLabel.setFont(new Font("Arial", Font.PLAIN,25));

        JTextField userText = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN,25));

        JPasswordField  passwordText = new JPasswordField (20);
        passwordText.setEchoChar('*');
        
        ButtonFormat logInButton = new ButtonFormat("Log In");
        logInButton.setPreferredSize(new Dimension(120,50));
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {   
                if (userText.getText().isEmpty() || passwordText.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Please enter username and password");
                else
                {
                    String user = Profile.verifyLogin(userText.getText(),passwordText.getText());
                    
                    if (user == null)
                        JOptionPane.showMessageDialog(null,"Wrong username or password");
                    else if (user.equals("Trainee"))
                    {
                        new TraineeController(userText.getText());
                        View.cardLayout.show(View.cardsPanel,"traineeMenu");
                    }
                    else if (user.equals("Trainer"))
                    {
                        new TrainerController(userText.getText());
                        View.cardLayout.show(View.cardsPanel,"trainerMenu");
                    }
                    else if (user.equals("Admin"))
                    {
                        new AdminController(userText.getText());
                        View.cardLayout.show(View.cardsPanel,"adminMenu");
                    }
                }
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 80;//make this component tall
        c.gridwidth = 2;
        panelLogin.add(label,c);
        
        c = new GridBagConstraints();
        c.insets = new Insets(20,5,5,5);      
        c.gridx = 0;
        c.gridy = 1;       
        panelLogin.add(userLabel,c);
        
        c.gridx = 1;
        c.gridy = 1;
        panelLogin.add(userText,c);
        
        c.gridx = 0;
        c.gridy = 2;
        panelLogin.add(passwordLabel,c);
        
        c.gridx = 1;
        c.gridy = 2;
        panelLogin.add(passwordText,c);
        
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        panelLogin.add(logInButton,c);
    }
}