import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
//user searches for a product on main page and view the detail of the product that they want to check
public class TestSearch {
    public static void main(String[] args)
    {
        System.setProperty("webdriver.chrome.driver","D:\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://petstore.octoperf.com/actions/Catalog.action");

        WebElement searchField = driver.findElement(By.name("keyword"));
        searchField.click();
        searchField.sendKeys("Dog");
        driver.findElement(By.name("searchProducts")).click();

        //wait time
        setTimer();
        WebElement productLink = driver.findElement(By.linkText("K9-BD-01"));
        productLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h2"))); //h2 is the title of the dog (Bulldog in this case)
        productTitle.click();
        //wait time here
        setTimer();


        //correct keyword
        if (productTitle.getText().equals("Bulldog")) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test Failed");
        }

        //read details of EST-6 dog
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/table/tbody/tr[2]/td[1]/a")).click(); //click EST-6 to read detail



        // Close the browser
        setTimer();
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

