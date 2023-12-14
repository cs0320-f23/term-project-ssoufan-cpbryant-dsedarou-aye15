package edu.brown.cs.student.main.Handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import edu.brown.cs.student.main.CSV.CSVData;
//import edu.brown.cs.student.main.CSV.CSVData.CSVRowCreator;
import edu.brown.cs.student.main.CSV.Parser;
import edu.brown.cs.student.main.Exceptions.FactoryFailureException;
import edu.brown.cs.student.main.CSV.Searcher;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.List;

/**
 * The `SearchHandler` class is responsible for handling HTTP requests related to searching within
 * CSV data in a web application. It implements the Spark `Route` interface, allowing it to process
 * incoming HTTP GET requests. This class retrieves the file path from a `CSVData` object, performs
 * searches within the CSV data based on specified criteria (e.g., value, headers, column name or index),
 * and generates JSON responses containing the search results or error information.
 */
public class MenuHandler implements Route{
  private CSVData data;
  private final String filepath;

  public MenuHandler(String filepath) {
    this.filepath = filepath;

  }

  /**
   * Handles the incoming HTTP GET request for searching within CSV data. It retrieves the file path
   * from the associated `CSVData` object, performs the search based on request parameters, and responds
   * with a JSON message containing the search results or an error message.
   *
   * @param request  The HTTP request object.
   * @param response The HTTP response object.
   * @return A JSON response containing either the search results or an error message.
   * @throws IOException If there are any I/O errors during the search operation.
   * @throws FactoryFailureException If there are any failures related to factory operations.
   */
  public Object handle(Request request, Response response)
      throws IOException, FactoryFailureException {
//    this.filepath = data.getFilePath();
    HashMap<String, Object> failure = new HashMap<>();

    if (this.filepath == null || this.filepath.isEmpty()) {
      return new FailureResponse(failure).serialize();
    }

    String value = request.queryParams("restriction");
    Searcher searcher = new Searcher(this.filepath, value);
    List<List<String>> result = searcher.getNoHeaderResult();

    Searcher all = new Searcher(this.filepath,"-");
    List<List<String>> allResult = all.getNoHeaderResult();

    List<List<String>> filteredItems = allResult.stream()
        .filter(item -> !item.contains(value))
        .collect(Collectors.toList());

    if (result.isEmpty()){
      return new FailureResponse(failure).serialize();
    }
    return new SuccessResponse(filteredItems, this.filepath).serialize();
  }

  /**
   * The `SuccessResponse` record represents a JSON response indicating a successful search operation
   * within CSV data. It includes methods for serializing the response as JSON.
   */
  public record SuccessResponse(List<List<String>> data, String filepath) {
    /**
     * Serializes the success response as JSON.
     *
     * @return A JSON representation of the success response.
     */
    String serialize() {
      try {
        Moshi moshi = new Moshi.Builder().build();
//        JsonAdapter<SearchHandler.SuccessResponse> adapter = moshi.adapter(
//            SearchHandler.SuccessResponse.class);
        Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("data", data);
//        System.out.println(data);
        for (int i = 0; i < data.size(); i++){
          Map<String, String> map = new HashMap<>();
          map.put("Meal", data.get(i).get(0));
          map.put("Item", data.get(i).get(1));
          map.put("Calories", data.get(i).get(2));
          map.put("Serving size", data.get(i).get(3));
          responseMap.put("item: " + i, map);
//          int menuIndex = filepath.indexOf("Menu");
//          int lastSlashIndex = filepath.lastIndexOf("/", menuIndex);
//          String dayOfWeek = filepath.substring(lastSlashIndex + 1, menuIndex);
//          responseMap.put("day: ", dayOfWeek);
        }
//        System.out.println(responseMap);
//        responseMap.put("result", "success");
        return adapter.toJson(responseMap);
//        return adapter.toJson(this);
      }catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
    }
  }

  /**
   * The `FailureResponse` record represents a JSON response indicating a failed search operation within
   * CSV data. It includes methods for serializing the response as JSON.
   */
  public record FailureResponse(HashMap<String, Object> filepath) {

    /**
     * Serializes the success response as JSON.
     *
     * @return A JSON representation of the success response.
     */
    String serialize() {
      Moshi moshi = new Moshi.Builder().build();
//        JsonAdapter<SearchHandler.SuccessResponse> adapter = moshi.adapter(
//            SearchHandler.SuccessResponse.class);
      Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
      JsonAdapter<Map<String, Object>> adapter = moshi.adapter(mapStringObject);
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("result", "failure");
      return adapter.toJson(responseMap);
    }
  }

}