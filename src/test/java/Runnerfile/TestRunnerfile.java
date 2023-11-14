package Runnerfile;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (features = "src/test/resources",
                plugin = {"pretty", "html:target/cucumber-html-report.html"
                        ,"html:target/cucumber_reports/cucumber_pretty.html"
                        ,"json:target/cucumber_reports/cucumberTestReport.json"
//                        ,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter"
                },
                glue = {"StepDefination"},

                tags="@tag1")
public class TestRunnerfile extends AbstractTestNGCucumberTests {
}
