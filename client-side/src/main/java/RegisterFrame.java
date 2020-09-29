import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {

    Container container=getContentPane();
    JLabel userLabel=new JLabel("USERNAME");
    JLabel nicknameLabel=new JLabel("NICKNAME");
    JLabel fullnameLabel=new JLabel("FULLNAME");
    JLabel emailLabel=new JLabel("EMAIL");
    JLabel passwordLabel=new JLabel("PASSWORD");
    JLabel retypepasswordLabel=new JLabel("RETYPE");
    JTextField userTextField=new JTextField();
    JTextField nicknameTextFeild=new JTextField();
    JTextField fullnameTextFeild=new JTextField();
    JTextField emailTextFeild=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JPasswordField retypepasswordField=new JPasswordField();
    JButton registerButton=new JButton("CONFIRM");
    JButton backtoLoginButton=new JButton("BACK TO LOGIN");


    public RegisterFrame()
    {
        //Calling methods inside constructor.
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();

    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        userLabel.setBounds(50,150,100,30);
        nicknameLabel.setBounds(50,180,100,30);
        fullnameLabel.setBounds(50,210,100,30);
        emailLabel.setBounds(50,240,100,30);
        passwordLabel.setBounds(50,270,100,30);
        retypepasswordLabel.setBounds(50,300,100,30);
        userTextField.setBounds(150,150,150,30);
        nicknameTextFeild.setBounds(150,180,150,30);
        fullnameTextFeild.setBounds(150,210,150,30);
        emailTextFeild.setBounds(150,240,150,30);
        passwordField.setBounds(150,270,150,30);
        retypepasswordField.setBounds(150,300,150,30);
        registerButton.setBounds(150,350,100,30);
        backtoLoginButton.setBounds(120,400,160,30);

    }
    public void addComponentsToContainer()
    {
        //Adding each components to the Container
        container.add(userLabel);
        container.add(nicknameLabel);
        container.add(fullnameLabel);
        container.add(emailLabel);
        container.add(passwordLabel);
        container.add(retypepasswordLabel);
        container.add(userTextField);
        container.add(nicknameTextFeild);
        container.add(fullnameTextFeild);
        container.add(emailTextFeild);
        container.add(passwordField);
        container.add(retypepasswordField);
        container.add(registerButton);
        container.add(backtoLoginButton);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
