package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddUserPage extends BasePage {

    private final By userRoleDropdown      = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By employeeNameInput     = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private final By autocompleteOption    = By.xpath("//div[@role='option'][1]");
    private final By statusDropdown        = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By usernameInput         = By.xpath("//label[text()='Username']/following::input[1]");
    private final By passwordInput         = By.xpath("//label[text()='Password']/following::input[1]");
    private final By confirmPasswordInput  = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    private final By saveButton            = By.xpath("//button[@type='submit']");

    public void selectUserRole(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
        By option = By.xpath("//div[@role='listbox']//span[text()='" + role + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void typeEmployeeName(String name) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(employeeNameInput));
        input.sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteOption)).click();
    }

    public void selectStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void enterUsername(String username) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        input.clear();
        input.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        input.clear();
        input.sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput));
        input.clear();
        input.sendKeys(password);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }
}
