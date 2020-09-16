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

    public void WriteUserToFile(User user) throws IOException {
        FileWriter csvWriter  = new FileWriter(filePath, true);
        csvWriter.append("\n");
        csvWriter.append(user.getUsername());
        csvWriter.append(",");
        csvWriter.append(user.getPassword());
        csvWriter.append(",");
        csvWriter.append(user.getFullname());
        csvWriter.append(",");
        csvWriter.append(user.getNickname());
        csvWriter.append(",");
        csvWriter.append(user.getEmail());
        csvWriter.close();
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
