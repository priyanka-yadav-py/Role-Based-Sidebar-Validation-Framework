
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By username = By.name("username");
    By password = By.name("password");
    By loginBtn = By.xpath("//button[contains(text(),'Sign In')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String user, String pass) {

        // Wait for username field
        WebElement userField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        );
        userField.clear();
        userField.sendKeys(user);

        // Wait for password field
        WebElement passField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        );
        passField.clear();
        passField.sendKeys(pass);

        // Wait and click login
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(loginBtn)
        );
        button.click();
    }
}