import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//test log in after register a new account and manage their account
//user registers a new account and used that to log in, then they change the password and log out
public class TestRegister {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        //1. Browse the Google page
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        //2. Locate the sign-in button and Click "Register Now!" link text
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.linkText("Register Now!")).click();
        //3. fill the form
        testRegistration(driver);
        setTimer();
        testLogin(driver);
        setTimer();
        testEditProfile(driver);
        setTimer();
        testLogOut(driver);
        setTimer();
        driver.close();

    }
    public static void setTimer()
    {
        try {
            Thread.sleep(5000); // 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void testRegistration(WebDriver driver)
    {
        driver.findElement(By.name("username")).sendKeys("kkalalalalala");
        driver.findElement(By.name("password")).sendKeys("hahaha");
        driver.findElement(By.name("repeatedPassword")).sendKeys("hahaha");
        driver.findElement(By.name("account.firstName")).sendKeys("abc");
        driver.findElement(By.name("account.lastName")).sendKeys("xyz");
        driver.findElement(By.name("account.email")).sendKeys("abc@email.com");
        driver.findElement(By.name("account.phone")).sendKeys("9380801");
        driver.findElement(By.name("account.address1")).sendKeys("123 street");
        driver.findElement(By.name("account.address2")).sendKeys("456 district");
        driver.findElement(By.name("account.city")).sendKeys("mars");
        driver.findElement(By.name("account.state")).sendKeys("earth");
        driver.findElement(By.name("account.zip")).sendKeys("333");
        driver.findElement(By.name("account.country")).sendKeys("sun");

        //Click submit button
        driver.findElement(By.name("newAccount")).click();
    }

    private static void testLogin(WebDriver driver)
    {
        driver.findElement(By.linkText("Sign In")).click();
        //log in with the created account
        driver.findElement(By.name("username")).sendKeys("kkalalalalala");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("hahaha");
        driver.findElement(By.name("signon")).click();
        //lead to the main page where the table of pets is available if log in is success

    }
    private static void testLogOut(WebDriver driver)
    {
        WebElement logOutAccount = driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[2]"));
        logOutAccount.click();
    }
    //change password
    private static void testEditProfile(WebDriver driver)
    {
        driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[3]")).click(); //go to my profile
        driver.findElement(By.name("password")).sendKeys("kakaka");
        driver.findElement(By.name("repeatedPassword")).sendKeys("kakaka");
        driver.findElement(By.name("editAccount")).click();

    }
}
