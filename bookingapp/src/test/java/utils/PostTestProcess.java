package utils;

import static utils.Decoder.decode;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class PostTestProcess {

    private static String TEST_RAIL_URL;
    private static String TEST_RAIL_USERNAME;
    private static String TEST_RAIL_PASSWORD;
    private static int TEST_RAIL_PROJECTID;
    private static int TEST_RAIL_SUITEID;
    private static boolean SUITE_AVAILABLE;
    private static TestRail testRail;
    private static Run run;

    public static void main(String[] args) {
        updateTestRail();
        killAllDriverProcesses();
    }

    private static void updateTestRail() {
        if (!System.getProperty("updateTestRail").equalsIgnoreCase("false")) {
            getTestRailCredentials();
            createRun();
            addTestCasesToRun();
            publishTestCaseResults();
        }
    }


    private static void getTestRailCredentials() {
        String testRailCred = null;
        if (System.getProperty("encodedCreds").equalsIgnoreCase("true")) {
            testRailCred = decode(System.getProperty("updateTestRail"));
        } else {
            testRailCred = System.getProperty("updateTestRail");
        }
            String[] testRailCreds = testRailCred.split("#");
            TEST_RAIL_URL = testRailCreds[0];
            TEST_RAIL_USERNAME = testRailCreds[1];
            TEST_RAIL_PASSWORD = testRailCreds[2];
            TEST_RAIL_PROJECTID = Integer.parseInt(testRailCreds[3]);
            if (testRailCreds.length > 4) {
                SUITE_AVAILABLE = true;
                TEST_RAIL_SUITEID = Integer.parseInt(testRailCreds[4]);
            }
    }

    private static void createRun() {
        testRail = TestRail.builder(TEST_RAIL_URL, TEST_RAIL_USERNAME, TEST_RAIL_PASSWORD).build();
        String runName = "Automation Test Run at :" + new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        if (SUITE_AVAILABLE) {
            run = testRail.runs().add(TEST_RAIL_PROJECTID, new Run().setName(runName).setSuiteId(TEST_RAIL_SUITEID).setIncludeAll(false)).execute();
        }
        else {
            run = testRail.runs().add(TEST_RAIL_PROJECTID, new Run().setName(runName).setIncludeAll(false)).execute();
        }
    }

    private static void addTestCasesToRun() {
        List<Integer> testCaseIds = new LinkedList<>();
        try {
            File testRailResults = new File(Constants.TEST_RAIL_RESULTS_FILE_PATH);
            BufferedReader testRailResultsReader = new BufferedReader(new FileReader(testRailResults));
            String result = "";
            while ((result = testRailResultsReader.readLine()) != null && result.length() > 1) {
                int testCaseID = Integer.parseInt(result.split(":")[1].replace("#Status", ""));
                if (testCaseID > 0) {
                    testCaseIds.add(testCaseID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        run.setCaseIds(testCaseIds);
        testRail.runs().update(run).execute();
    }

    private static void publishTestCaseResults() {
        try {
            File testRailResults = new File(Constants.TEST_RAIL_RESULTS_FILE_PATH);
            BufferedReader testRailResultsReader = new BufferedReader(new FileReader(testRailResults));
            String result = "";
            while ((result = testRailResultsReader.readLine()) != null && result.length() > 1) {
                int testCaseID = Integer.parseInt(result.split(":")[1].replace("#Status", ""));
                if (testCaseID > 0) {
                    int testStatusID = Integer.parseInt(result.split(":")[2]);
                    List<ResultField> customResultFields = testRail.resultFields().list().execute();
                    testRail.results().addForCase(run.getId(), testCaseID, new Result().setStatusId(testStatusID), customResultFields).execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void killAllDriverProcesses() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM WindowsChromeDriver.exe");
        } catch (Exception ignored) {
        }
    }

}
