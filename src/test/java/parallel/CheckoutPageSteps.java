package parallel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.pages.AddtoCartPage;
import com.pages.CheckoutPage;
import com.pages.KreditVergleichPage;
import com.pages.LoginPage;
import com.qa.factory.DriverFactory;
import com.qa.util.ExcelReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutPageSteps {
	
	private CheckoutPage CP = new CheckoutPage(DriverFactory.getDriver());

	@When("user proceeds to checkout with customer info firstname {string} lastname {string} postalcode {string} and completes order process")
	public void user_proceeds_to_checkout_with_customer_info_firstname_lastname_postalcode_and_completes_order_process(String Firstname, String Lastname, String Postalcode) throws InterruptedException {
		CP.checkoutMethod();
	//	Thread.sleep(3000);
		CP.PCInfoForm(Firstname, Lastname, Postalcode);
		CP.getPaymentInfo();
	//	Thread.sleep(3000);
		CP.finishchk();

	}

	@Then("order is placed successfully")
	public void order_is_placed_successfully() throws InterruptedException {
	
	//	Thread.sleep(3000);
		String ordercompletemessage = CP.verifyordercompleted();
		Assert.assertEquals(ordercompletemessage,"CHECKOUT: COMPLETE!");
		
		String thankyouMessage = CP.verifyThankyouMessage();
		Assert.assertEquals(thankyouMessage, "THANK YOU FOR YOUR ORDER");
		System.out.println("E2E Order placed successfully");
		
	}


}
