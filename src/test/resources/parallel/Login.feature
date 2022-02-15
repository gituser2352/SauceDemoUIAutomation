Feature: Sauce Demo Login Feature Validation

@LoginPositiveScenario  
Scenario Outline: Validate login for Valid user name and password credentials 
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
Then User Login is successful for valid login credentials 

Examples:
| URL 											| UserName 			| Password 		|
|https://www.saucedemo.com/ | standard_user | secret_sauce| 

@LoginNegativeScenario
Scenario Outline: Validate login for Invalid user name and or password credentials 
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
Then User Login is not successful for Invalid login credentials and throws errormessage "<ErrorMessage>" for scenario "<Scenario>" 

Examples:
| URL 											| UserName 			| Password 		| ErrorMessage																														 | Scenario    			|
|https://www.saucedemo.com/ | standard_user | 1234			  | Epic sadface: Username and password do not match any user in this service| Invalid User name|
|https://www.saucedemo.com/ | wrong_user	  | secret_sauce| Epic sadface: Username and password do not match any user in this service| Invalid password |
|https://www.saucedemo.com/ |locked_out_user| secret_sauce| Epic sadface: Sorry, this user has been locked out.											 | Locked User |

@LoginNegativeScenario
Scenario Outline: Validate error for login with Problem user but with valid username and password credentials 
Given user navigates to Sauce demo page "<URL>"
When user enters username "<UserName>" and password "<Password>" and clicks login button
Then User Login is successful but found problems after login with Problem user credentials for scenario "<Scenario>" 

Examples:
| URL 											| UserName 			| Password 		| Scenario     |
|https://www.saucedemo.com/ | problem_user | secret_sauce| Problem User |
