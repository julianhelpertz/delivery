package de.deluxesoftware.delivery.misc;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class Utils {

    private Utils() {}

    public static void logSuccess(Logger logger) {
        logger.log(Level.INFO, "[Delivery-Server] Successful!");
    }

    public static void logError(Logger logger) {
        logger.log(Level.INFO, "[Delivery-Server] An error occurred!");
    }

    public static long cryptAdler32(String key) {
        Checksum checksum = new Adler32();
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        checksum.update(bytes);
        return checksum.getValue();
    }

    public static boolean checkPortAvailability(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

}
