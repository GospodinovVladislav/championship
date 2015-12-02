package championship;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class AddUserTest {

	
	@Test
	public void addNewUserTest() throws AWTException{
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://localhost:8080/championship/");
		driver.manage().window().maximize();
		
		//New Participant Test start
		driver.findElement(By.partialLinkText("Hello,")).click();
		driver.findElement(By.partialLinkText("Register")).click();
		driver.findElement(By.id("username")).sendKeys("durrauko@yahoo.com");
		driver.findElement(By.id("password")).sendKeys("vladikata");
		driver.findElement(By.id("login-submit")).click();
		
		driver.findElement(By.linkText("Participants")).click();
		driver.findElement(By.id("addParticipantButton")).click();
		
		String username = "Newest";
		
		driver.findElement(By.id("first_name")).sendKeys(username);
		driver.findElement(By.id("last_name")).sendKeys("Testr");
		driver.findElement(By.id("e-mail")).sendKeys("newest@tester.com");
		driver.findElement(By.id("register-submit")).click();
		
		List<WebElement> names = driver.findElements(By.className("firstName"));
		boolean exist = false;
		
		for(WebElement el : names){
			if(el.getText().equals(username)){
				exist = true;
				break;
			}
		}
		
		Assert.assertEquals(true,exist);
		//New participant test end
		
		
		
		//Edit participant start
		
		List<WebElement> editList = driver.findElements(By.className("editParticipant"));
		editList.get(editList.size()-1).click();
		
		String newUsername = "Newer";
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys(newUsername);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("Test");
		driver.findElement(By.id("e-mail")).clear();
		driver.findElement(By.id("e-mail")).sendKeys("edit@test.com");
		driver.findElement(By.id("edit_submit")).click();
		
		List<WebElement> edited = driver.findElements(By.className("firstName"));
		boolean existEdited = false;
		
		for(WebElement el : edited){
			if(el.getText().equals(newUsername)){
				existEdited = true;
				break;
			}
		}
		
		Assert.assertEquals(true,existEdited);
		
		//Edit participant end
		
		//Remove participant start
		
		List<WebElement> removeList = driver.findElements(By.className("deleteParticipant"));
		removeList.get(removeList.size()-1).click();
		
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_ENTER);
		
		
		List<WebElement> removedList = driver.findElements(By.className("firstName"));
		boolean removed = true;
		
		for(WebElement el : removedList){
			if(el.getText().equals(newUsername)){
				removed = false;
				break;
			}
		}
		
		
		Assert.assertEquals(true,removed);
	
		
		
		driver.close();
		
	}
	
}
