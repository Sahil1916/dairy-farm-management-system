package com.sahil.dairy;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

public final class AgentDebugLog {

    private static final String SESSION = "5edbf6";
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(400))
            .build();

    private AgentDebugLog() {
    }

    public static void log(String hypothesisId, String location, String message, String dataJson) {
        String line = "{\"sessionId\":\"" + SESSION + "\",\"hypothesisId\":\"" + hypothesisId
                + "\",\"location\":\"" + location + "\",\"message\":\"" + message
                + "\",\"data\":" + dataJson + ",\"timestamp\":" + System.currentTimeMillis() + "}\n";
        Path[] paths = new Path[] {
                Paths.get("C:\\Users\\barge\\Desktop\\Projects\\my_dairy_system\\debug-5edbf6.log"),
                Paths.get(System.getProperty("user.dir"), "debug-5edbf6.log")
        };
        for (Path p : paths) {
            try {
                Files.writeString(p, line, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (Exception ignored) {
            }
        }
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:7264/ingest/cdd70c4e-abcb-4e9f-a5ee-974c1e8be476"))
                    .timeout(Duration.ofMillis(800))
                    .header("Content-Type", "application/json")
                    .header("X-Debug-Session-Id", SESSION)
                    .POST(HttpRequest.BodyPublishers.ofString(line.trim()))
                    .build();
            HTTP.send(req, HttpResponse.BodyHandlers.discarding());
        } catch (Exception ignored) {
        }
    }
}
