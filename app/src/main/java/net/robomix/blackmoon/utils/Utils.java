package net.robomix.blackmoon.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.activation.MimetypesFileTypeMap;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;

public class Utils {

    @NonNull
    public static String convertErrorsListToString(List<String> errorsList) {
        StringBuilder result = new StringBuilder();
        if (errorsList == null || errorsList.size() == 0) {
            return result.toString();
        }

        for (int i = 0; i < errorsList.size(); i++) {
            result.append("You have next error(s): ");
            result.append((i + 1)).append(".").append(" ").append(errorsList.get(i));
            if (i + 1 < errorsList.size()){
                result.append(", ");
            }
        }
        return result.toString();
    }

    @NonNull
    public static String getHashFromFile(String path) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(path)));
            byte[] digest = md.digest();
            result = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    public static String getMimeType(File file) {
        if (file == null) {
            return null;
        }
        return new MimetypesFileTypeMap().getContentType(file);
    }
}
