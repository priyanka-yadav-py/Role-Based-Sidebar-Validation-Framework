package tests;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.CampaignListPage;


public class CampaignTest extends BaseTest {

    @Test
    public void testManageCampaignNavigation() {

        System.out.println("TEST STARTED");

        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.login("aag", "lmsadmin@111");

        System.out.println("LOGIN DONE");

        // Step 2: Navigate using Sidebar
        SidebarPage sidebar = new SidebarPage(driver);

        System.out.println("CLICKING CAMPAIGNS");

        CampaignListPage page = sidebar.goToManageCampaigns();

        System.out.println("NAVIGATION DONE");
        
        // ✅ Validation
        if (page.isOnCampaignListPage()) {
            System.out.println("On Manage Campaign Page ✅");
        } else {
            System.out.println("Navigation Failed ❌");
        }
    }
    }
