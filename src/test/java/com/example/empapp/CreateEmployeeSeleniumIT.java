package com.example.empapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateEmployeeSeleniumIT {

    private WebDriver webDriver;

    @BeforeEach
    void init() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        webDriver = new ChromeDriver();
    }

    @AfterEach
    void destroy() {
        webDriver.close();
    }

    @Test
    void testCreateEmployee() {
        webDriver.get("http://localhost:8080/emp-app");
        webDriver.findElement(By.linkText("Create employee")).click();
        String name = "John Doe" + System.currentTimeMillis();
        webDriver.findElement(By.id("create-form:name-input")).sendKeys(name);
        webDriver.findElement(By.id("create-form:save-button")).click();

        String message = webDriver.findElement(By.xpath("/html/body/ul/li")).getText();
        assertEquals("Employee has created", message);

         List<WebElement> elements = webDriver.findElements(By.xpath("//tr/td[2]"));
         assertTrue(elements.stream().map(WebElement::getText).anyMatch(s -> s.equals(name)));
    }
}
