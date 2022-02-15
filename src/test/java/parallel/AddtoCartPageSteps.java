package parallel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import com.pages.AddtoCartPage;
import com.qa.factory.DriverFactory;
import com.qa.util.ExcelReader;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddtoCartPageSteps {
	
	private String itemstoaddcount;
	private int count;
	private String itemtoadd;
	
	private AddtoCartPage AP = new AddtoCartPage(DriverFactory.getDriver());
	private ExcelReader reader = new ExcelReader();
	private String workingDir = System.getProperty("user.dir");
	private String Excelinppath =workingDir+"/src/test/resources/testData/Saucetestdata.xlsx";
//	private String Excelinppath =workingDir+"\\src\\test\\resources\\testData\\pbtestdata.xlsx";
	
	
	@When("user navigates to product details page of  desired product {string} adds it to cart")
	public void user_navigates_to_product_details_page_of_desired_product_adds_it_to_cart(String addProduct) throws InterruptedException {
	 
		AP.selectProduct(addProduct);
		Thread.sleep(2000);
		AP.addtocart();
	}

	
	@Then("correct product {string} is added to cart for scenario {string}")
	public void correct_product_is_added_to_cart_for_scenario(String addProduct, String Scenario) {
	  boolean result = AP.verifyCart();
	  Assert.assertTrue(result);
	  System.out.println("Successfully added item to the cart");
	  AP.addtocart();
	}

	@When("user adds products to cart from given sheetname {string} and rownumber {int}")
	public void user_adds_products_to_cart_from_given_sheetname_and_rownumber(String sheetName, Integer rowNumber) throws InvalidFormatException, IOException, InterruptedException {
		List<Map<String,String>> testData = reader.getData(Excelinppath, sheetName);
		 itemstoaddcount = testData.get(rowNumber).get("Noofitemstoadd");
		 int count = Integer.parseInt(itemstoaddcount);
		 System.out.println("no. of items to select :"+count);
		 for(int i=0;i<count;i++) {
			 int j =i+1;
			 String item = "item"+j;
			// System.out.println("Item number is: "+item);
			 itemtoadd = testData.get(rowNumber).get(item);
			//System.out.println("Item from excel is:"+itemtoadd);
			 AP.additem(itemtoadd);
			// Thread.sleep(2000);
		 }

		 AP.movetoHome();


		
	}
			
	@Then("correct products are added to cart list from given sheetname {string} and rownumber {int} for scenario {string}")
	public void correct_products_are_added_to_cart_list_from_given_sheetname_and_rownumber_for_scenario(String sheetName, Integer rowNumber, String Scenario) throws InterruptedException, InvalidFormatException, IOException {
	   int actualcount =0;
		List<Map<String,String>> testData = reader.getData(Excelinppath, sheetName);
		 itemstoaddcount = testData.get(rowNumber).get("Noofitemstoadd");
		 int count = Integer.parseInt(itemstoaddcount);
		 System.out.println("no. of items to select :"+count);
		//click on cart
		 Thread.sleep(2000);
		 AP.clickcartbutton();
		 //validate each item added in the cart 
		 for(int i=0;i<count;i++) {
			 int j =i+1;
			 String item = "item"+j;
			// System.out.println("Item number is: "+item);
			 itemtoadd = testData.get(rowNumber).get(item);
		//	 System.out.println("Item from excel is:"+itemtoadd);
		//	 AP.additem(itemtoadd);
			 actualcount= actualcount+AP.verifyCartItem(itemtoadd);
			// Thread.sleep(3000);
		 }
		 
		 Assert.assertEquals(actualcount, count,"Total items in cart doesn't match to total items added");
		System.out.println("All items added are present in cart");
	

	}

}
