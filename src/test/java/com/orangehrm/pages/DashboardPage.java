package com.orangehrm.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {

    public static final String DASHBOARD_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";

    public void open() {
        driver.get(DASHBOARD_URL);
    }

    public void assertLoaded() {
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }
}




