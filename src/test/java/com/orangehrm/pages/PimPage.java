package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class PimPage extends BasePage {

    // Menú lateral
    private final By pimMenuLink = By.xpath("//span[contains(@class, 'oxd-text--span') and text()='PIM']");

    // Botón Add de la lista de empleados
    private final By addEmployeeButton = By.xpath("//button[normalize-space()='Add']");

    // Campos del formulario de búsqueda
    private final By employeeIdSearchInput = By.xpath("//div[./label[text()='Employee Id']]/following-sibling::div//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");

    public void clickPimMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
        wait.until(ExpectedConditions.urlContains("pim/viewEmployeeList"));
    }

    public void assertOnPimPage() {
        wait.until(ExpectedConditions.urlContains("pim/viewEmployeeList"));
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton)).click();
    }

    public void searchByEmployeeId(String employeeId) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdSearchInput));
        input.clear();
        input.sendKeys(employeeId);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isEmployeeVisibleInResults(String employeeId) {
        By row = By.xpath("//div[@role='row']//div[normalize-space()='" + employeeId + "']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(row));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
