package stepdefs;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue     = "stepdefs",
        plugin   = {
                "pretty",
                "html:target/cucumber-reports/index.html"
        }
)
public class RunCucumberTest {
}
