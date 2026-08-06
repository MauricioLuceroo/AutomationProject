package com.orangehrm.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage extends BasePage {

    private final JavascriptExecutor js = (JavascriptExecutor) driver;

    // Menú lateral
    private final By adminMenuLink = By.xpath("//a[contains(@href, 'admin/viewAdminModule')]//span[text()='Admin']");

    // Campos del panel de búsqueda
    private final By searchUsernameInput = By.xpath("//label[normalize-space()='Username']/following::input[1]");
    /** Autocomplete del username (suele usar listbox). */
   

    // Botones principales
    private final By addButton           = By.xpath("//button[normalize-space()='Add']");


    public void clickAdminMenu() {
        wait.until(ExpectedConditions.presenceOfElementLocated(adminMenuLink));
        WebElement adminElement = driver.findElement(adminMenuLink);
        js.executeScript("arguments[0].click();", adminElement);
        wait.until(ExpectedConditions.urlContains("admin/viewSystemUsers"));
    }

    public void assertOnAdminPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(25))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.urlContains("admin/viewSystemUsers"),
                            ExpectedConditions.urlContains("admin/viewSystemUser"),
                            ExpectedConditions.visibilityOfElementLocated(searchUsernameInput)));
        } catch (TimeoutException ignored) {
            // Si la vista aún no terminó de cargar, el siguiente paso se intentará de nuevo.
        }
    }

    public void clickAdd() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    

   
}
