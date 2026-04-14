package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class PimPage extends BasePage {

    // Menú lateral
    private final By pimMenuLink = By.xpath("//span[contains(@class, 'oxd-text--span') and text()='PIM']");

    // Botón Add de la lista de empleados
    private final By addEmployeeButton = By.xpath("//button[normalize-space()='Add']");

    // Campos del formulario de búsqueda
    private final By employeeIdSearchInput = By.xpath("//div[./label[text()='Employee Id']]/following-sibling::div//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By deleteConfirmButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By loadingSpinner = By.cssSelector("div.oxd-form-loader");
    private final By noRecordsFound = By.xpath("//*[normalize-space()='No Records Found']");

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
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (TimeoutException ignored) {
            // El loader puede no aparecer en todas las ejecuciones.
        }
    }

    public boolean isEmployeeVisibleInResults(String employeeId) {
        By row = By.xpath("//div[@role='row']//div[normalize-space()='" + employeeId + "']");
        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(WebDriverException.class)
                    .until(d -> !d.findElements(row).isEmpty());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void deleteEmployeeById(String employeeId) {
        String rowXpath = "//div[@role='row' and .//div[normalize-space()='" + employeeId + "']]";
        By row = By.xpath(rowXpath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(row));

        // Estrategia robusta: primero selector semántico, luego fallback al último botón de acciones de la fila.
        By semanticDeleteButton = By.xpath(
                "(" + rowXpath + ")" +
                        "//button[@title='Delete' or @aria-label='Delete' or .//i[contains(@class,'bi-trash')]]");
        By fallbackDeleteButton = By.xpath(
                "(" + rowXpath + ")" +
                        "//div[contains(@class,'oxd-table-cell-actions')]//button[last()]");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(semanticDeleteButton)).click();
        } catch (TimeoutException ignored) {
            wait.until(ExpectedConditions.elementToBeClickable(fallbackDeleteButton)).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(row));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (TimeoutException ignored) {
            // El loader puede no aparecer siempre.
        }
    }

    public boolean isEmployeeAbsentInResults(String employeeId) {
        By row = By.xpath("//div[@role='row']//div[normalize-space()='" + employeeId + "']");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(noRecordsFound),
                            ExpectedConditions.visibilityOfElementLocated(row)
                    )
            );
        } catch (TimeoutException ignored) {
            // Si no aparece ninguna señal, igual validamos por presencia real de filas.
        }
        return driver.findElements(row).isEmpty();
    }
}
