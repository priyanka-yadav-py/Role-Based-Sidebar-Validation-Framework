package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert; 

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestData;

public class LoginTest extends BaseTest {

  /*  @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Credentials will be here for temporarily
        loginPage.login("aag", "lmsadmin@111");
        
        //  Validation
        boolean isDashboardVisible = driver.findElement(
        	    By.xpath("//span[normalize-space()='Dashboard']")
        	).isDisplayed();
        	Assert.assertTrue(isDashboardVisible, "Login failed - Dashboard not visible");
    }	*/
	
	 // ✅ DataProvider (fetch usernames)
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return TestData.getLoginData();
    }

    // ✅ Test for multiple users
    @Test(dataProvider = "loginData")
    public void loginWithMultipleUsers(String username) {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // 🔹 Common password
        String password = TestData.getPassword();

        // 🔹 Login
        loginPage.login(username, password);

        // 🔹 Validation (Generic for all Users)
        Assert.assertTrue(
        		!driver.getCurrentUrl().contains("login"),
                "❌ Login failed for user: " + username
        );

        System.out.println("✅ Login successful for: " + username);
    }
    

    public void logout() {
        try {
            driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
        } catch (Exception e) {
            System.out.println("⚠ Logout not found, trying direct URL");

            // fallback
            driver.get("https://lmsstage1.collegedunia.com/logout");
        }
    }
}