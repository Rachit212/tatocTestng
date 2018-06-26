package com.qait.tatoc.MavenTatocTesting;

import java.awt.Desktop.Action;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {

	WebDriver driver;

	@BeforeTest
	public void init() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void Step_01_EnterUrl() throws InterruptedException {
		driver.get("http://10.0.1.86/tatoc");

	}

	@Test
	public void Step_02_OpenLink() {
		// driver.findElement(By.cssSelector("a")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.findElement(By.xpath("/html/body/div/div[2]/a[1]")).click();
		driver.findElement(By.xpath("//a[text()='Basic Course']")).click();
	}

	@Test
	public void Step_03_GreenBox() {
		driver.findElement(By.className("greenbox")).click();
	}

	@Test
	public void Step_04_Frame_Dungeon() {

		 WebDriverWait wait=new WebDriverWait(driver,10);
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main")));
		 String
		 actualAnswer=driver.findElement(By.xpath("//div[text()='Box 1']")).getAttribute("class");
		 Boolean condition=true;
		 while(condition) {
		 driver.findElement(By.xpath("//a[text()='Repaint Box 2']")).click();
		 WebElement childDiv=driver.findElement(By.id("child"));
		 driver.switchTo().frame(childDiv);
		 String
		 expectedAnswer=driver.findElement(By.id("answer")).getAttribute("class");
		 driver.switchTo().parentFrame();
		 if(actualAnswer.equals(expectedAnswer)) {
		 condition=false;
		 }
		 }
		 driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		 driver.switchTo().defaultContent();
		
	}
	@Test
	public void Step_05_Drag_Box() {

		WebElement from = driver.findElement(By.id("dragbox"));
		WebElement to = driver.findElement(By.id("dropbox"));
		Actions act = new Actions(driver);
		act.dragAndDrop(from, to).build().perform();
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();

	}
		 @Test
	public void Step_06_Pop_Up_Window() {
			 String mainwindow = driver.getWindowHandle();
			 driver.findElement(By.xpath("//a[text()='Launch Popup Window']")).click();
			 for(String nextWindow : driver.getWindowHandles() )
			 {
			 	driver.switchTo().window(nextWindow);
			 }
			 driver.findElement(By.id("name")).sendKeys("hello");
			 driver.findElement(By.id("submit")).click();
			 for(String nextWindow : driver.getWindowHandles())
			 {
			 	driver.switchTo().window(mainwindow);
			 }
			 driver.findElement(By.xpath("//a[text()='Proceed']")).click();

			
	}
	


@Test
public void Step_07_Token_Generate() {
	driver.findElement(By.xpath("//a[text()='Generate Token']")).click();
	String d=driver.findElement(By.id("token")).getText();
	String[] tokenValue = d.split("\\s");//Value splitted into two [0]:Token: [1]: dfsffd23hj
	String token = tokenValue[1]; 
	Cookie cookie = new Cookie("Token", token);
	driver.manage().addCookie(cookie);
	driver.findElement(By.xpath("//a[text()='Generate Token']")).click();
	System.out.println(cookie);
	driver.quit();
}

@AfterTest
	public void after() {
		driver.close();
	}


}