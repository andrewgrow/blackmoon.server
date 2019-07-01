package net.robomix.blackmoon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final String ERROR_MESSAGE = "error_message";
    public static final String INFO_MESSAGE = "info_message";

    private static String hostName;

    @Value("${hostname}")
    public void setHostName(String hostName) {
        Constants.hostName = hostName;
    }

    public static String getHostName() {
        return hostName;
    }
}
