package edu.tum.ase.compiler.service;

import edu.tum.ase.compiler.model.SourceCode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    public Map<String, Object> compile(SourceCode sourceCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", sourceCode.getCode());
        response.put("fileName", sourceCode.getFileName());

        File tempFile = null;
        try {
            // Create a temporary directory and file with the correct name
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            tempFile = new File(tempDir, sourceCode.getFileName());

            // Write source code to the file
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(sourceCode.getCode());
            }

            // Compile the file using `javac`
            Process process = Runtime.getRuntime().exec("javac " + tempFile.getAbsolutePath());

            // Capture stdout and stderr
            String stdout = captureStream(process.getInputStream());
            String stderr = captureStream(process.getErrorStream());

            process.waitFor();

            // Populate response
            response.put("stdout", stdout);
            response.put("stderr", stderr);
            response.put("compilable", stderr.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
            response.put("stderr", e.getMessage());
            response.put("compilable", false);
        } finally {
            // Clean up the temporary file
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }

        return response;
    }

    private String captureStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append(System.lineSeparator());
        }
        return result.toString().trim();
    }
}

