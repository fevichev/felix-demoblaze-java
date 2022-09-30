package com.test.utils;

import com.test.base.BaseTest;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportingHelper extends BaseTest {

    public ReportingHelper(WebDriver driver) {
        super(driver);
    }

    public static void publishReport() {
        if (prop.getProperty("publish.report").matches("true")) {
            executeCommandLineInTerminal("npm i test-results-reporter");
            executeCommandLineInTerminal("npx test-results-reporter publish "
                    + prop.getProperty("path.to.report.config.file"));
        }
    }

    public static void executeCommandLineInTerminal(String commandToExecute) {
        try {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(commandToExecute);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                logMessage(line);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
