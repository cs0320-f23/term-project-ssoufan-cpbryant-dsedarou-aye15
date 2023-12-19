# Brown Bites

## Project Details:

In this project, we created a functional meal planner which is based off CSVs of the menus for the current week.
We have menus for both the Vdub and the Ratty and will use the options from there along with the user inputted
calorie limit and any restrictions to develop a meal plan for breakfast, lunch, and dinner for the current day. We
also have a community section which is fully mocked, but if we had more time we would plan to implement firebase
authentication and allow users to sign in and post. The last component for the project is a menu viewer which will
pop up and allow us to view all the special items for the week.

Team Members: Annie Ye (aye15), Shadi Soufan (ssoufan), Daniel Sedarous (dsedarou), Channing Bryant (cpbryant)<br />
Contributors: none <br />
Estimated time to complete: 40 hours<br />
https://github.com/cs0320-f23/term-project-ssoufan-cpbryant-dsedarou-aye15.git

## Design Choices:

This project contains 10 classes, 2 interfaces, 2 exception classes, and 2 records.

- The Main class serves as the entry point for a Spark web application. It initializes the
  application by setting up a Spark server to listen on port 2023. The class defines route handlers
  for several endpoints by creating an object of MenuHandler and DayHandler.
- MenuHandler class acts as a central component for managing search-related interactions within the CSV data. It   
  ensures proper handling of HTTP requests, performs searches using the Searcher class, and constructs appropriate 
  JSON responses to communicate the results or errors back to the client. The class plays a crucial role in 
  enhancing the user experience by providing seamless search functionalities within the web application. This 
  class ensures that all the data is pulled out from the CSVs and properly serialized so it can be accessed in 
  our frontend.
- The DayHandler class is responsible for handling requests to view the Day of a CSV file.
  It takes a CSVData object as a dependency, which presumably contains information about the loaded
  CSV file. It then constructs a JSON response containing the CSV data or an error message in case of failure. It 
  creates a HashMap to store and display the Days of the CSV filed passed in. 
- The SearchHandler class handles HTTP requests for CSV data searches in a web app. It extracts
  parameters, conducts searches by creating a Searcher object, and produces JSON responses. Success
  or failure responses are structured using inner records. This class plays a vital role in managing
  CSV data search operations.
- The App.tsx class is our highest level class which contains our title and our high level Display class.
- The Display.tsx class is a high level class which we use to display all of our tsx classes and where we chose
  to create al of our props. This class is used solely for the purpose of properly displaying everything in our 
  App class as well as sharing props across classes. 
- Our Calendar class handles displaying the meal plan on the table.
- Our Selector class handles calculating a meal plan with the user query for the calorie limit. It also handles  
  the frontend selection for the restrictions and ensures that the outputted meal plan is in line with the 
  restrictions and calorie limit which is passed in/selected. 
- Our Popup class handles the popup menu options which displays the specialty items for each day of the week.
- Our Generate class handles when the Generate button is clicked, the menu day is displayed so the user knows 
  which day they are meal planning for.
- Our CommunityInput, ControlledCommunityInput, and CommunityHistory classes all manage mocking the community 
  section. The user can make posts and the user will the the post they just submitted displayed in the website's 
  history. 

## Errors/Bugs:

Our Breakfast section will sometimes be overloaded with options. When the same button is clicked for a different calorie count sequentially the table will not update. For example inputting a 2000 calorie count, clicking the none restriction, then updating the calorie count to 3000, and clicking the none restriction again.

## Tests:

Our Testing class handles integration testing unit testing, fuzz testing, and mock testing. It tests the logic of the program
such as:

- ensuring that we get the SuccessResponse and FailureResponse when necessary after calling each
  Handler
- ensuring that the correct data is being returned as a response
- the correct response code is being returned depending on if the query will produce an error
- the user is able to accurately view their meal plan in a table in the frontend
- the user is able to post into the community section 
- the user is accurately able to generate the current day based on their file when they hit the generate button.
- the user is able to view the popup menu in the frontend.
- the table will not appear when the user inputs an incorrect calorie input.

## How to:

To run the backend tests, go into the directory test -> java -> edu.brown.cs.student -> and hit the
run button for the respective tests.<br />
To run the program, go into Main input your proper filepath for that day and hit run. Once the link is provided, click on the link. <br />
Then go into the frontend and run npm install to download node modules then do npm start and click the link to view the frontend<br />
When in the frontend, input a calorie limit in the input box then select a restriction, once you select a restriction, you will see the meal plan for that day appear.<br />
To run the frontend tests run npm install playwright to install playwright then run npx playwright test to run the tests. <br />

