package utils;

import cucumber.api.Scenario;
import java.io.FileOutputStream;

public class TestRailResultsTracker {

    public static void addTestCaseAndPassFailDetails(Scenario scenario) {
        if (!System.getProperty("updateTestRail").equalsIgnoreCase("false")) {
            String textToWrite = "TestCaseID:" + getTestCaseID(scenario) + "#Status:" + getTestCaseStatus(scenario) + "\r\n";
            try {
                FileOutputStream fosTestRail = new FileOutputStream(Constants.TEST_RAIL_RESULTS_FILE_PATH, true);
                fosTestRail.write(textToWrite.getBytes());
                fosTestRail.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int getTestCaseID(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.contains(":")){
                String TestCaseKeyword = tag.split(":")[0].replace("@TestRail(", "");
                if (scenario.getName().contains(TestCaseKeyword)) {
                    return Integer.parseInt(tag.split(":")[1].replace("C", "").replace(")", ""));
                }
            }
            else if (tag.contains("@TestRail")) {
                return Integer.parseInt(tag.replace("@TestRail(C", "").replace(")", ""));
            }
        }
        return -1;
    }

    private static int getTestCaseStatus(Scenario scenario) {
        if (scenario.getStatus().firstLetterCapitalizedName().equals("Passed")) {
            return 1;
        } else if (scenario.getStatus().firstLetterCapitalizedName().equals("Failed")) {
            return 5;
        } else {
            return 4;
        }
    }

}
