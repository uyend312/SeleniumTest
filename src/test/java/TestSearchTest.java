import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpiraTestConfiguration(
        url = "https://rmit.spiraservice.net/",
        login = "s3904418",
        rssToken = "{7D805319-92EA-4A0F-B919-EBA1C365EBAB}",
        projectId = 42
)
//user searches for a product on main page and view the detail of the product that they want to check
public class TestSearchTest {

    private static WebDriver driver;


    private String expectedResult;
    private String actualResult;
    private WebElement element;
    private WebDriverWait wait;


    public void setTimer()
    {
        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(1)
    //@SpiraTestCase( testCaseId = 3989)
    @DisplayName("Verify website can be loaded")
    public void testwebsite()
    {
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        //Specify the amount of time driver should wait when searching for an element if it is not immediately present.
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        actualResult = "JPetStore Demo";
        expectedResult = driver.getTitle();
        assertEquals(expectedResult,actualResult);
    }

    @BeforeAll
    //Setup my driver here through @BeforeAll, this method is running once
    //all test classes
    public static void setup()
    {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Test
    @Order(2)
    @DisplayName("Verify user can search for product")
    //@SpiraTestCase( testCaseId = 3990)
    public void testSearchForProduct() {
        // Navigate to the catalog page
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        setTimer();

        WebElement searchField = driver.findElement(By.name("keyword"));
        searchField.click();
        searchField.sendKeys("Dog");
        driver.findElement(By.name("searchProducts")).click();

        setTimer();



        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[3]")));


        assertEquals("Bulldog", productTitle.getText(), "Correct result");
    }

    @Test
    @Order(3)
    @DisplayName("Verify user can read a product detail (catalog)")
   // @SpiraTestCase( testCaseId = 3991)
    public void testProductDetail()
    {
        WebElement productLink = driver.findElement(By.linkText("K9-BD-01"));
        productLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2")));

        assertEquals("Bulldog", productTitle.getText(), "Displayed correctly.");
        setTimer();
    }

    @Test
    @Order(4)
    @DisplayName("Verify can read a detail of a specific product")
    //@SpiraTestCase( testCaseId = 3992)
    public void testProductPageDetail()
    {
        WebElement productLink = driver.findElement(By.linkText("EST-6"));
        productLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td/b")));

        assertEquals("EST-6", productTitle.getText(), "Pet details page displayed correctly.");
        setTimer();
    }
    @AfterAll
    //closing the browser
    //all test classes
    public static void CloseBrowser()
    {
        driver.close();
    }
}
