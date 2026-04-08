package com.orangehrm.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public final class DriverManager {

    public static final String BROWSER_PROPERTY = "browser";
    public static final String BROWSER_ENV = "BROWSER";

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<Browser> CURRENT_BROWSER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver() {
        createDriver(resolveBrowserFromEnvironment());
    }

    public static void createDriver(Browser browser) {
        if (DRIVER.get() != null) {
            return;
        }
        WebDriver driver = instantiate(browser);
        driver.manage().window().maximize();
        DRIVER.set(driver);
        CURRENT_BROWSER.set(browser);
    }

    public static Browser getCurrentBrowser() {
        Browser b = CURRENT_BROWSER.get();
        if (b == null) {
            throw new IllegalStateException("No hay navegador activo.");
        }
        return b;
    }

    private static Browser resolveBrowserFromEnvironment() {
        String fromProperty = System.getProperty(BROWSER_PROPERTY);
        if (fromProperty != null && !fromProperty.isBlank()) {
            return Browser.fromName(fromProperty);
        }
        String fromEnv = System.getenv(BROWSER_ENV);
        return Browser.fromName(fromEnv);
    }

    private static boolean isHeadless() {
        return "true".equalsIgnoreCase(System.getProperty("headless"))
                || "true".equalsIgnoreCase(System.getenv("HEADLESS"))
                || System.getenv("CI") != null;
    }

    private static WebDriver instantiate(Browser browser) {
        boolean headless = isHeadless();
        return switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                }
                yield new ChromeDriver(options);
            }
            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
                }
                yield new EdgeDriver(options);
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                yield new FirefoxDriver(options);
            }
            case SAFARI -> {
                WebDriverManager.safaridriver().setup();
                yield new SafariDriver();
            }
        };
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver no inicializado. Ejecuta los hooks @Before de Cucumber.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
        CURRENT_BROWSER.remove();
    }
}
