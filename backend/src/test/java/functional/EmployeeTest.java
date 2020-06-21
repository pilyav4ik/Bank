package functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeTest extends FunctionalTest {


    private static List<WebElement> elements = null;

    @Test
    public void addEmployee() throws InterruptedException {
        driver.get("http://localhost:3000/employees/");

        WebElement buttonNew = driver.findElement(By.className("p-menuitem-text"));
        buttonNew.click();

        elements = driver.findElements(By.tagName("form"));
        for (WebElement element : elements) {
            element.findElement(By.id("name")).sendKeys("Max");
            element.findElement(By.id("salary")).sendKeys("2500");
            element.findElement(By.id("department")).sendKeys("25");
        }
        driver.findElement(By.cssSelector("body .p-dialog .p-dialog-footer button")).click();
        Thread.sleep(3000);

        assertEquals("Success!", driver.findElement(By.className("p-growl-title")).getText());
    }

    @Test
    public void addEmployeeInfo() {
        driver.get("http://localhost:3000/employees/");

        WebElement moreInfoLink = driver.findElement(By.linkText("More info"));
        moreInfoLink.click();


        List<WebElement> selectDep = driver.findElements(By.className("p-datatable-row"));
        selectDep.get(0).click();
        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        elements = driver.findElements(By.tagName("form"));
        for (WebElement element : elements) {
            element.findElement(By.id("city")).sendKeys("New York");
            element.findElement(By.id("street")).sendKeys("442 Logan St");
            element.findElement(By.id("bankName")).sendKeys("New York Bank");
            element.findElement(By.id("cardNumber")).sendKeys("NY12345678912358");
        }
        driver.findElement(By.cssSelector("body .p-dialog .p-dialog-footer button")).click();

        assertEquals("Success!", driver.findElement(By.className("p-growl-title")).getText());
    }

    @Test
    public void editEmployee() throws InterruptedException {
        driver.get("http://localhost:3000/employees/");

        Thread.sleep(3000);
        List<WebElement> selectDep = driver.findElements(By.className("p-datatable-row"));
        selectDep.get(0).click();
        WebElement editButton = driver.findElement(By.linkText("Edit"));
        editButton.click();

        elements = driver.findElements(By.tagName("form"));
        for (WebElement element : elements) {
            element.findElement(By.id("name")).clear();
            element.findElement(By.id("name")).sendKeys("Max");
            element.findElement(By.id("salary")).clear();
            element.findElement(By.id("salary")).sendKeys("2500");
            element.findElement(By.id("department")).clear();
            element.findElement(By.id("department")).sendKeys("25");
        }
        driver.findElement(By.cssSelector("body .p-dialog .p-dialog-footer button")).click();
        assertEquals("Success!", driver.findElement(By.className("p-growl-title")).getText());
    }


    @Test
    public void deleteEmployee() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(3000);
        WebElement buttonNew = driver.findElement(By.linkText("Employees"));
        buttonNew.click();
        Thread.sleep(1000);
        List<WebElement> selectDep = driver.findElements(By.className("p-datatable-row"));
        selectDep.get(0).click();
        Thread.sleep(1000);
        WebElement deleteButton = driver.findElement(By.linkText("Delete"));
        deleteButton.click();
        assertEquals("Deleted!", driver.findElement(By.className("p-growl-title")).getText());
    }
}
