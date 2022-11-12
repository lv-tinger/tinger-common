package org.tinger.common.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourceUtils {

    public static String readText(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        InputStream inputStream = loadResource(path);

        if (inputStream == null) {
            return "";
        }

        try {
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            return new String(bytes, Charset.defaultCharset());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            IoUtils.close(inputStream);
        }

    }

    public static List<String> readLines(String path) {
        if (StringUtils.isEmpty(path)) {
            return Collections.emptyList();
        }

        InputStream inputStream = loadResource(path);
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;
        try {
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(streamReader);
            return bufferedReader.lines().filter(line -> !StringUtils.prefixWith(line, "#")).collect(Collectors.toList());

        } catch (Exception ex) {
            throw new RuntimeException();
        } finally {
            IoUtils.close(bufferedReader);
            IoUtils.close(inputStream);
            IoUtils.close(streamReader);
        }
    }

    public static InputStream loadResource(String path) {
        if (path.startsWith("classpath://")) {
            String classPath = path.substring(12);
            return Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResourceAsStream(classPath);
        } else {
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                throw new RuntimeException();
            }
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException ignore) {
                throw new RuntimeException();
            }
        }
    }
}
