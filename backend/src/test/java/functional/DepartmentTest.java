package functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DepartmentTest extends FunctionalTest {

    @Test
    public void addDepartment() {
        driver.get("http://localhost:3000/departments/");
        WebElement buttonNew = driver.findElement(By.className("p-menuitem-text"));
        buttonNew.click();

        WebElement inputText = driver.findElement(By.id("department"));
        inputText.clear();
        inputText.click();
        inputText.sendKeys("Selenium");

        WebElement sendButton = driver.findElement(By.className("p-button"));
        sendButton.click();

        assertEquals("Success!", driver.findElement(By.className("p-growl-title")).getText());
    }

    @Test
    public void editDepartment() throws InterruptedException {
        driver.get("http://localhost:3000/departments/");
        Thread.sleep(3000);
        List<WebElement> selectDep = driver.findElements(By.className("p-datatable-row"));
        selectDep.get(0).click();
        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        WebElement inputText = driver.findElement(By.id("department"));
        inputText.clear();
        inputText.click();
        inputText.sendKeys("Selenium");

        WebElement sendButton = driver.findElement(By.className("p-button"));
        sendButton.click();

        assertEquals("18 Selenium", selectDep.get(0).getText());
    }

    @Test
    public void deleteDepartment() throws InterruptedException {
        driver.get("http://localhost:3000/departments/");
        Thread.sleep(3000);
        List<WebElement> selectDep = driver.findElements(By.className("p-datatable-row"));
        selectDep.get(1).click();
        Thread.sleep(1000);
        WebElement deleteButton = driver.findElement(By.linkText("Delete"));
        deleteButton.click();
        assertEquals("Deleted!", driver.findElement(By.className("p-growl-title")).getText());
    }
}
