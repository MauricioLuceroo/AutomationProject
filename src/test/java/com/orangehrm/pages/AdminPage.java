package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AdminPage extends BasePage {

    // Menú lateral
    private final By adminMenuLink = By.xpath("//span[contains(@class, 'oxd-text--span') and text()='Admin']");

    // Campos del panel de búsqueda
    private final By searchUsernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    private final By userRoleDropdown    = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By userRoleESS         = By.xpath("//div[@role='listbox']//span[text()='ESS']");
    private final By userRoleAdmin       = By.xpath("//div[@role='listbox']//span[text()='Admin']");
    private final By employeeNameInput   = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private final By statusDropdown      = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By statusEnable        = By.xpath("//div[@role='listbox']//span[text()='Enabled']");
    private final By statusDisable       = By.xpath("//div[@role='listbox']//span[text()='Disabled']");

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
        wait.until(ExpectedConditions.urlContains("admin/viewSystemUsers"));
    }

    public void clickAdd() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void searchByUsername(String username) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput));
        input.clear();
        input.sendKeys(username);
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
}
