package ru.Alexander.NauJava;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginLogoutSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    void testLoginAndLogoutScenario() {
        driver.get(baseUrl + "/login");

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));

        usernameInput.sendKeys("user1");
        passwordInput.sendKeys("123");

        driver.findElement(By.id("login-btn")).click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/web/tasks"), "Не удалось войти. Текущий URL: " + currentUrl);

        WebElement logoutLink = driver.findElement(By.linkText("Выйти из системы"));
        logoutLink.click();

        String urlAfterLogout = driver.getCurrentUrl();
        assertTrue(urlAfterLogout.contains("/login"), "Не удалось выйти. Текущий URL: " + urlAfterLogout);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
