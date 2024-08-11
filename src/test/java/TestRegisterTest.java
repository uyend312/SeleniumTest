import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
//user registers a new account and used that to log in, then they change the password and log out
@SpiraTestConfiguration(
        url = "https://rmit.spiraservice.net/",
        login = "s3904418",
        rssToken = "{7D805319-92EA-4A0F-B919-EBA1C365EBAB}",
        projectId = 42
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRegisterTest {

    private static ChromeDriver driver;
    private String expectedResult;
    private String actualResult;
    private WebElement element;
    private WebDriverWait wait;


    @BeforeAll
    //Setup my driver here through @BeforeAll, this method is running once
    //all test classes
    public static void setup()
    {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Test
    @Order(1)
    @DisplayName("Verify website can be loaded")
    //@SpiraTestCase(testCaseId = 3941)
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
   @Order(7)
    @DisplayName("Verify register can be failed with incorrect data type")
   // @SpiraTestCase(testCaseId = 3945 )
    public void testFailedRegistration() {
        // Navigate to the registration page
        driver.findElement(By.linkText("Register Now!")).click();
        driver.findElement(By.name("username")).sendKeys("abc"); //duplicate username
        driver.findElement(By.name("password")).sendKeys("abc");
        driver.findElement(By.name("repeatedPassword")).sendKeys("abc");
        driver.findElement(By.name("account.firstName")).sendKeys("abc");
        driver.findElement(By.name("account.lastName")).sendKeys("xyz");
        driver.findElement(By.name("account.email")).sendKeys("abc@email.com");
        //no phone number (the text field is blank)
        driver.findElement(By.name("account.address1")).sendKeys("123 street");
        driver.findElement(By.name("account.address2")).sendKeys("456 district");
        driver.findElement(By.name("account.city")).sendKeys("mars");
        driver.findElement(By.name("account.state")).sendKeys("earth");
        driver.findElement(By.name("account.zip")).sendKeys("333");
        driver.findElement(By.name("account.country")).sendKeys("sun");
        driver.findElement(By.name("newAccount")).click();


        //bring to the main page where the table of pets is available
        WebElement myMenu = driver.findElement(By.xpath("//*[@id=\"SidebarContent\"]"));
        assertTrue(myMenu.isDisplayed(), "User registration successful.");




    }


    @Test
    @Order(2)
    @DisplayName("Verify Successful register")
    //@SpiraTestCase(testCaseId = 3943)
    public void testSuccessfulRegistration()
    {
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.linkText("Register Now!")).click();
        driver.findElement(By.name("username")).sendKeys("abc3013");
        driver.findElement(By.name("password")).sendKeys("abc");
        driver.findElement(By.name("repeatedPassword")).sendKeys("abc");
        driver.findElement(By.name("account.firstName")).sendKeys("abc");
        driver.findElement(By.name("account.lastName")).sendKeys("xyz");
        driver.findElement(By.name("account.email")).sendKeys("abc@email.com");
        driver.findElement(By.name("account.phone")).sendKeys("00099999");
        driver.findElement(By.name("account.address1")).sendKeys("123 street");
        driver.findElement(By.name("account.address2")).sendKeys("456 district");
        driver.findElement(By.name("account.city")).sendKeys("mars");
        driver.findElement(By.name("account.state")).sendKeys("earth");
        driver.findElement(By.name("account.zip")).sendKeys("333");
        driver.findElement(By.name("account.country")).sendKeys("sun");
        driver.findElement(By.name("newAccount")).click();


        //bring to the main page where the table of pets is available
        WebElement myMenu = driver.findElement(By.xpath("//*[@id=\"SidebarContent\"]"));
        assertTrue(myMenu.isDisplayed(), "User registration successful.");
    }
    @Test
    @Order(3)
    @DisplayName("Verify Successful Login")
   // @SpiraTestCase(testCaseId = 3942)
    public void testLogin()
    {
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("username")).sendKeys("abc3013");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("abc");
        driver.findElement(By.name("signon")).click();
        //lead to the main page where the table of pets is available if log in is success

        WebElement myMenu = driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[3]"));
        assertTrue(myMenu.isDisplayed(), "User login is successful.");


    }

    @Test
    @Order(4)
    @DisplayName("Verify user can change password")
   // @SpiraTestCase(testCaseId = 4099)
    public void testEditPasswd()
    {
        driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[3]")).click(); //go to my profile
        driver.findElement(By.name("password")).sendKeys("kakaka");
        driver.findElement(By.name("repeatedPassword")).sendKeys("kakaka");
        driver.findElement(By.name("editAccount")).click();
        assertAll(
                () -> {
                    WebElement passwordField = driver.findElement(By.name("password"));
                    String actualPasswordValue = passwordField.getAttribute("value");
                    assertEquals("kakaka", actualPasswordValue);
                },
                () -> {
                    WebElement repeatedPasswordField = driver.findElement(By.name("repeatedPassword"));
                    String actualRepeatedPasswordValue = repeatedPasswordField.getAttribute("value");
                    assertEquals("kakaka", actualRepeatedPasswordValue);
                }
        );
    }

    @Test
    @Order(5)
    @DisplayName("Verify Successful Log out")
   // @SpiraTestCase(testCaseId = 3944)
    public void logOut()
    {
        WebElement logOutAccount = driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[2]"));
        logOutAccount.click();
        assertFalse(logOutAccount.isDisplayed(), "Sign out option is disable after log out."); //supposed to be failed

    }

    @Test
    @Order(6)
    @DisplayName("Verify log in fail (password cannot be changed")
   // @SpiraTestCase(testCaseId = 4098)
    public void logInFailAfterChangePassword()
    {
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("username")).sendKeys("abc3013");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("kakaka"); //new password
        driver.findElement(By.name("signon")).click();
        //lead to the main page where the table of pets is available if log in is success

        WebElement message = driver.findElement(By.xpath("//*[@id=\"Content\"]/ul/li"));
        assertTrue(message.isDisplayed(), "User login fail.");
    }



    @AfterAll
    //closing the browser
    //all test classes
    public static void CloseBrowser()
    {
        driver.close();
    }
}