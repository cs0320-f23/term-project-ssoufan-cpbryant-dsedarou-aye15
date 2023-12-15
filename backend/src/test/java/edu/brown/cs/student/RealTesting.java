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
    Spark.get("menu", new MenuHandler("/Users/shadisoufan/Desktop/term-project-ssoufan-cpbryant-dsedarou-aye15/backend/data/MondayMenu.csv"));
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

    // The default method is "GET", which is what we're using here.
    // If we were using "POST", we'd need to say so.
    // clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  /**
   * This method tests if the area query parameter is not inputted
   * @throws IOException
   */

  @Test
  public void testValidHalalSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("",
        body.toString());
  }

  @Test
  public void testValidVeganSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("",
        body.toString());
  }

  @Test
  public void testValidVegetarianSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("",
        body.toString());
  }

  @Test
  public void testValidGlutenSearch() throws IOException {
    HttpURLConnection loadConnection = tryRequest("menu?restriction=Halal");
    Map<String, Object> body =
        adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
    assertEquals(200, loadConnection.getResponseCode());
    assertEquals("success", body.get("result"));
    body.remove("result");
    assertEquals("",
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
        String randomString = generateRandomString(3 + random.nextInt(8)); // Generates a random string of length 3-10
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

  @Test
  public void FuzzTest() throws IOException {
    for(int i = 0;i<20;i++) {
      String call = getRandomRestriction();
      HttpURLConnection loadConnection = tryRequest(call);
      Map<String, Object> body =
          adapter.fromJson(new Buffer().readFrom(loadConnection.getInputStream()));
      assertEquals(200, loadConnection.getResponseCode());
      System.out.println(body.toString());
    }

  }


}
