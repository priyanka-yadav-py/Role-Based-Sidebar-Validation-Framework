package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CampaignListPage {
	
	
	WebDriver driver;
    WebDriverWait wait;
    
    public CampaignListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    By pageHeader = By.xpath("//span[contains(normalize-space(),'Campaigns')]");

    public boolean isOnCampaignListPage() {
        return driver.findElement(pageHeader).isDisplayed();
    }

}
