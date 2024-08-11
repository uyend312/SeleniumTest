import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
//User add/remove a product to cart and check out
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpiraTestConfiguration(
        url = "https://rmit.spiraservice.net/",
        login = "s3904418",
        rssToken = "{7D805319-92EA-4A0F-B919-EBA1C365EBAB}",
        projectId = 42
)
public class TestCheckOutTest {

    private static ChromeDriver driver;
    private String expectedResult;
    private String actualResult;
    public static void setTimer()
    {
        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    //Setup my driver here through @BeforeAll, this method is running once
    //all test classes
    public static void setup()
    {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    @AfterAll
    //closing the browser
    //all test classes
    public static void CloseBrowser()
    {
        driver.close();
    }
    @Test
    @Order(1)
    @DisplayName("Verify Website can be loaded")
    //@SpiraTestCase(testCaseId = 3946)
    public void testwebsite()
    {
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        //Specify the amount of time driver should wait when searching for an element if it is not immediately present.
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        actualResult = "JPetStore Demo";
        expectedResult = driver.getTitle();
        assertEquals(expectedResult,actualResult);
    }

    @Test
    @Order(2)
    @DisplayName("Verify Successful Login")
    //@SpiraTestCase(testCaseId = 3947)
    public void testSuccessfulLogin() {
        //log in process
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");
        driver.findElement(By.linkText("Sign In")).click();

        // valid username and password
        driver.findElement(By.name("username")).sendKeys("uyen");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("uyen");

        setTimer();
        //sign in
        driver.findElement(By.name("signon")).click();

        WebElement userProfileLink = driver.findElement(By.linkText("My Account")); ////*[@id="MenuContent"]/a[3]
        assertNotNull(userProfileLink, "Log in success");
    }

    @Test
    @Order(3)
    @DisplayName("Verify Adding Product to Cart (with a product that is out of stock")
    //@SpiraTestCase(testCaseId = 3948)
    public void testAddProductToCart() {
        // Log in as a registered user (use the credentials provided in the test)
        driver.findElement(By.xpath("//*[@id=\"QuickLinks\"]/a[4]")).click();
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[1]/a")).click(); //Manx cat
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[5]/a")).click(); //EST-14 type - this is add to cart process
        driver.findElement(By.xpath("//*[@id=\"QuickLinks\"]/a[2]")).click(); //go to dogs
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[1]/a")).click(); //add bulldog
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[5]/a")).click(); //add male adult bulldog (out-of-stock product)
        setTimer();
    //you're not supposed to be able to add the out of stock product
        WebElement falseInStock = driver.findElement(By.xpath("//*[@id=\"Cart\"]/form/table/tbody/tr[3]/td[4]")); //false in stock
        assertEquals("false", falseInStock.getText());

    }

    @Test
    @Order(4)
    @DisplayName("Quantity changed after refreshing page")
   // @SpiraTestCase( testCaseId = 4278)
    public void testRefreshPage()
    {
        driver.navigate().refresh();
        setTimer();

        //driver.findElement(By.id("input_name")).getAttribute("value");
        WebElement quantity = driver.findElement(By.xpath("//*[@id=\"Cart\"]/form/table/tbody/tr[3]/td[5]/input"));
        expectedResult = "1";
        assertEquals(expectedResult,  quantity.getAttribute("value"));
    }
    @Test
    @Order(5)
    @DisplayName("Verify Item can be removed")
    //@SpiraTestCase( testCaseId = 3993)
    public void testRemoveItem()
    {
        // Locate the "Remove" link
        WebElement remain = driver.findElement(By.linkText("EST-14"));
        WebElement remove = driver.findElement(By.xpath("//*[@id=\"Cart\"]/form/table/tbody/tr[2]/td[8]/a"));

        remove.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.stalenessOf(remove));

        driver.findElement(By.name("updateCartQuantities")).click();

        assertFalse(remain.isDisplayed());
    }
    @Test
    @Order(6)
    @DisplayName("Verify Checkout Process (check out with out-of stock product")
    //@SpiraTestCase(testCaseId = 3949)
    public void testCheckoutProcess() {

        //WebElement falseInStock = driver.findElement(By.xpath("//*[@id=\"Cart\"]/form/table/tbody/tr[3]/td[4]")); //false in stock
        driver.findElement(By.xpath("//*[@id=\"Cart\"]/a")).click(); //click proceed checkout
        driver.findElement(By.name("newOrder")).click(); //"continue" button
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/a")).click(); //"confirm" button
        setTimer();

        WebElement confirmCart = driver.findElement(By.xpath("//*[@id=\"Content\"]/ul/li"));
        assertNotNull(confirmCart, "check out success with \"out of stock\" product");
        System.out.println("Test completed");

    }


}