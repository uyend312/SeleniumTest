import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//Verify the checkout process for a registered user with a valid shopping cart.
//User add/remove a product to cart and check out
public class TestCheckOut {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        //1. Browse the Google page
        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        //2. Locate the sign-in button and Click
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("username")).sendKeys("uyen");
        driver.findElement(By.name("password")).clear(); //the textfield already has some keywords, needs to clear before enter
        driver.findElement(By.name("password")).sendKeys("uyen");
        driver.findElement(By.name("signon")).click();

        setTimer();

        //3. Add "cat" to cart
        driver.findElement(By.xpath("//*[@id=\"QuickLinks\"]/a[4]")).click(); //menu table
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[1]/a")).click(); //Manx cat
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[5]/a")).click(); //EST-14 type - this is adding to cart process

        setTimer();
        //4. go back
        driver.findElement(By.xpath("//*[@id=\"QuickLinks\"]/a[2]")).click(); //go to dogs
        setTimer();
        // add more item
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[1]/a")).click(); //add bulldog
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[5]/a")).click(); //add male adult bulldog (out-of-stock product)
        setTimer();
        //refresh page
        driver.navigate().refresh();
        setTimer();
        //5. remove item (remove cat to only check out dog
        driver.findElement(By.xpath("//*[@id=\"Cart\"]/form/table/tbody/tr[2]/td[8]/a")).click(); //remove cat

        setTimer();
        //6.check out
        driver.findElement(By.xpath("//*[@id=\"Cart\"]/a")).click(); //click proceed checkout
        driver.findElement(By.name("newOrder")).click(); //"continue" button
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/a")).click(); //"confirm" button

        //7. result message
        System.out.println("Successful test");
        //8. wait time before close
       setTimer();

//close browser
        driver.quit();


    }
    public static void setTimer()
    {
        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
