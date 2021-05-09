package dev.j4d.twitchchatreader.twitchconnection;

import dev.j4d.twitchchatreader.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

@Component
public class TwitchConnection {

    private static final Logger logger = LoggerFactory.getLogger(TwitchConnection.class);

    private final BufferedReader inputReader;
    private final DataOutputStream outputStream;
    private final User botUser;

    public TwitchConnection(
            @Value("${twitch.host}") String host,
            @Value("${twitch.port}") int port,
            @Value("${twitch.username}") String username,
            @Value("${twitch.token}") String token) throws IOException {
        final var socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        this.inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.botUser = new User(username, token);
        login();
    }

    public boolean isLoggedIn() {
        return botUser.isLoggedIn();
    }

    public void login() {
        if (botUser.isLoggedIn()) {
            logger.error("Failed to login: A user is already logged in");
            return;
        }
        send("PASS " + botUser.getToken());
        send("NICK " + botUser.getUsername());
        botUser.setLoggedIn(true);
    }

    public void send(String message) {
        try {
            outputStream.writeBytes(message + "\n");
            logOutput(message);
        } catch (IOException ex) {
            logger.error("Exception occurred while writing to socket", ex);
        }
    }

    public Optional<String> read() {
        try {
            return Optional.ofNullable(inputReader.readLine());
        } catch (IOException ex) {
            logger.error("Exception occurred while reading from socket", ex);
            return Optional.empty();
        }
    }

    private void logOutput(String output) {
        logger.info("TO TWITCH << {}", output);
    }
}
