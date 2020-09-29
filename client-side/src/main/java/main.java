import Model.LoginModel;
import Model.RegisterModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main extends JDialog {


    public static void main(String[] args) {
        final boolean[] isLoggedIn = {false};
        final boolean[] isShowRegister = {false};
        HandleDisplay(isLoggedIn, isShowRegister );
    }

    public static void HandleDisplay(final boolean[] isLoggedIn,final boolean[] isShowRegister )
    {
        if(!isLoggedIn[0] && !isShowRegister[0] ) {
            final LoginFrame frame = new LoginFrame();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.loginButton.addActionListener(new ActionListener() {
                @lombok.SneakyThrows
                @Override
                public void actionPerformed(ActionEvent e) {
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    LoginModel loginModel = new LoginModel();
                    loginModel.setUsername(frame.userTextField.getText());
                    loginModel.setPassword(frame.passwordField.getText());
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonInputString = mapper.writeValueAsString(loginModel);
                    RequestBody body = RequestBody.create(mediaType, jsonInputString);
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/auth/login")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        isLoggedIn[0] = true;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed.");
                    }
                }
            });
            frame.registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isShowRegister[0] = true;
                    frame.setVisible(false);
                    HandleDisplay(isLoggedIn, isShowRegister);
                }
            });
        }

        if(isShowRegister[0])
        {
            final RegisterFrame frame = new RegisterFrame();
            frame.setTitle("Register Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.registerButton.addActionListener(new ActionListener() {
                @SneakyThrows
                @Override
                public void actionPerformed(ActionEvent e) {
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    ObjectMapper mapper = new ObjectMapper();
                    RegisterModel registerModel = new RegisterModel(frame.userTextField.getText(),frame.passwordField.getText(),
                                                                frame.fullnameTextFeild.getText(),  frame.nicknameTextFeild.getText(),
                                                            frame.emailTextFeild.getText());
                    String jsonInputString = mapper.writeValueAsString(registerModel);
                    RequestBody body = RequestBody.create(mediaType,jsonInputString );
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/auth/register")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200) {
                        isShowRegister[0] = false;
                        frame.setVisible(false);
                        HandleDisplay(isLoggedIn, isShowRegister);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Register failed.");
                    }
                }
            });

            frame.backtoLoginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isShowRegister[0] = false;
                    frame.setVisible(false);
                    HandleDisplay(isLoggedIn, isShowRegister);
                }
            });
        }

    }
}
