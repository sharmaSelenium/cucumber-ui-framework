package utils;

public class PostTestProcess {

    public static void main(String[] args) {
        killAllDriverProcesses();
    }
    private static void killAllDriverProcesses() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        } catch (Exception ignored) {
        }
    }

}
