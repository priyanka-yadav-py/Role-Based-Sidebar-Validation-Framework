package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {

    // DataProvider (Excel → Map)
    @DataProvider(name = "loginData")
    public Object[][] loginData() {

      //  String path = "/Users/priyanka/eclipse-workspace/LMS-Multi-User-Login/data/users.xlsx";    Static path
    	String path = System.getProperty("user.dir") + "/data/users.xlsx";

        return ExcelUtils.getTestData(path, "Sheet1");
    }

    //  Main Test
    @Test(dataProvider = "loginData")
    public void loginWithMultipleUsers(HashMap<String, String> data) throws InterruptedException {

        //  Read data from Excel
        String username = data.get("username");
        String password = data.get("password");
        String menus = data.get("menus");

        LoginPage loginPage = new LoginPage(driver);
        SidebarPage sidebarPage = new SidebarPage(driver);

        //  Login
        loginPage.login(username, password);
        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL: " + currentUrl);

        // Validate Login
        Assert.assertFalse(
                driver.getCurrentUrl().contains("login"),
                " Login failed for user: " + username
        );

        System.out.println("\n User: " + username);

        //  Get Actual Sidebar Menus
        List<String> actualMenus = sidebarPage.getVisibleMenus();

        //  Case 1: Page-only users (No Sidebar)
        if (actualMenus.isEmpty()) {

            System.out.println("No sidebar for user: " + username);

            Assert.assertFalse(
                    driver.getCurrentUrl().contains("login"),
                    " Page not loaded for user: " + username
            );

            System.out.println(" Page validated for: " + username);

            logout();
            return;
        }

        // Case 2: No expected menus in Excel
        if (menus == null || menus.trim().isEmpty()) {

            System.out.println(" No expected menus provided in Excel for: " + username);

            logout();
            return;
        }

        //  Convert Expected Menus
        List<String> expectedMenus = Arrays.stream(menus.split(","))
                                           .map(String::trim)
                                           .filter(menu -> !menu.isEmpty())
                                           .toList();

        System.out.println("Expected Menus: " + expectedMenus);
        System.out.println("Actual Menus: " + actualMenus);

        //  Validate Expected Menus
        for (String menu : expectedMenus) {

            if (!actualMenus.contains(menu)) {

                System.out.println("\n Missing menu for user: " + username);
                System.out.println("Missing Menu: " + menu);

                Assert.fail("Missing menu: " + menu);
            }
        }

        //  Validate Unauthorized Menus
        for (String menu : actualMenus) {

            if (!expectedMenus.contains(menu)) {

                System.out.println("\n Unauthorized menu for user: " + username);
                System.out.println("Unauthorized Menu: " + menu);

                Assert.fail("Unauthorized menu visible: " + menu);
            }
        }

        System.out.println("Sidebar validated for: " + username);

        //  Logout
        logout();
    }

    //  Logout Method
    public void logout() {

        try {

            driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();

        } catch (Exception e) {

            System.out.println("⚠ Logout not found, using fallback URL");

            driver.get("https://lmsstage1.collegedunia.com/logout");
        }
    }
}
