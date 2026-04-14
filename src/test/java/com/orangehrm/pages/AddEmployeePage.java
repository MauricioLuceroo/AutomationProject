package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEmployeePage extends BasePage {

    // Campos del formulario de agregar empleado
    private final By firstNameInput  = By.xpath("//input[@name='firstName']");
    private final By middleNameInput = By.xpath("//input[@name='middleName']");
    private final By lastNameInput   = By.xpath("//input[@name='lastName']");
    private final By employeeIdInput = By.xpath("//div[./label[text()='Employee Id']]/following-sibling::div//input");
    private final By lastNameRequiredError = By.xpath(
            "//input[@name='lastName']/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//span[contains(@class,'oxd-input-field-error-message') and normalize-space()='Required']");

    // Botones
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");

    public void enterFirstName(String firstName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        input.clear();
        input.sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(middleNameInput));
        input.clear();
        input.sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        input.clear();
        input.sendKeys(lastName);
    }

    public void enterEmployeeId(String employeeId) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdInput));
        input.click();
        input.clear();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(employeeId);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void assertSaved() {
        wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
    }

    public String getCurrentEmployeeId() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdInput));
        return input.getAttribute("value").trim();
    }

    public String getEmpNumberFromCurrentUrl() {
        String url = driver.getCurrentUrl();
        Matcher matcher = Pattern.compile("/empNumber/(\\d+)").matcher(url);
        return matcher.find() ? matcher.group(1) : "";
    }

    public boolean isLastNameRequiredErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameRequiredError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
