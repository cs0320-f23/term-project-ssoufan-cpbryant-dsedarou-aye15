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
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

public class MenuMockTesting {

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
    Spark.get("menu", new MenuHandler("C:\\cs32\\term-project-ssoufan-cpbryant-dsedarou-aye15\\backend\\src\\main\\java\\menu.csv"));
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
   * @param apiCall- The queries we would like to input for our search
   * @return the connection after searching
   * @throws IOException - Error with any connectivity
   */
  private static HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection (but don't actually send the request yet)
    URL requestURL = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
    clientConnection.connect();
    return clientConnection;
  }

  /**
   * This method tests if the area query parameter is not inputted
   * @throws IOException
   */
  @Test
  public void testNoAreaParameter() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?feufiwef");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("failure", body.get("result"));
    assertEquals("Please input a proper query, EX: [http://localhost:2025/menu?restriction= "
        + "Alcohol, Soy, Shellfish, Gluten, Wheat, Eggs, Milk, or leave it empty for no restriction]",
        body.get("error_description"));
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
    assertEquals("Please input a proper query, EX: [http://localhost:2025/menu?restriction= "
            + "Alcohol, Soy, Shellfish, Gluten, Wheat, Eggs, Milk, or leave it empty for no restriction]",
        body.get("error_description"));
  }

  /**
   * This method tests a valid restriction search for wheat
   * @throws IOException
   */
  @Test
  public void testValidWheatSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Wheat");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 4={Item=Scrambled Eggs with Cheese, Serving size=4oz, Meal=Breakfast, Calories=201}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 2={Item=Vegetarian Omelette, Serving size=6oz, Meal=Breakfast, Calories=201}, "
            + "item: 7={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 8={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, "
            + "item: 5={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 6={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}}",
        body.toString());
  }

  /**
   * This method tests a valid restriction search for alcohol
   * @throws IOException
   */
  @Test
  public void testValidAlcoholSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Alcohol");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 18={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133},"
            + " item: 19={Item=Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=192},"
            + " item: 20={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=263}, "
            + "item: 23={Item=Birthday Cake, Serving size=2.8oz, Meal=Dinner, Calories=265}, "
            + "item: 21={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Dinner, Calories=221}, "
            + "item: 22={Item=Naan Bread, Serving size=125g, Meal=Dinner, Calories=376}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133},"
            + " item: 3={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 12={Item=Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=192}, "
            + "item: 4={Item=Scrambled Eggs with Cheese, Serving size=4oz, Meal=Breakfast, Calories=201}, "
            + "item: 13={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=263}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 10={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, "
            + "item: 2={Item=Vegetarian Omelette, Serving size=6oz, Meal=Breakfast, Calories=201},"
            + " item: 11={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 7={Item=Romano, Serving size=517, Meal=Lunch, Calories=-Dijon Crusted Chicken}, "
            + "item: 16={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 8={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, "
            + "item: 17={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 5={Item=Veggie Sausage Pattie, Serving size=2 patties, Meal=Breakfast, Calories=160}, "
            + "item: 14={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Lunch, Calories=221}, "
            + "item: 6={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 15={Item=Creamy Pesto Pasta Shrimp Saute, Serving size=8oz, Meal=Lunch, Calories=206}}",
        body.toString());
  }
  /**
   * This method tests a valid restriction search for soy
   * @throws IOException
   */
  @Test
  public void testValidSoySearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Soy");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=263}, "
            + "item: 18={Item=Naan Bread, Serving size=125g, Meal=Dinner, Calories=376}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Scrambled Eggs with Cheese, Serving size=4oz, Meal=Breakfast, Calories=201}, "
            + "item: 12={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 4={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, "
            + "item: 13={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 10={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Lunch, Calories=221}, "
            + "item: 2={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 11={Item=Creamy Pesto Pasta Shrimp Saute, Serving size=8oz, Meal=Lunch, Calories=206}, "
            + "item: 7={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 16={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=263}, "
            + "item: 8={Item=Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=192}, "
            + "item: 17={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Dinner, Calories=221}, "
            + "item: 5={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 14={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, "
            + "item: 6={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, "
            + "item: 15={Item=Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=192}}",
        body.toString());
  }
  /**
   * This method tests a valid restriction search for milk
   * @throws IOException
   */
  @Test
  public void testValidMilkSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Milk");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 4={Item=Vegan Chocolate Banana Bread, Serving size=1 slice, Meal=Breakfast, Calories=291}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 10={Item=Vegan Red Velvet Cupcake, Serving size=1 cupcake, Meal=Dinner, Calories=502}, "
            + "item: 2={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 7={Item=Vegan Blondies with Cranberry, Serving size=1 bar, Meal=Lunch, Calories=262}, "
            + "item: 8={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 5={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, "
            + "item: 6={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}}",
        body.toString());
  }
  /**
   * This method tests a valid restriction search for shellfish
   * @throws IOException
   */
  @Test
  public void testValidShellfishSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Shellfish");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Maple Cream Cheese French Toast Casserole, Serving size=4oz, Meal=Breakfast, Calories=388}, "
            + "item: 18={Item=Chocolate Chili Spice Cookies, Serving size=1 cookie, Meal=Lunch, Calories=181}, "
            + "item: 19={Item=Vegan Blondies with Cranberry, Serving size=1 bar, Meal=Lunch, Calories=262}, "
            + "item: 20={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 23={Item=Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=192}, "
            + "item: 24={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=263}, "
            + "item: 21={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 22={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, "
            + "item: 27={Item=Vegan Red Velvet Cupcake, Serving size=1 cupcake, Meal=Dinner, Calories=502}, "
            + "item: 28={Item=Birthday Cake, Serving size=2.8oz, Meal=Dinner, Calories=265}, "
            + "item: 25={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Dinner, Calories=221}, "
            + "item: 26={Item=Naan Bread, Serving size=125g, Meal=Dinner, Calories=376}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 12={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 4={Item=Scrambled Eggs with Cheese, Serving size=4oz, Meal=Breakfast, Calories=201}, "
            + "item: 13={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 10={Item=Romano, Serving size=517, Meal=Lunch, Calories=-Dijon Crusted Chicken}, "
            + "item: 2={Item=Vegetarian Omelette, Serving size=6oz, Meal=Breakfast, Calories=201}, "
            + "item: 11={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, "
            + "item: 7={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 16={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=263}, "
            + "item: 8={Item=Vegan Chocolate Banana Bread, Serving size=1 slice, Meal=Breakfast, Calories=291}, "
            + "item: 17={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Lunch, Calories=221}, "
            + "item: 5={Item=Veggie Sausage Pattie, Serving size=2 patties, Meal=Breakfast, Calories=160}, "
            + "item: 14={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 6={Item=Gingerbread Donut, Serving size=1 donut, Meal=Breakfast, Calories=216}, "
            + "item: 15={Item=Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=192}}",
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
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 4={Item=Scrambled Eggs with Cheese, Serving size=4oz, Meal=Breakfast, Calories=201}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 2={Item=Vegetarian Omelette, Serving size=6oz, Meal=Breakfast, Calories=201}, "
            + "item: 7={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 8={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 5={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 6={Item=Romano, Serving size=517, Meal=Lunch, Calories=-Dijon Crusted Chicken}}",
        body.toString());
  }
  /**
   * This method tests a valid restriction search for eggs
   * @throws IOException
   */
  @Test
  public void testValidEggsSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Eggs");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Creamy Pesto Pasta Shrimp Saute, Serving size=8oz, Meal=Lunch, Calories=206}, "
            + "item: 18={Item=Vegan Red Velvet Cupcake, Serving size=1 cupcake, Meal=Dinner, Calories=502},"
            + " item: 0={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 12={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 4={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, "
            + "item: 13={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 1={Item=Vegan Chocolate Banana Bread, Serving size=1 slice, Meal=Breakfast, Calories=291}, "
            + "item: 10={Item=Chocolate Chili Spice Cookies, Serving size=1 cookie, Meal=Lunch, Calories=181}, item: 2={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, item: 11={Item=Vegan Blondies with Cranberry, Serving size=1 bar, Meal=Lunch, Calories=262}, item: 7={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=263}, item: 16={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=263}, item: 8={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Lunch, Calories=221}, item: 17={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Dinner, Calories=221}, item: 5={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, item: 14={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, item: 6={Item=Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=192}, "
            + "item: 15={Item=Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=192}}",
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
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("{item: 9={Item=Roasted Romano Potatoes, Serving size=4oz, Meal=Lunch, Calories=111}, "
            + "item: 18={Item=Rigatoni, Serving size=8oz, Meal=Dinner, Calories=278}, "
            + "item: 19={Item=Whole Wheat Penne, Serving size=4oz, Meal=Dinner, Calories=300}, "
            + "item: 20={Item=Ham & Bean Soup, Serving size=6oz, Meal=Dinner, Calories=133}, "
            + "item: 23={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Dinner, Calories=221}, "
            + "item: 24={Item=Naan Bread, Serving size=125g, Meal=Dinner, Calories=376}, "
            + "item: 21={Item=Cheese Pizza, Serving size=1 slice, Meal=Dinner, Calories=192}, "
            + "item: 22={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Dinner, Calories=263}, "
            + "item: 25={Item=Vegan Red Velvet Cupcake, Serving size=1 cupcake, Meal=Dinner, Calories=502}, "
            + "item: 26={Item=Birthday Cake, Serving size=2.8oz, Meal=Dinner, Calories=265}, "
            + "item: 0={Item=Hard Boiled Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=133}, "
            + "item: 3={Item=Egg White Omelette, Serving size=100g, Meal=Breakfast, Calories=46}, "
            + "item: 12={Item=Cheese Pizza, Serving size=1 slice, Meal=Lunch, Calories=192}, "
            + "item: 4={Item=Basic Tofu Scramble, Serving size=3.5oz, Meal=Breakfast, Calories=133}, "
            + "item: 13={Item=Pepperoni Pizza, Serving size=1 slice, Meal=Lunch, Calories=263}, "
            + "item: 1={Item=Fried Eggs, Serving size=2 eggs, Meal=Breakfast, Calories=132}, "
            + "item: 10={Item=Whole Wheat Penne, Serving size=4oz, Meal=Lunch, Calories=300}, "
            + "item: 2={Item=Vegetarian Omelette, Serving size=6oz, Meal=Breakfast, Calories=201}, "
            + "item: 11={Item=Ham & Bean Soup, Serving size=6oz, Meal=Lunch, Calories=133}, "
            + "item: 7={Item=Romano, Serving size=517, Meal=Lunch, Calories=-Dijon Crusted Chicken}, "
            + "item: 16={Item=Chocolate Chili Spice Cookies, Serving size=1 cookie, Meal=Lunch, Calories=181}, "
            + "item: 8={Item=Rigatoni, Serving size=8oz, Meal=Lunch, Calories=278}, "
            + "item: 17={Item=Vegan Blondies with Cranberry, Serving size=1 bar, Meal=Lunch, Calories=262}, "
            + "item: 5={Item=Vegan Chocolate Banana Bread, Serving size=1 slice, Meal=Breakfast, Calories=291}, "
            + "item: 14={Item=Meat Lovers Pizza, Serving size=1 slice, Meal=Lunch, Calories=221}, "
            + "item: 6={Item=Maple Cream Cheese French Toast Casserole, Serving size=4oz, Meal=Breakfast, Calories=388}, "
            + "item: 15={Item=Creamy Pesto Pasta Shrimp Saute, Serving size=8oz, Meal=Lunch, Calories=206}}",
        body.toString());
  }
}
