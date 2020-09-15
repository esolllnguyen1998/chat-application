package com.dack.spring.ws.Infrastructure.repo;

import com.dack.spring.ws.Infrastructure.entities.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandle {
    private String filePath;

    public FileHandle() {
        filePath = "src/main/java/com/dack/spring/ws/infrastructure/files/users.csv";
    }

    public ArrayList<String> ReadAllFile() {
        BufferedReader reader;
        var lines = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void WriteUserToFile(User user, boolean isForce) throws IOException {
        String fileName = filePath;
        FileWriter fw = new FileWriter(fileName, isForce);
        byte[] bin_user = ConvertUserToBytes(user);
        for (byte b : bin_user) {
            fw.write(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        fw.write(System.lineSeparator());
        fw.close();
    }

    private static byte[] ConvertUserToBytes(User user) {
        String strUser = user.getFullname() + ";" + user.getPassword() + ";" + user.getFullname() + ";"
                + user.getNickname() + ";" + user.getEmail();
        return strUser.getBytes();
    }

    public void UpdateNewListUserToFile(ArrayList<User> users) throws IOException {
        boolean isFirstTime = true;
        for (User user : users) {
            if (isFirstTime) {
                WriteUserToFile(user, false);
                isFirstTime = false;
                continue;
            }
            WriteUserToFile(user, true);
        }
    }

    public void WriteToCVSFile(ArrayList<String> strUsers) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
        writer.write("Username,Password,Fullname,Nickname,Email");
        writer.newLine();
        for (String user : strUsers) {
            writer.write(user.replace(";", ","));
            writer.newLine();
        }

        writer.close();
    }
}
