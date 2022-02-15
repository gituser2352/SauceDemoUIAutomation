Feature: Add to Cart Feature Validation

@AddtoCart
Scenario Outline: Validate user is able to navigate to secific product page and add product to cart  
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
And user navigates to product details page of  desired product "<Product>" adds it to cart 
Then correct product "<Product>" is added to cart for scenario "<Scenario>" 

Examples:
| URL 											| UserName 			| Password 		| Product  							 | Scenario				 						  |
|https://www.saucedemo.com/ | standard_user | secret_sauce|Sauce Labs Bolt T-Shirt | Addtocartonproductdetailspage| 


@AddtoCart 
Scenario Outline: Validate user is able to add multiple items to cart and verify cart list  
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
And user adds products to cart from given sheetname "<SheetName>" and rownumber <RowNumber> 
Then correct products are added to cart list from given sheetname "<SheetName>" and rownumber <RowNumber> for scenario "<Scenario>" 

Examples:
| URL 											| UserName 			| Password 		| SheetName | RowNumber | Scenario			 |
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 0				  |Additemstocarto | 
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 1				  |Additemstocarto |
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 2				  |Additemstocarto |
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 3				  |Additemstocarto |
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 4				  |Additemstocarto |