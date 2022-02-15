package parallel;

import java.io.IOException;
import org.testng.Assert;

import com.pages.LoginPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginPageSteps {
	
	
	private LoginPage LP = new LoginPage(DriverFactory.getDriver());
	
	@Given("user navigates to Sauce demo page {string}")
	public void user_navigates_to_sauce_demo_page(String URL) {
		DriverFactory.getDriver().get(URL);
		
	}

	@When("user enters username {string} and password {string} and clicks login button")
	public void user_enters_username_and_password_and_clicks_login_button(String UserName, String Password) throws InterruptedException {
	 
		LP.loginCase(UserName, Password);
		
	}

	@Then("User Login is successful for valid login credentials")
	public void user_login_is_successful_for_valid_login_credentials() throws InterruptedException {
	  
	
		String ActualHomepageText = LP.validlogin();
		Assert.assertEquals(ActualHomepageText,"PRODUCTS");
	//	Thread.sleep(2000);
		System.out.println("Successfully logged in with valid login credentials:"+ActualHomepageText);
	}
	
	@Then("User Login is not successful for Invalid login credentials and throws errormessage {string} for scenario {string}")
	public void user_login_is_not_successful_for_Invalid_login_credentials_and_throws_errormessage_for_scenario(String expErrorMessage, String Scenario) throws InterruptedException, IOException {
	  
		//String ActualPagetitle = LP.valid();
		String ActualErrorText = LP.getLoginErrorText();
		Assert.assertEquals(ActualErrorText,expErrorMessage);
		System.out.println("Detected error for login with Invalid login credentials:"+ActualErrorText);
		LP.getScreenshot(Scenario+"Errormessagepage");
	}
	

	@Then("User Login is successful but found problems after login with Problem user credentials for scenario {string}")
	public void user_login_is_successful_but_found_problems_after_login_with_Problem_user_credentials_for_scenario(String Scenario) throws InterruptedException, IOException {
	  
		//String ActualPagetitle = LP.valid();
		boolean result = LP.probuser();
		LP.getScreenshot(Scenario+"Errorpage");
		Assert.assertTrue(result,"Detected same images for al products error for login with problem User");
		//System.out.println("Detected error for login with problem User:");
		
	}
	

	
}
