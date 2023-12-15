package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.Handlers.MenuHandler;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import spark.Spark;

public class RealTesting {

  private final Type mapStringObject =
      Types.newParameterizedType(Map.class, String.class, Object.class);
  private JsonAdapter<Map<String, Object>> adapter;

  @BeforeAll
  public static void setup_before_everything() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  @BeforeEach
  public void setup() {
    Spark.get("menu", new MenuHandler("/Users/channingbryant/Desktop/cs320/term-project-ssoufan-cpbryant-dsedarou-aye15/backend/data/MondayMenu.csv"));
    Spark.init();
    Spark.awaitInitialization();


    Moshi moshi = new Moshi.Builder().build();
    adapter = moshi.adapter(mapStringObject);
  }


  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("menu");
    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  /**
   * Use this to build the URL connection without sending the request in
   *
   * @param apiCall- The queries we would like to input for our search
   * @return the connection after searching
   * @throws IOException - Error with any connectivity
   */
  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection (but don't actually send the request yet)
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();

    // The default method is "GET", which is what we're using here.
    // If we were using "POST", we'd need to say so.
    // clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  /**
   * This method tests if the area query parameter is not inputted
   *
   * @throws IOException
   */
  @Test
  public void testNoAreaParameter() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?feufiwef");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals(body.get("result"),"failure");
//    assertEquals("Please input a proper query, EX: [http://localhost:2025/menu?restriction= "
//            + "Vegan, Vegetarian, Halal, Gluten or leave it empty for no restriction]",
//        body.get("error_description"));
  }




  /**
   * This method tests if failure response is emitted when a false restriction is given
   * @throws IOException
   */
  @Test
  public void testInvalidQueryInput() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Pizza");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("failure", body.get("result"));
//    assertEquals("Please input a proper query, EX: [http://localhost:2025/menu?restriction= "
//            + "Vegan, Vegetarian, Halal, Gluten or leave it empty for no restriction]",
//        body.get("error_description"));
  }




  /**
   * This method tests a valid restriction search for wheat
   * @throws IOException
   */
  @Test
  public void testValidVeganSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Vegan");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals(
        "{item: 9={Item=Ratty Cuban Style Black Beans, Serving size=1 cup, Meal=Dinner, Calories=200}, item: 18={Item=VDub Vegan Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=150}, item: 19={Item=VDub Vegan Buffalo Chicken Scallopini, Serving size=3 oz, Meal=Lunch, Calories=120}, item: 20={Item=VDub Vegan Bbq Jackfruit, Serving size=1/2 cup, Meal=Lunch, Calories=90}, item: 23={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 24={Item=VDub Roasted Butternut Squash, Serving size=1 cup, Meal=Dinner, Calories=80}, item: 21={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 22={Item=VDub Gardein Blk Bean Patty, Serving size=1 patty, Meal=Lunch, Calories=150}, item: 25={Item=VDub Vegan Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 0={Item=Ratty Brown Rice, Serving size=1 cup, Meal=Breakfast, Calories=300}, item: 3={Item=Ratty Plain Yogurt, Serving size=8oz, Meal=Breakfast, Calories=150}, item: 12={Item=Ratty Breaded Chicken Cutlet, Serving size=1 piece, Meal=Dinner, Calories=250}, item: 4={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Lunch, Calories=270}, item: 13={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, item: 1={Item=Ratty Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=250}, item: 10={Item=Ratty Cumin Roasted Sweet Potatoes, Serving size=4oz, Meal=Dinner, Calories=150}, item: 2={Item=Ratty Steel Cut Oats, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 11={Item=Ratty Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 7={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, item: 16={Item=VDub Tex Mex Tofu Scramble, Serving size=1 cup, Meal=Breakfast, Calories=200}, item: 8={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Dinner, Calories=270}, item: 17={Item=VDub Roasted Broccoli, Serving size=1 cup, Meal=Breakfast, Calories=55}, item: 5={Item=Ratty Garlicky Green Beans, Serving size=5oz, Meal=Lunch, Calories=100}, item: 14={Item=VDub Veggie Sausage Pattie , Serving size=1 patty, Meal=Breakfast, Calories=70}, item: 6={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Lunch, Calories=170}, item: 15={Item=VDub Applewood Smoked Bacon , Serving size=3 pieces , Meal=Breakfast, Calories=120}}",
        body.toString());
  }




  /**
   * This method tests a valid restriction search for alcohol
   * @throws IOException
   */
  @Test
  public void testValidVegetarianSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Vegetarian");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals(
        "{item: 9={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 18={Item=VDub Salad, Serving size=1 serving, Meal=Lunch, Calories=50}, item: 19={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 0={Item=Ratty Egg White Omlet, Serving size=6oz, Meal=Breakfast, Calories=45}, item: 20={Item=VDub Vegan Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 3={Item=Ratty Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=240}, item: 12={Item=VDub Scrambled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=180}, item: 4={Item=Ratty Veggie Sausage Patty, Serving size=2 patties, Meal=Breakfast, Calories=160}, item: 13={Item=VDub Oatmeal, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 1={Item=Ratty Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=130}, item: 10={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=190}, item: 2={Item=Ratty Scrambled Eggs, Serving size=4oz, Meal=Breakfast, Calories=170}, item: 11={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Dinner, Calories=200}, item: 7={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Lunch, Calories=200}, item: 16={Item=VDub Vegan Bbq Jackfruit, Serving size=1/2 cup, Meal=Lunch, Calories=90}, item: 8={Item=Ratty Cajun Pasta, Serving size=1 bowl, Meal=Dinner, Calories=500}, item: 17={Item=VDub Gardein Blk Bean Patty, Serving size=1 patty, Meal=Lunch, Calories=150}, item: 5={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 14={Item=VDub Omelet, Serving size=1 egg, Meal=Breakfast, Calories=90}, item: 6={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=190}, item: 15={Item=VDub Yogurt, Serving size= 1 cup, Meal=Breakfast, Calories=150}}",
        body.toString());
  }


  /**
   * This method tests a valid restriction search for soy
   * @throws IOException
   */
  @Test
  public void testValidHalalSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("{item: 9={Item=Ratty Crinkle-Cut Fries, Serving size=3oz, Meal=Lunch, Calories=360}, item: 18={Item=VDub Omelet, Serving size=1 egg, Meal=Breakfast, Calories=90}, item: 19={Item=VDub Yogurt, Serving size= 1 cup, Meal=Breakfast, Calories=150}, item: 0={Item=Ratty Egg White Omlet, Serving size=6oz, Meal=Breakfast, Calories=45}, item: 20={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 3={Item=Ratty Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=240}, item: 12={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 4={Item=Ratty Blueberry Pancakes, Serving size=2 pancakes, Meal=Breakfast, Calories=350}, item: 13={Item=Ratty Crinkle-Cut Fries, Serving size=3oz, Meal=Dinner, Calories=360}, item: 1={Item=Ratty Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=130}, item: 10={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=190}, item: 21={Item=VDub Salad, Serving size=1 serving, Meal=Lunch, Calories=50}, item: 2={Item=Ratty Scrambled Eggs, Serving size=4oz, Meal=Breakfast, Calories=170}, item: 11={Item=Ratty Cilantro Rice, Serving size=1 cup, Meal=Dinner, Calories=200}, item: 22={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 7={Item=Ratty Steel Cut Oats, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 16={Item=VDub Roasted Broccoli, Serving size=1 cup, Meal=Breakfast, Calories=55}, item: 8={Item=Ratty Plain Yogurt, Serving size=8oz, Meal=Breakfast, Calories=150}, item: 17={Item=VDub Oatmeal, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 5={Item=Ratty Tater Tots, Serving size=4oz, Meal=Breakfast, Calories=200}, item: 14={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Dinner, Calories=280}, item: 6={Item=Ratty Brown Rice, Serving size=1 cup, Meal=Breakfast, Calories=300}, item: 15={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=190}}",
        body.toString());
  }
  /**
   * This method tests a valid restriction search for gluten
   * @throws IOException
   */
  @Test
  public void testValidGlutenSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Gluten");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("{item: 0={Item=Ratty Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=240}, item: 3={Item=Ratty Garlicky Green Beans, Serving size=5oz, Meal=Lunch, Calories=100}, item: 4={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Lunch, Calories=280}, item: 1={Item=Ratty Brown Rice, Serving size=1 cup, Meal=Breakfast, Calories=300}, item: 2={Item=Ratty Plain Yogurt, Serving size=8oz, Meal=Breakfast, Calories=150}, item: 7={Item=VDub Roasted Broccoli, Serving size=1 cup, Meal=Breakfast, Calories=55}, item: 5={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Dinner, Calories=280}, item: 6={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Dinner, Calories=170}}",
        body.toString());
  }




  /**
   * This method tests a valid no restriction search
   * @throws IOException
   */
  @Test
  public void testValidNoRestrictionSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals(
        "{item: 41={Item=Ratty Crinkle-Cut Fries, Serving size=3oz, Meal=Dinner, Calories=360}, item: 42={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Dinner, Calories=280}, item: 40={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 45={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=190}, item: 46={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Dinner, Calories=200}, item: 43={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Dinner, Calories=170}, item: 44={Item=Ratty Breaded Chicken Cutlet, Serving size=1 piece, Meal=Dinner, Calories=250}, item: 49={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, item: 47={Item=Ratty Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=260}, item: 48={Item=Ratty Rigatoni, Serving size=8oz, Meal=Dinner, Calories=280}, item: 29={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, item: 70={Item=VDub Seasoned French Fries, Serving size=1 serving, Meal=Lunch, Calories=360}, item: 71={Item=VDub Cheese Manicotti , Serving size=1 piece, Meal=Dinner, Calories=240}, item: 30={Item=Ratty French Onion Soup, Serving size=1 cup, Meal=Dinner, Calories=380}, item: 74={Item=VDub Roasted Butternut Squash, Serving size=1 cup, Meal=Dinner, Calories=80}, item: 31={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Dinner, Calories=270}, item: 75={Item=VDub Vegan Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 72={Item=VDub Creamy Pesto Pasta , Serving size=1 serving, Meal=Dinner, Calories=300}, item: 73={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Dinner, Calories=200}, item: 34={Item=Ratty Cajun Pasta, Serving size=1 bowl, Meal=Dinner, Calories=500}, item: 78={Item=VDub Popcorn Shrimp, Serving size=12 pieces, Meal=Dinner, Calories=230}, item: 35={Item=Ratty Cilantro Rice, Serving size=1 cup, Meal=Dinner, Calories=200}, item: 79={Item=VDub Chipotle Chicken, Serving size=1 serving, Meal=Dinner, Calories=180}, item: 32={Item=Ratty Spicy Cuban Beef Stir-fry, Serving size=1 cup, Meal=Dinner, Calories=150}, item: 76={Item=VDub Turkey Burger 4 Oz Butterball, Serving size=1 patty, Meal=Dinner, Calories=190}, item: 33={Item=Ratty Cuban Style Black Beans, Serving size=1 cup, Meal=Dinner, Calories=200}, item: 77={Item=VDub Seasoned French Fries, Serving size=1 serving, Meal=Dinner, Calories=360}, item: 38={Item=Ratty Smores Cupcakes, Serving size=1 cupcake, Meal=Dinner, Calories=280}, item: 39={Item=Ratty Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 36={Item=Ratty Cumin Roasted Sweet Potatoes, Serving size=4oz, Meal=Dinner, Calories=150}, item: 37={Item=Ratty Chicken Taco Salad, Serving size=4oz, Meal=Dinner, Calories=250}, item: 9={Item=Ratty Cranberry Coffee Cake, Serving size=1 slice, Meal=Breakfast, Calories=400}, item: 18={Item=Ratty Garlicky Green Beans, Serving size=5oz, Meal=Lunch, Calories=100}, item: 19={Item=Ratty Mint Chocolate Chip Cookie, Serving size=1 piece, Meal=Lunch, Calories=100}, item: 60={Item=VDub Vegan Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=150}, item: 63={Item=VDub Jalapeno Ranch Mac And Cheese, Serving size=1 cup, Meal=Lunch, Calories=350}, item: 20={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 64={Item=VDub Vegan Bbq Jackfruit, Serving size=1/2 cup, Meal=Lunch, Calories=90}, item: 61={Item=VDub Vegan Buffalo Chicken Scallopini, Serving size=3 oz, Meal=Lunch, Calories=120}, item: 62={Item=VDub Chicken Caesar Wrap, Serving size=1 wrap, Meal=Lunch, Calories=600}, item: 23={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Lunch, Calories=170}, item: 67={Item=VDub Turkey Burger, Serving size=1 patty, Meal=Lunch, Calories=190}, item: 24={Item=Ratty Breaded Chickn Cutlet, Serving size=1 piece, Meal=Lunch, Calories=250}, item: 68={Item=VDub Gardein Blk Bean Patty, Serving size=1 patty, Meal=Lunch, Calories=150}, item: 21={Item=Ratty Crinkle-Cut Fries, Serving size=3oz, Meal=Lunch, Calories=360}, item: 65={Item=VDub Basmati Rice Cooked, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 22={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Lunch, Calories=280}, item: 66={Item=VDub Spicy-sweet Roasted Yams, Serving size=1 serving, Meal=Lunch, Calories=180}, item: 27={Item=Ratty Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=260}, item: 28={Item=Ratty Rigatoni, Serving size=8oz, Meal=Lunch, Calories=280}, item: 25={Item=Ratty Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=190}, item: 69={Item=VDub Salad, Serving size=1 serving, Meal=Lunch, Calories=50}, item: 26={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Lunch, Calories=200}, item: 52={Item=VDub Applewood Smoked Bacon , Serving size=3 pieces , Meal=Breakfast, Calories=120}, item: 0={Item=Ratty Egg White Omlet, Serving size=6oz, Meal=Breakfast, Calories=45}, item: 53={Item=VDub Tex Mex Tofu Scramble, Serving size=1 cup, Meal=Breakfast, Calories=200}, item: 50={Item=VDub Scrambled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=180}, item: 51={Item=VDub Veggie Sausage Pattie , Serving size=1 patty, Meal=Breakfast, Calories=70}, item: 3={Item=Ratty Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=240}, item: 12={Item=Ratty Plain Yogurt, Serving size=8oz, Meal=Breakfast, Calories=150}, item: 56={Item=VDub Oatmeal, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 4={Item=Ratty Steak & Egg Frittata, Serving size=1 serving, Meal=Breakfast, Calories=400}, item: 13={Item=Ratty Turkey Breakfast Sausage, Serving size=1 sausage, Meal=Breakfast, Calories=100}, item: 57={Item=VDub Omelet, Serving size=1 egg, Meal=Breakfast, Calories=90}, item: 1={Item=Ratty Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=130}, item: 10={Item=Ratty Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=250}, item: 54={Item=VDub Tater Tots , Serving size=10 tots, Meal=Breakfast, Calories=150}, item: 2={Item=Ratty Scrambled Eggs, Serving size=4oz, Meal=Breakfast, Calories=170}, item: 11={Item=Ratty Steel Cut Oats, Serving size=1 cup, Meal=Breakfast, Calories=150}, item: 55={Item=VDub Roasted Broccoli, Serving size=1 cup, Meal=Breakfast, Calories=55}, item: 7={Item=Ratty Tater Tots, Serving size=4oz, Meal=Breakfast, Calories=200}, item: 16={Item=Ratty Tortellini, Serving size=3oz, Meal=Lunch, Calories=250}, item: 8={Item=Ratty Brown Rice, Serving size=1 cup, Meal=Breakfast, Calories=300}, item: 17={Item=Ratty Balsamic Marinated Chicken, Serving size=1 piece, Meal=Lunch, Calories=140}, item: 5={Item=Ratty Blueberry Pancakes, Serving size=2 pancakes, Meal=Breakfast, Calories=350}, item: 14={Item=Ratty French Onion Soup, Serving size=1 cup, Meal=Lunch, Calories=380}, item: 58={Item=VDub Yogurt, Serving size= 1 cup, Meal=Breakfast, Calories=150}, item: 6={Item=Ratty Veggie Sausage Patty, Serving size=2 patties, Meal=Breakfast, Calories=160}, item: 15={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Lunch, Calories=270}, item: 59={Item=VDub Cranberry Coffee Cake, Serving size=1 slice, Meal=Breakfast, Calories=350}}",
        body.toString());
  }


  public static String getRandomRestriction() {
    Random random = new Random();
    int randomNumber = random.nextInt(5); // Generates a random number between 0 and 4

    String restriction;
    switch (randomNumber) {
      case 0:
        restriction = "menu?restriction=Halal";
        break;
      case 1:
        restriction = "menu?restriction=Vegan";
        break;
      case 2:
        restriction = "menu?restriction=Vegetarian";
        break;
      case 3:
        restriction = "menu?restriction=Gluten";
        break;
      default:
        String randomString = generateRandomString(
            3 + random.nextInt(8)); // Generates a random string of length 3-10
        restriction = "menu?restriction=" + randomString;
        break;
    }
    return restriction;
  }

  public static String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }
    return sb.toString();
  }

  private Boolean isValid(String result) {
    return result != null && result.contains("Breakfast") && result.contains("Lunch") &&
        result.contains("Dinner");
  }

  @Test
  public void FuzzTest() throws IOException {
    for (int i = 0; i < 20; i++) {
      String call = getRandomRestriction();
      HttpURLConnection loadConnection = tryRequest(call);
      Map<String, Object> body =
          adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
      assertEquals(200, loadConnection.getResponseCode());
      System.out.println(body.toString());
    }
  }

  @Test
  public void PropertyBasedTest() throws IOException {
    for (int i = 0; i < 20; i++) {
      String call = getRandomRestriction();
      HttpURLConnection loadConnection = tryRequest(call);
      Map<String, Object> body =
          adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
      assertEquals(200, loadConnection.getResponseCode());
      //System.out.println(body.toString());
      assert body != null;
      Assert.assertTrue(isValid(body.toString()));
    }
  }

  @Test
  public void PropertyBasedTestSameResultFromSameInput() throws IOException {
    String res = "{item: 41={Item=VDub Vegan Buffalo Chicken Scallopini, Serving size=3 oz, Meal=Lunch, Calories=120}, item: 42={Item=VDub Chicken Caesar Wrap, Serving size=1 wrap, Meal=Lunch, Calories=600}, item: 40={Item=VDub Vegan Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=150}, item: 45={Item=VDub Spicy-sweet Roasted Yams, Serving size=1 serving, Meal=Lunch, Calories=180}, item: 46={Item=VDub Turkey Burger, Serving size=1 patty, Meal=Lunch, Calories=190}, item: 43={Item=VDub Jalapeno Ranch Mac And Cheese, Serving size=1 cup, Meal=Lunch, Calories=350}, item: 44={Item=VDub Vegan Bbq Jackfruit, Serving size=1/2 cup, Meal=Lunch, Calories=90}, item: 49={Item=VDub Cheese Manicotti , Serving size=1 piece, Meal=Dinner, Calories=240}, item: 47={Item=VDub Gardein Blk Bean Patty, Serving size=1 patty, Meal=Lunch, Calories=150}, item: 48={Item=VDub Seasoned French Fries, Serving size=1 serving, Meal=Lunch, Calories=360}, item: 29={Item=Ratty Breaded Chicken Cutlet, Serving size=1 piece, Meal=Dinner, Calories=250}, item: 30={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Dinner, Calories=200}, item: 31={Item=Ratty Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=260}, item: 34={Item=VDub Scrambled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=180}, item: 35={Item=VDub Veggie Sausage Pattie , Serving size=1 patty, Meal=Breakfast, Calories=70}, item: 32={Item=Ratty Rigatoni, Serving size=8oz, Meal=Dinner, Calories=280}, item: 33={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, item: 38={Item=VDub Tater Tots , Serving size=10 tots, Meal=Breakfast, Calories=150}, item: 39={Item=VDub Cranberry Coffee Cake, Serving size=1 slice, Meal=Breakfast, Calories=350}, item: 36={Item=VDub Applewood Smoked Bacon , Serving size=3 pieces , Meal=Breakfast, Calories=120}, item: 37={Item=VDub Tex Mex Tofu Scramble, Serving size=1 cup, Meal=Breakfast, Calories=200}, item: 9={Item=Ratty Garlicky Green Beans, Serving size=5oz, Meal=Lunch, Calories=100}, item: 18={Item=Ratty Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, item: 19={Item=Ratty French Onion Soup, Serving size=1 cup, Meal=Dinner, Calories=380}, item: 20={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Dinner, Calories=270}, item: 23={Item=Ratty Cajun Pasta, Serving size=1 bowl, Meal=Dinner, Calories=500}, item: 24={Item=Ratty Cumin Roasted Sweet Potatoes, Serving size=4oz, Meal=Dinner, Calories=150}, item: 21={Item=Ratty Spicy Cuban Beef Stir-fry, Serving size=1 cup, Meal=Dinner, Calories=150}, item: 22={Item=Ratty Cuban Style Black Beans, Serving size=1 cup, Meal=Dinner, Calories=200}, item: 27={Item=Ratty Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 28={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Dinner, Calories=170}, item: 25={Item=Ratty Chicken Taco Salad, Serving size=4oz, Meal=Dinner, Calories=250}, item: 26={Item=Ratty Smores Cupcakes, Serving size=1 cupcake, Meal=Dinner, Calories=280}, item: 52={Item=VDub Vegan Vanilla Cake, Serving size=1 slice, Meal=Dinner, Calories=250}, item: 0={Item=Ratty Steak & Egg Frittata, Serving size=1 serving, Meal=Breakfast, Calories=400}, item: 53={Item=VDub Turkey Burger 4 Oz Butterball, Serving size=1 patty, Meal=Dinner, Calories=190}, item: 50={Item=VDub Creamy Pesto Pasta , Serving size=1 serving, Meal=Dinner, Calories=300}, item: 51={Item=VDub Roasted Butternut Squash, Serving size=1 cup, Meal=Dinner, Calories=80}, item: 3={Item=Ratty Zucchini Muffin, Serving size=1 muffin, Meal=Breakfast, Calories=250}, item: 12={Item=Ratty Grilled Chicken, Serving size=1 piece, Meal=Lunch, Calories=280}, item: 56={Item=VDub Chipotle Chicken, Serving size=1 serving, Meal=Dinner, Calories=180}, item: 4={Item=Ratty Turkey Breakfast Sausage, Serving size=1 sausage, Meal=Breakfast, Calories=100}, item: 13={Item=Ratty Saugy Hot Dog, Serving size=1 piece, Meal=Lunch, Calories=170}, item: 1={Item=Ratty Veggie Sausage Patty, Serving size=2 patties, Meal=Breakfast, Calories=160}, item: 10={Item=Ratty Mint Chocolate Chip Cookie, Serving size=1 piece, Meal=Lunch, Calories=100}, item: 54={Item=VDub Seasoned French Fries, Serving size=1 serving, Meal=Dinner, Calories=360}, item: 2={Item=Ratty Cranberry Coffee Cake, Serving size=1 slice, Meal=Breakfast, Calories=400}, item: 11={Item=Ratty Italian Salad, Serving size=1 serving, Meal=Lunch, Calories=200}, item: 55={Item=VDub Popcorn Shrimp, Serving size=12 pieces, Meal=Dinner, Calories=230}, item: 7={Item=Ratty Tortellini, Serving size=3oz, Meal=Lunch, Calories=250}, item: 16={Item=Ratty Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=260}, item: 8={Item=Ratty Balsamic Marinated Chicken, Serving size=1 piece, Meal=Lunch, Calories=140}, item: 17={Item=Ratty Rigatoni, Serving size=8oz, Meal=Lunch, Calories=280}, item: 5={Item=Ratty French Onion Soup, Serving size=1 cup, Meal=Lunch, Calories=380}, item: 14={Item=Ratty Breaded Chickn Cutlet, Serving size=1 piece, Meal=Lunch, Calories=250}, item: 6={Item=Ratty Minestrone Soup, Serving size=1 cup, Meal=Lunch, Calories=270}, item: 15={Item=Ratty Veggie Pizza, Serving size=1 slice, Meal=Lunch, Calories=200}}";
    for (int i = 25; i<25;i++) {
      HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
      Map<String, Object> body =
          adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
      //System.out.println(body.toString());
      assertEquals(200, loadConnection.getResponseCode());
      assertEquals(body.toString(),res);
    }
  }
}
