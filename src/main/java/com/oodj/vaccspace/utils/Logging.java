package com.oodj.vaccspace.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class Logging {
    public static Path logPath = Paths.get("storage/logs.txt");

    public static void write(String log) {
        try {
            createLogFileIfNotExist();
            Files.write(logPath, prepareLog(log).getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String prepareLog(String log) {
        return String.format("[%s] %s%n", getDate(), log);
    }

    private static String getDate() {
        return Instant.now().toString();
    }

    private static void createLogFileIfNotExist() throws IOException {
        if (!Files.exists(logPath)) {
            Files.createFile(logPath);
        }
    }
}
