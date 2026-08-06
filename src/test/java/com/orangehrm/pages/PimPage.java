package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class PimPage extends BasePage {

    private final JavascriptExecutor js = (JavascriptExecutor) driver;

    // Menú lateral
    private final By pimMenuLink = By.xpath("//span[text()='PIM']");
    private final By menuToggle = By.xpath("//button[contains(@class, 'oxd-main-menu-button')]");

    // Botón Add de la lista de empleados
    private final By addEmployeeButton = By.xpath("//button[normalize-space()='Add']");

    // Campos del formulario de búsqueda
    private final By employeeIdSearchInput = By.xpath("//div[./label[text()='Employee Id']]/following-sibling::div//input");
    /** Botón de búsqueda del formulario de filtros (no depende del texto por idioma). */
    private final By searchButton = By.cssSelector("div.oxd-form-actions button[type='submit']");
    private final By deleteConfirmButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By loadingSpinner = By.cssSelector("div.oxd-form-loader");
    private final By noRecordsFound = By.xpath("//*[normalize-space()='No Records Found']");

    // Botón para expandir el panel de búsqueda en pantallas pequeñas
    private final By expandSearchButton = By.xpath("//button[contains(@class, 'oxd-icon-button') and .//i[contains(@class, 'bi-caret-down-fill')]]");

    public void clickPimMenu() {
        // Intentar expandir el menú si está colapsado
        try {
            WebElement toggle = wait.until(ExpectedConditions.presenceOfElementLocated(menuToggle));
            js.executeScript("arguments[0].click();", toggle);
            // Esperar un poco para que se expanda
            Thread.sleep(500);
        } catch (TimeoutException | InterruptedException ignored) {
            // El toggle no está presente o no es necesario
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(pimMenuLink));
        WebElement pimElement = driver.findElement(pimMenuLink);
        js.executeScript("arguments[0].click();", pimElement);
        wait.until(ExpectedConditions.urlContains("pim/viewEmployeeList"));
    }

    public void assertOnPimPage() {
        wait.until(ExpectedConditions.urlContains("pim/viewEmployeeList"));
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton)).click();
    }

    public void searchByEmployeeId(String employeeId) {
        expandSearchPanelIfNeeded();
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(employeeIdSearchInput));
        js.executeScript("arguments[0].scrollIntoView(true);", input);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        js.executeScript("arguments[0].value = arguments[1];", input, employeeId);
        WebElement searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(searchButton));
        js.executeScript("arguments[0].scrollIntoView(true);", searchBtn);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        js.executeScript("arguments[0].click();", searchBtn);
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

    public void expandSearchPanelIfNeeded() {
        try {
            // Intentar encontrar el botón de expandir
            WebElement expandBtn = driver.findElement(expandSearchButton);
            actions.moveToElement(expandBtn).click().perform();
            // Esperar un poco para que se expanda
            Thread.sleep(500);
        } catch (Exception ignored) {
            // Si no hay botón o no es necesario, continuar
        }
    }
}
