package sauce.base;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;





public class BaseClass {
	public static WebDriver driver;
	public static Properties prop;
	public Wait<WebDriver> tempWait;

	public BaseClass() {

		this.driver = driver;
	
	}
	

	public void waitAndClickElement(WebDriver driver, WebElement element) {

		tempWait = new WebDriverWait(driver, Duration.ofSeconds(20));
		boolean clicked = false;
		int attempts = 0;
		while (!clicked && attempts < 3) {
			try {
				tempWait.until(ExpectedConditions.elementToBeClickable(element)).click();
			//	System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
				clicked = true;
			} catch (Exception e) {
				System.out.println("Unable to wait and click on WebElement, Exception: " + e.getMessage());
				System.out.println("Click attempts " + attempts + " time");
				attempts++;
			}
		}
		if (attempts >= 9) {
		//	Assert.fail("Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
			System.out.println("Unable to click element");
		}
	}

	public void sendKeysToWebElement(WebDriver driver, WebElement element, String textToSend) {

		try {
			element.clear();
			element.sendKeys(textToSend);
		//	System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<"
		//			+ element.toString() + ">");
		} catch (Exception e) {
			System.out.println("Unable to locate WebElement: " + "<" + element.toString()
					+ "> and send the following keys: " + textToSend);
		//	Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
		}
	}
	
	public void getScreenshot(WebDriver driver, String screenShotName) throws IOException {
		
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

	//add-on methods

		public void JSClick(WebElement element) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		}
}
