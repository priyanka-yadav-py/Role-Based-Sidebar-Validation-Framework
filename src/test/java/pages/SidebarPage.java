package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SidebarPage {

    WebDriver driver;
    WebDriverWait wait;

    public SidebarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Sidebar root
    By sidebar = By.xpath("//ul[@class='sidebar-menu']");
    public void clickMainMenu(String menuName) {
        By menu = By.xpath("//ul[@class='sidebar-menu']//a[.//span[normalize-space()='" + menuName + "']]");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(menu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(element));

        // Small wait for animation
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        // Force JS click (most reliable for sidebar)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        
     // ✅ DEBUG LINE
        System.out.println("Clicked Main Menu: " + menuName);
        
    }

    // ✅ Dynamic Sub Menu (REUSED LOGIC)
    public void clickSubMenu(String mainMenu, String subMenu) {
    	clickMainMenu(mainMenu);
    	By sub = By.xpath("//span[normalize-space()='" + mainMenu + "']/ancestor::li//a[contains(normalize-space(),'" + subMenu + "')]");
    	wait.until(ExpectedConditions.elementToBeClickable(sub)).click();
    	
    }

    // ✅ Verify menu present
    public boolean isMenuVisible(String menuName) {
        By menu = By.xpath("//ul[@class='sidebar-menu']//span[text()='" + menuName + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(menu)).isDisplayed();
        }
     
    
    // ✅ Navigation method (IMPORTANT)
    public CampaignListPage goToManageCampaigns() {
        clickSubMenu("Campaigns", "Manage Campaigns");
        return new CampaignListPage(driver);
    
    }
    
}