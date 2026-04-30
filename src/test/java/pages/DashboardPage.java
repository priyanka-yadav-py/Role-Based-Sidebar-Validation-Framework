package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    // Locator for Dashboard text
    By dashboardText = By.xpath("//span[normalize-space()='Dashboard']");

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to verify dashboard is displayed
    public boolean isDashboardDisplayed() {
        return driver.findElement(dashboardText).isDisplayed();
    }

	public boolean isDashboardVisible() {
		// TODO Auto-generated method stub
		return false;
	}
}