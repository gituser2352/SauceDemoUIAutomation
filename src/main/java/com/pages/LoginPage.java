package com.pages;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


import sauce.base.BaseClass;



public class LoginPage extends BaseClass{
	
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;
	
	//1. By Locators
	private By username = By.cssSelector("input[id='user-name']");
	private By password = By.cssSelector("input[id='password']");
	private By logintbtn = By.cssSelector("#login-button");
	private By productsheaderelement = By.cssSelector("span[class='title']");
	private By errorh3 = By.tagName("h3");
	private By img1 = By.cssSelector("#inventory_container .inventory_item:nth-child(1) img");
	private By img2 = By.cssSelector("#inventory_container .inventory_item:nth-child(2) img");
	
	//2. Constructor of the page class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.actions = new Actions(driver);
		
	}
	 
	//3. page actions
	
	public void loginCase(String id, String pwd) throws InterruptedException {

		sendKeysToWebElement(driver,driver.findElement(username),id);
		sendKeysToWebElement(driver,driver.findElement(password),pwd);
		waitAndClickElement(driver,driver.findElement(logintbtn));
		
	}
	
	public String getLoginErrorText() {
		
		return (driver.findElement(errorh3).getText());

	}

	public boolean probuser() {
		String imgLink1 = driver.findElement(img1).getAttribute("src");
		String imgLink2 = driver.findElement(img2).getAttribute("src");
		if(imgLink1.equalsIgnoreCase(imgLink2)) {
		
			return false;
		}
		return true;
	}
	
	public String validlogin() {
			
		
	return(driver.findElement(productsheaderelement).getText());
	
	}
	
	public void logOut() {
		try {if (driver.getTitle().equalsIgnoreCase("Swag Labs")) {
			waitAndClickElement(driver, driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")));
			waitAndClickElement(driver, driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")));
		}
		}
		catch(Exception e) {
			e.getCause();
		}
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public void getScreenshot(String screenShotName) throws IOException {
		
		//date format for screenshot
		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy h-m-s");
		Date date = new Date();
		String workingDir = System.getProperty("user.dir");
		String screenshotPath=workingDir+"\\Screenshots\\"+screenShotName+dateFormat.format(date)+".png";
		
		//Capture Screenshot
		TakesScreenshot takescreenshot = (TakesScreenshot) driver;
		File source = takescreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File(screenshotPath));
											  
	}

}
