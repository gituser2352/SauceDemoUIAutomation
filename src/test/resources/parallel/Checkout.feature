Feature: Checkout and place order Feature Validation

@Checkout(E2E) 
Scenario Outline: Validate user is able to Checkout and place order- Place order E2E flow  
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
And user adds products to cart from given sheetname "<SheetName>" and rownumber <RowNumber> 
And correct products are added to cart list from given sheetname "<SheetName>" and rownumber <RowNumber> for scenario "<Scenario>" 
And user proceeds to checkout with customer info firstname "<FirstName>" lastname "<LastName>" postalcode "<PostalCode>" and completes order process
Then order is placed successfully
Examples:
| URL 											| UserName 			| Password 		| SheetName | RowNumber| FirstName| LastName| PostalCode | Scenario			 |
|https://www.saucedemo.com/ | standard_user | secret_sauce| Saucedata | 0				 | jackson  | Peter   | 80935			 |Checkoutandcompleteorder | 



