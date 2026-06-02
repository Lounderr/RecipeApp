package recipe.app;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:recipe/app",
        glue = "recipe.app",
        plugin = {"pretty", "html:target/report.html"}
)
public class Starter {
}
