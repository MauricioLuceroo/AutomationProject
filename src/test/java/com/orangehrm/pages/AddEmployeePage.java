package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddEmployeePage extends BasePage {

    // Campos del formulario de agregar empleado
    private final By firstNameInput  = By.xpath("//input[@name='firstName']");
    private final By middleNameInput = By.xpath("//input[@name='middleName']");
    private final By lastNameInput   = By.xpath("//input[@name='lastName']");
    private final By employeeIdInput = By.xpath("//div[./label[text()='Employee Id']]/following-sibling::div//input");

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
        input.clear();
        input.sendKeys(employeeId);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void assertSaved() {
        wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
    }
}
