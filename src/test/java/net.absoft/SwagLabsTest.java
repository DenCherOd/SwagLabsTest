package net.absoft;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SwagLabsTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        WebDriverWait wait = (new WebDriverWait(driver, 10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }
    @Test(groups= {"Negative"})
    public void authorizationWithWrongPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(), 'Epic sadface: Username " +
                "and password do not match any user in this service')]")).isDisplayed(),
                "Login and password are correct");
    }

    @Test(groups = {"Negative"})
    public void blockedUserAuthorization() {
        driver.findElement(By.id("user-name")).sendKeys(("locked_out_user"));
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(), 'Epic sadface: Sorry, this" +
                " user has been locked out')]")).isDisplayed(),"User is not blocked");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}