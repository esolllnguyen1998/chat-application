package com.dack.spring.ws.Infrastructure.repo;

import com.dack.spring.ws.api.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileHandle {
    private String filePath;

    public FileHandle(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> ReadAllFile() {
        BufferedReader reader;
        var lines = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String strUser= BinaryToText(line);
                lines.add(strUser);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String BinaryToText(String binaryString) {
        StringBuilder stringBuilder = new StringBuilder();
        int charCode;
        for (int i = 0; i < binaryString.length(); i += 8) {
            charCode = Integer.parseInt(binaryString.substring(i, i + 8), 2);
            String returnChar = Character.toString((char) charCode);
            stringBuilder.append(returnChar);
        }
        return stringBuilder.toString();
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
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("files/users.csv"));
        writer.write("Username,Password,Fullname,Nickname,Email");
        writer.newLine();
        for (String user : strUsers) {
            writer.write(user.replace(";", ","));
            writer.newLine();
        }

        writer.close();
    }
}
