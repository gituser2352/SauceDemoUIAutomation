package com.pages;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class KreditVergleichPage {
	
	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;
	private int flagj = 1;
	private double highest=0.0;
	
	//1. By Locators
	private By cookie = By.xpath("//a[contains(text(),'Alle akzeptieren')]");
	private By loanamtfield = By.cssSelector("input[inputmode='numeric']");
	private By durationDropdown = By.id("duration");
	private By Gesamttilgungelement = By.xpath("(//div[@class='monthlyRate right ng-tns-c51-0 redAttack'])[1]");
	//private By filter = By.xpath("//div[@class='openFilter ng-tns-c28-1']/span"); 
	private By filterelement1 = By.cssSelector(".openFilter span");
	private By articleelement = By.tagName("article");
	private By checkboxelements = By.cssSelector("div[class='checkboxText ng-tns-c28-1']");
	private By resultproductselements = By.cssSelector("article[id*='product_id']");
	private By Ratenpausenelement = By.cssSelector("div[class='sign cpIconQes']");
	private By NonRatenpausenelement = By.cssSelector("div[class='sign cpIconFast']");
	private By BonitatZinsenelement = By.cssSelector("span[class='maxRate ng-tns-c51-0 ng-star-inserted']");
	private By EmptyproductTextelement = By.xpath("(//div[@class='blueBar'])[2]");
	private By sortbutton = By.xpath("//span[normalize-space()='Sortieren']");
	private By sortelement1 = By.cssSelector("div.sortingBox ul li:nth-child(1)");
	private By sortelementslist = By.cssSelector("div.sortingBox ul li");
	
	//2. Constructor of the page class
	public KreditVergleichPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.actions = new Actions(driver);
	}
	
	//3. page actions
	public void acceptCookie() throws InterruptedException {
		//Accept cookie
		
		WebElement ele = driver.findElement(cookie);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(cookie));
		Thread.sleep(2000);
		JSClick( ele);
		
	}

	public void enterloanamount( String loanamount) {
		
		//Enter loan amount in 'Wie viel?' box
		//actions = new Actions(driver);
		WebElement elem = driver.findElement(loanamtfield);
		actions.moveToElement(elem).click().sendKeys(Keys.BACK_SPACE).build().perform();
		elem.sendKeys(loanamount);
		//System.out.println("page assertion successful");
		
	}

	public void dropDownSelection(String dropdownId, String option) throws InterruptedException {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("" + dropdownId + "")));
		driver.findElement(By.id("" + dropdownId + "")).click();
		Thread.sleep(2000);
		List<WebElement> listElements = driver
				.findElements(By.xpath("//div[@id='" + dropdownId + "']//following-sibling::ul//li"));
		int sizeOfList = listElements.size();
		for (int i = 0; i <= sizeOfList - 1; i++) {
			if (listElements.get(i).getText().contains(option)) {
				actions.moveToElement(listElements.get(i)).click().build().perform();
				//listElements.get(i).click();
				break;
			}
		}
	}

	public  String getexpectedmonatliche() throws InterruptedException {
		
		Thread.sleep(2000);
		String durationboxmessage = driver.findElement(durationDropdown).getText();
		System.out.println(durationboxmessage);
		String[] instalamt1 = durationboxmessage.split(" ");
		String expmonatlichrate = instalamt1[2];
		System.out.println("expected monatliche in method-"+expmonatlichrate);
		return expmonatlichrate;
	}

	public  String getActualmonatliche() throws InterruptedException {
	
		String monatlichrate1 = driver.findElement(Gesamttilgungelement).getText();
		System.out.println("actual monthly rate:"+ monatlichrate1);
		String[] monatlichrate2 = monatlichrate1.split(" ");
		String actualmonatlichrate = monatlichrate2[0];
		System.out.println("monthly rate trimmed:"+ actualmonatlichrate);
		
		return actualmonatlichrate;
	}
		
	public void clickfilter() {
		
		driver.findElement(filterelement1).click();
		
	}
	
	public int getcountofenabledfieldsinFilter() {
		
		return driver.findElements(checkboxelements).size();
	}

	
	public void filterSelection(String filterValue) throws InterruptedException {
		//System.out.println("flagj value is :"+flagj);
		
		if (flagj == 1) {
			driver.findElement(filterelement1).click();
		}
		
		String xpath = "//div[@class=\"filtersContent ng-tns-c28-1 ng-trigger ng-trigger-enterTrigger ng-star-inserted\"]//li//div[text()=\""
				+ filterValue + "\"]";
		Thread.sleep(4000);
		String checkClassName = driver.findElement(By.xpath(xpath + "/..")).getAttribute("class");
		if (checkClassName.contains("checked")) {
			System.out.println("Already Checked");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//		actions.moveToElement(driver.findElement(By.xpath(xpath))).click().build().perform();
			JSClick(driver.findElement(By.xpath(xpath)));

		} else {
			System.out.println("UnChecked");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			JSClick(driver.findElement(By.xpath(xpath)));
		}
		++flagj;
	}

	public boolean verifyFilter() throws InterruptedException {
		boolean validationresult = false;
		wait.until(ExpectedConditions.visibilityOfElementLocated(articleelement));
		Thread.sleep(2000);
		List<WebElement> articles = driver.findElements(articleelement);
		int a = articles.size();
		System.out.println("No.of articles "+a);
		for (int i = 1; i < a; i++) {
			try {
				String filterValue1 = "//article[" + i
						+ "]//div[@class=\"box properties ng-tns-c51-0 ng-star-inserted\"]//app-product-bullets//ul//li[1]";
				String filterValue2 = "//article[" + i
						+ "]//div[@class=\"box properties ng-tns-c51-0 ng-star-inserted\"]//app-product-bullets//ul//li[2]";
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filterValue1)));
				String filterText = driver.findElement(By.xpath(filterValue1)).getText();
				String filterClass = driver.findElement(By.xpath(filterValue1)).getAttribute("class");
				
				String filterText2 = driver.findElement(By.xpath(filterValue2)).getText();
				String filterClass2 = driver.findElement(By.xpath(filterValue2)).getAttribute("class");
				if (filterClass.equalsIgnoreCase("red")) {
	
				} else if (filterClass2.equalsIgnoreCase("red")) {
	
				}else if(filterText2.equalsIgnoreCase("kostenlose Gesamttilgung")) {
					if(i==(a-1)) {
						validationresult = true;
					}
				}
				
				JSMovetoElement(driver.findElement(By.xpath(filterValue1)));
					
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Message" + e.getMessage());
				System.out.println("Cause" + e.getCause());
			}
		}
		actions.sendKeys(Keys.HOME).build().perform();
		
		return validationresult;
	}
	
	//getcountofenabledfieldsinFilter

	public int getTotalProductsdisplayed() {
		
		return driver.findElements(resultproductselements).size();
		
	}
	
	public int getTotalRatenPausenProductsdisplayed() {
		
		return driver.findElements(Ratenpausenelement).size();
	}
	
	public int getTotalNonRatenPausenProductsdisplayed() {
		
		return driver.findElements(NonRatenpausenelement).size();
	}
	
	public int getBonitätsunabhängigeZinsenProductsdisplayedcount() {
		
		return driver.findElements(BonitatZinsenelement).size();
		
	}	
	
	public String getBonitätsunabhängigeZinsenelementText() {
		
		return driver.findElement(BonitatZinsenelement).getText();
		
	}	
	
	public String getEmptyfieldserrormessage() {
		
		return driver.findElement(EmptyproductTextelement).getText();
	}
	
	
	
	public void sortSelection(String sort) throws InterruptedException {
					
		wait.until(ExpectedConditions.visibilityOfElementLocated(sortbutton));
		Thread.sleep(2000);
		driver.findElement(sortbutton).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(sortelement1));
		Thread.sleep(2000);

		List<WebElement> sortElements = driver.findElements(sortelementslist);
		for (int i = 1; i <= sortElements.size(); ++i) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector("div.sortingBox ul li:nth-child(" + i + ") div p")));
			String sortElement = driver.findElement(By.cssSelector("div.sortingBox ul li:nth-child(" + i + ") div p"))
					.getText();
			if (sortElement.equalsIgnoreCase(sort)) {
				JSClick(driver.findElement(By.cssSelector("div.sortingBox ul li:nth-child(" + i + ")")));

				break;
			}
			
			
		}

	}
	
	public boolean verifySort() throws InterruptedException {
		boolean sortResult = true;
		wait.until(ExpectedConditions.visibilityOfElementLocated(articleelement));
		Thread.sleep(2000);
		
		List<WebElement> articles = driver.findElements(articleelement);
		int a = articles.size();
		System.out.println(a);
		for (int i = 1; i < a; i++) 
			{
			String filterValue1 = "//article[" + i
					+ "]//div[@class=\"box properties ng-tns-c51-0 ng-star-inserted\"]//app-product-bullets//ul//li[1]";
			String rating =driver.findElement(By.xpath("//section[@class='products']//article["+i+"]//span[@class='blue textLink']")).getText();
			String[] sr= rating.split(" ");
			String sr1= sr[0].trim();
			double f=Double.parseDouble(sr1); 
			if(i==1) {
				highest =f;
			}
						if(highest*1000<f) {
							sortResult = false;
				}
						
			JSMovetoElement(driver.findElement(By.xpath(filterValue1)));

			}
		actions.sendKeys(Keys.HOME).build().perform();
		return sortResult;
	}
	
	
	public String getPageTitle() {
		return driver.getTitle();
	}

//add-on methods

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
	public void JSMovetoElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void JSClick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
}
