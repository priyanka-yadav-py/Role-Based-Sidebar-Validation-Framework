package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SidebarPage {

    WebDriver driver;
    WebDriverWait wait;

    public SidebarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Sidebar locator
    By sidebar = By.xpath("//ul[@class='sidebar-menu']");

    // Check if sidebar exists (IMPORTANT)
    public boolean isSidebarPresent() {
        return driver.findElements(sidebar).size() > 0;
    }

    // Click Main Menu
    public void clickMainMenu(String menuName) {

        By menu = By.xpath(
            "//ul[@class='sidebar-menu']//a[.//span[normalize-space()='" + menuName + "']]"
        );

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(menu));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        System.out.println("Clicked Main Menu: " + menuName);
    }

    // Click Sub Menu
    public void clickSubMenu(String mainMenu, String subMenu) {

        clickMainMenu(mainMenu);

        By sub = By.xpath(
            "//span[normalize-space()='" + mainMenu + "']/ancestor::li//a[contains(normalize-space(),'" + subMenu + "')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(sub)).click();

        System.out.println(" Clicked Sub Menu: " + subMenu);
    }

    // Check if menu is visible (SAFE)
    public boolean isMenuVisible(String menuName) {
        try {
            By menu = By.xpath(
                "//ul[@class='sidebar-menu']//span[normalize-space()='" + menuName + "']"
            );
            return wait.until(ExpectedConditions.visibilityOfElementLocated(menu)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get all visible sidebar menus (VERY IMPORTANT)
    public List<String> getVisibleMenus() {

        List<String> menus = new ArrayList<>();

        try {
            if (!isSidebarPresent()) {
                System.out.println("Sidebar not present");
                return menus; // return empty list
            }

            List<WebElement> elements = driver.findElements(
                By.xpath("//ul[@class='sidebar-menu']//span[normalize-space()]")
            );

            for (WebElement el : elements) {

                if (el.isDisplayed()) {
                    String text = el.getText().trim();

                    if (!text.isEmpty() && !menus.contains(text)) {
                        menus.add(text);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error while fetching sidebar menus");
        }

        return menus; //  NEVER NULL
    }

    //  Navigation Example
    public CampaignListPage goToManageCampaigns() {
        clickSubMenu("Campaigns", "Manage Campaigns");
        return new CampaignListPage(driver);
    }
}