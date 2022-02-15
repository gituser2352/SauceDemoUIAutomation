package com.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


import sauce.base.BaseClass;



public class CheckoutPage extends BaseClass{
	
	private WebDriver driver;
//	public Wait<WebDriver> tempWait;
	private int clickedCart = 0;
	
	//--
	private Actions actions;
	private WebDriverWait wait;
	
	
	//1. By Locators
	private By chkoutLink = By.cssSelector(".shopping_cart_link");
	private By chkoutbtn = By.cssSelector(".checkout_button");
	private By firstName = By.id("first-name");
	private By lastName = By.id("last-name");
	private By postalCode = By.id("postal-code");
	private By cont = By.id("continue");
	private By paymentInfo = By.xpath("//div[normalize-space()='Payment Information:']/following-sibling::div[1]");	
	private By shipmentInfo = By.xpath("//div[normalize-space()='Shipping Information:']/following-sibling::div[1]");
	private By subTotal = By.cssSelector(".summary_subtotal_label");
	private By tax = By.cssSelector(".summary_tax_label");
	private By total = By.cssSelector(".summary_total_label");
	private By finish = By.id("finish");
	private By titleCheckout = By.cssSelector(".title");
	private By thankYou = By.cssSelector(".complete-header");

	
	//2. Constructor of the page class
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.actions = new Actions(driver);
		
	}
	 
	//3. page actions	
	public void checkoutMethod() {
	//	waitAndClickElement(chkoutLink);
		waitAndClickElement(driver, driver.findElement(chkoutbtn));
	}
	
	public void PCInfoForm(String fname,String lname,String pcode)
	{
		sendKeysToWebElement(driver, driver.findElement(firstName), fname);
		sendKeysToWebElement(driver, driver.findElement(lastName), lname);
		sendKeysToWebElement(driver, driver.findElement(postalCode), pcode);
		waitAndClickElement(driver, driver.findElement(cont));
	}
	
	public void  getPaymentInfo() {
		System.out.println("Payment information :"+driver.findElement(paymentInfo).getText());
		System.out.println("Shipment information :"+driver.findElement(shipmentInfo).getText());
		System.out.println(driver.findElement(subTotal).getText());
		System.out.println(driver.findElement(tax).getText());
		System.out.println(driver.findElement(total).getText());
	}
	public void finishchk() {
		waitAndClickElement(driver, driver.findElement(finish));
		
	}
	public String verifyordercompleted() {
		
		return driver.findElement(titleCheckout).getText();
		
	}
	public String verifyThankyouMessage() {
		 actions.sendKeys(Keys.HOME).build().perform();
		return driver.findElement(thankYou).getText();
	
	}
	
	

	
}
