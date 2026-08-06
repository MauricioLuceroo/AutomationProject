package com.orangehrm.steps;

import com.orangehrm.config.DriverManager;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;



public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        DriverManager.createDriver();

        if (shouldSkipPrecondition(scenario)) {
            return;
        }

        ensureLoggedIn();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    private boolean shouldSkipPrecondition(Scenario scenario) {
        for (String tag : scenario.getSourceTagNames()) {
            String normalized = tag == null ? "" : tag.trim();
            if (normalized.equalsIgnoreCase("@login") || normalized.equalsIgnoreCase("login")) {
                return true;
            }
        }
        return false;
    }

    private void ensureLoggedIn() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        loginPage.open();

        String username = System.getProperty("orangehrm.user",
                System.getenv().getOrDefault("ORANGEHRM_USER", "Admin"));
        String password = System.getProperty("orangehrm.pass",
                System.getenv().getOrDefault("ORANGEHRM_PASS", "admin123"));

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        dashboardPage.assertLoaded();
    }
}
