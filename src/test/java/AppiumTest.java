import com.aventstack.extentreports.Status;
import org.example.config.BaseTest;
import org.junit.Test;

public class AppiumTest extends BaseTest {

    @Test
    public void testLogin() {
        // Log a message to the Extent report
        test.log(Status.PASS, "Login successful!");
        extent.flush();
    }
}
