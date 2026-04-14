package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage extends BasePage {

    // Menú lateral
    private final By adminMenuLink = By.xpath("//span[contains(@class, 'oxd-text--span') and text()='Admin']");

    // Campos del panel de búsqueda
    private final By searchUsernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    private final By searchListBox       = By.cssSelector("div[role='listbox']");
    private final By userRoleDropdown    = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By userRoleESS         = By.xpath("//div[@role='listbox']//span[text()='ESS']");
    private final By userRoleAdmin       = By.xpath("//div[@role='listbox']//span[text()='Admin']");
    private final By employeeNameInput   = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private final By statusDropdown      = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By statusEnable        = By.xpath("//div[@role='listbox']//span[text()='Enabled']");
    private final By statusDisable           = By.xpath("//div[@role='listbox']//span[text()='Disabled']");
    private final By employeeNameAutocomplete = By.xpath("//div[@role='option'][1]");

    // Botones principales
    private final By addButton           = By.xpath("//button[normalize-space()='Add']");
    private final By searchButton        = By.xpath("//button[normalize-space()='Search']");

    // Confirmación de eliminación
    private final By deleteConfirmButton = By.xpath("//button[normalize-space()='Yes, Delete']");

    public void clickAdminMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuLink)).click();
        wait.until(ExpectedConditions.urlContains("admin/viewSystemUsers"));
    }

    public void assertOnAdminPage() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlContains("admin/viewSystemUsers"));
    }

    public void clickAdd() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void selectUserRoleFilter(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        By option = role.equalsIgnoreCase("ESS") ? userRoleESS : userRoleAdmin;
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectStatusFilter(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        By option = status.equalsIgnoreCase("Enabled") ? statusEnable : statusDisable;
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void searchByUsername(String username) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput));
        input.clear();
        input.sendKeys(username);
        
        // Intentar seleccionar del autocomplete si aparece
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchListBox));
            By matchingOption = By.xpath("//div[@role='listbox']//div[@role='option'][contains(normalize-space(.), "
                    + toXpathLiteral(username) + ")]"
            );
            wait.until(ExpectedConditions.elementToBeClickable(matchingOption)).click();
        } catch (TimeoutException e) {
            // Si no hay autocomplete, proceder sin seleccionar
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void searchByUsernameAndRole(String username, String role) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput));
        input.clear();
        input.sendKeys(username);
        selectUserRoleFilter(role);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void typeEmployeeNameFilter(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameAutocomplete)).click();
    }

    public void searchByAllFilters(String username, String role, String employeeName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput));
        input.clear();
        input.sendKeys(username);
        selectUserRoleFilter(role);
        typeEmployeeNameFilter(employeeName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void deleteUserByUsername(String username) {
        By deleteBtn = By.xpath(
                "//div[@role='row' and .//div[normalize-space()='" + username + "']]" +
                "//button[.//i[contains(@class,'bi-trash')]]");
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmButton)).click();
    }

    public boolean isUserAbsentInResults(String username) {
        By userCell = By.xpath("//div[@role='row']//div[normalize-space()='" + username + "']");
        List<WebElement> cells = driver.findElements(userCell);
        return cells.isEmpty();
    }

    private String toXpathLiteral(String text) {
        if (!text.contains("'")) {
            return "'" + text + "'";
        }
        if (!text.contains("\"")) {
            return "\"" + text + "\"";
        }

        StringBuilder builder = new StringBuilder("concat(");
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\'') {
                builder.append("\"'\"");
            } else if (c == '\"') {
                builder.append("'\"'");
            } else {
                builder.append("'").append(c).append("'");
            }

            if (i < text.length() - 1) {
                builder.append(",");
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
