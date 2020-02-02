package utils;

import cucumber.api.event.EventHandler;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestStepFinished;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AllureListener extends AllureCucumber4Jvm {

    private final EventHandler<TestStepFinished> stepFinishedHandlerScreenshot;

    public AllureListener() {
        this(Allure.getLifecycle());
    }

    public AllureListener(AllureLifecycle lifecycle) {
        super(lifecycle);
        this.stepFinishedHandlerScreenshot = this::handleTestStepFinishedScreenshot;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(cucumber.api.event.TestStepFinished.class, this.stepFinishedHandlerScreenshot);
        super.setEventPublisher(publisher);
    }

    private void handleTestStepFinishedScreenshot(TestStepFinished event) {
        try {
            if ((event.result.getStatus().firstLetterCapitalizedName().equalsIgnoreCase("Failed")) || (System.getProperty("screenShot").equalsIgnoreCase("allSteps")) && (!event.testStep.getCodeLocation().equalsIgnoreCase("Hooks.quitWebDriver(Scenario)"))) {
                String currTimems = String.valueOf(System.currentTimeMillis());
                File src = ((TakesScreenshot) Hooks.getDriver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File("./target/screenshots/Image_" + event.testStep.getCodeLocation() + currTimems + ".png"));
                Path content = Paths.get("./target/screenshots/Image_" + event.testStep.getCodeLocation() + currTimems + ".png");
                try (InputStream is = Files.newInputStream(content)) {
                    Allure.addAttachment("Step Screenshot:", is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}