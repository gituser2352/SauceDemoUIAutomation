package com.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sauce.base.BaseClass;



public class AddtoCartPage extends BaseClass{
	
	private WebDriver driver;
	private int clickedCart = 0;
	private Actions actions;
	private WebDriverWait wait;
	
	
	//1. By Locators	
	private By addToCart = By.cssSelector("button[class='btn btn_primary btn_small btn_inventory']");
	private By cartCount = By.cssSelector("#shopping_cart_container .shopping_cart_badge");
	private By btnAddToCart = By.cssSelector("#shopping_cart_container .shopping_cart_badge");
	private By productelement = By.cssSelector("#inventory_container .inventory_item .inventory_item_name");
	private By cartItems = By.xpath("//div[@class='cart_item']");
	
	
	//2. Constructor of the page class
	public AddtoCartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.actions = new Actions(driver);
		
	}
	 
	//3. page actions
	
	public void selectProduct(String product) {
		List<WebElement> sauceProducts = driver.findElements(productelement);
		for(int i=1; i<sauceProducts.size()-1; ++i) {
			String txtProduct = driver.findElement(By.cssSelector("#inventory_container .inventory_item:nth-child("+i+") .inventory_item_name")).getText();
			if(txtProduct.equalsIgnoreCase(product)) {
			//	System.out.println(i+" "+product+" "+ txtProduct  );
				waitAndClickElement(driver, sauceProducts.get(i));
				break;
			}
		}
		
	}
	
	public void addtocart() {
		try
		{if(driver.findElement(addToCart).isDisplayed()) {
			waitAndClickElement(driver, driver.findElement(addToCart));
			++clickedCart;
		}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Already in cart");

		}
		
	}
	public boolean verifyCart(){
	String count = driver.findElement(cartCount).getText();
	int i = Integer.parseInt(count);
	if(clickedCart==i) {
		return true;
	}
	
	return false;
	}
	
	
	public void additem(String product) {
		List<WebElement> sauceProducts = driver.findElements(productelement);
		for(int i=1; i<=sauceProducts.size(); i++) {
			String txtProduct = driver.findElement(By.cssSelector("#inventory_container .inventory_item:nth-child("+i+") .inventory_item_name")).getText();
			if(txtProduct.equalsIgnoreCase(product)) {
				//System.out.println(i+" "+product+" "+ txtProduct  );
				String itemxpath = "(//div[@class='inventory_item'])["+i+"]//div[2]/button";
				WebElement ele = driver.findElement(By.xpath(itemxpath));
				JSMovetoElement(ele);
				waitAndClickElement(driver, ele);
				break;
			}
		}
		
		 actions.sendKeys(Keys.HOME).build().perform();
	}
	
	public void clickcartbutton() {
		driver.findElement(cartCount).click();
	}
	//verifyCartlist
	public int verifyCartItem(String product) {
		int k=0;
		List<WebElement> cartProducts = driver.findElements(cartItems);
		System.out.println("Total items in the cart are :"+cartProducts.size());
		for(int i=1; i<=cartProducts.size(); i++) {
			String ele = "(//div[@class='cart_item'])["+i+"]//div[@class='inventory_item_name']";
			String txtProduct = driver.findElement(By.xpath(ele)).getText();
			if(txtProduct.equalsIgnoreCase(product)) {
				//System.out.println(i+" "+product+" "+ txtProduct  );
				//String itemxpath = "(//div[@class='inventory_item'])["+i+"]//div[2]/button";
				WebElement ele1 = driver.findElement(By.xpath(ele));
				JSMovetoElement(ele1);
			
				return k+1;
			
			}
		}
		
		 actions.sendKeys(Keys.HOME).build().perform();
	return k;
	}
	
	public void movetoHome() {
		 actions.sendKeys(Keys.HOME).build().perform();
	}
	
	public void JSMovetoElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
}
