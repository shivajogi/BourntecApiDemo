package StepDefination;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static liberary.ConfigReader.getConfigValue;

public class Post_Request_Api {
    String baseURL;
    String post_endPoint;
    RequestSpecification request;

    ResponseSpecification responseSpec;
    Response ExtractResponse;
    String key;
    String value;
    String Keys;
    String Values;
    String HeaderName;
    String HeaderValue;
    String place_id;


    private JSONObject jsonObject;
    private String filePath = "C:\\Users\\ShivaJogi\\Documents\\BourntecApiDemo\\UpdateBody.json";



    public RequestSpecification requestSpecification(String KEYS, String VALUES,String NAME, String Values ) throws IOException {


            PrintStream log = new PrintStream(new FileOutputStream("loggin1.txt"));
//            request = new RequestSpecBuilder().setBaseUri(baseURL)

              request= new RequestSpecBuilder().setBaseUri(getConfigValue("baseURL"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .addQueryParam(KEYS, VALUES)
                    .addHeader(NAME,Values).build();

//                    .setContentType(ContentType.JSON).build();
            return request;
        }



    public ResponseSpecification responseSpecification()
    {
         responseSpec= new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON).build();
         return responseSpec;
    }

    // ..................................ADD Post Request ..........................................................

    @Given("BaseUrl for post {string}")
    public void baseurlForPost(String url) {
        baseURL = url;
        System.out.println("Entered url is "+baseURL);
    }

    @And("endPoint for post request {string}")
    public void endpointForPostRequest(String endpoint)
    {
        post_endPoint=endpoint;
    }

    @And("user enters body for post request as {string}")
    public void userEntersBodyForPostRequestAs(String Jsonbody) throws IOException {
        String sendData = new String(Files.readAllBytes(Paths.get(Jsonbody)));
        ExtractResponse= given().log().all().spec(requestSpecification(Keys,Values,HeaderName,HeaderValue))
                .when().body(sendData).post(post_endPoint)
                .then().log().all().spec(responseSpecification()).extract().response();
    }

    @And("Then the API call got success with status code {string}")
    public void thenTheAPICallGotSuccessWithStatusCode(String statuscode) {
        int ActualstatusCode= ExtractResponse.getStatusCode();
        int expectcode= Integer.parseInt(statuscode);
        Assert.assertEquals(ActualstatusCode,expectcode);
    }

    @And("Check scope of the response after post call {string}")
    public void checkScopeOfTheResponseAfterPostCall(String scop) {
       String response= ExtractResponse.asString();
        JsonPath js= new JsonPath(response);
        Assert.assertEquals(js.get(scop),"APP");

    }


    @And("user enter quaryParam key value")
    public void userEnterQuaryParamKeyValue(DataTable table) {
        List<Map<String, String>> data = table.asMaps();
        for (Map<String, String> form : data) {
             Keys = form.get("Key");
             Values = form.get("value");
            System.out.println(Keys);
            System.out.println(Values);
        }
   }

    @And("Store the place_id in one variable")
    public void storeThePlace_idInOneVariable() {
        String gettingresponse= ExtractResponse.asString();
        JsonPath js= new JsonPath(gettingresponse);
        place_id= js.get("place_id");
        System.out.println("place_id value is.........."+ place_id);
    }

    // ..........................................GET Request.................................................................

    @Given("BaseUrl for Get {string}")
    public void baseurlForGet(String geturl) {
        baseURL = geturl;
    }

    @And("endPoint for Get request {string}")
    public void endpointForGetRequest(String ENDPOINT) throws IOException {
        ExtractResponse= given().log().all().spec(requestSpecification(Keys,Values,HeaderName,HeaderValue)).queryParam("place_id",place_id)
                .when().get(ENDPOINT)
                .then().log().all().spec(responseSpecification()).extract().response();
    }

    //..................................................put request...............................................................

    @Given("BaseUrl for put {string}")
    public void baseurlForPut(String PUTURL) {
        baseURL = PUTURL;
    }

    @And("endPoint for Put request {string}")
    public void endpointForPutRequest(String PutEndpoint) throws IOException
    {
            post_endPoint=PutEndpoint;
    }

    @And("user enters body for Update request as {string}")
    public void userEntersBodyForUpdateRequestAs(String Jsonbody) throws IOException {
        String sendData = new String(Files.readAllBytes(Paths.get(Jsonbody)));
        ExtractResponse= given().log().all().spec(requestSpecification(Keys,Values,HeaderName,HeaderValue)).queryParam("place_id",place_id)
                .when().body(sendData).put(post_endPoint)
                .then().log().all().spec(responseSpecification()).extract().response();
    }

    //..............................................Delete the Request............................................................

    @Given("BaseUrl for Delete {string}")
    public void baseurlForDelete(String DeleteURL) {
        baseURL = DeleteURL;
    }
    @And("endPoint for Delete request {string}")
    public void endpointForDeleteRequest(String DeleteEndpoint) throws IOException {
        post_endPoint=DeleteEndpoint;
        ExtractResponse= given().log().all().spec(requestSpecification(Keys,Values,HeaderName,HeaderValue).queryParam("place_id",place_id))
                .when().delete(post_endPoint)
                .then().log().all().spec(responseSpecification()).extract().response();
    }

    @And("user enters body for Delete request as {string}")
    public void userEntersBodyForDeleteRequestAs(String Jsonbody) throws IOException {
        String sendData = new String(Files.readAllBytes(Paths.get(Jsonbody)));
        ExtractResponse= given().log().all().spec(requestSpecification(Keys,Values,HeaderName,HeaderValue).queryParam("place_id",place_id))
                .when().body(sendData).delete(post_endPoint)
                .then().log().all().spec(responseSpecification()).extract().response();
    }


    @And("user enter header value")
    public void userEnterHeaderValue(DataTable table) {
        List<Map<String, String>> data = table.asMaps();
        for (Map<String, String> form : data) {
            HeaderName = form.get("name");
            HeaderValue = form.get("value");
            System.out.println(Keys);
            System.out.println(Values);
        }
    }

  // .......................................update the Json body and upload the file............................................

    @Given("the JSON file is present in the project")
    public void theJSONFileIsPresentInTheProject() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath));
        jsonObject = (JSONObject) obj;
    }

    @When("I edit the JSON data {string} {string}")
    public void iEditTheJSONData(String keys, String values) {
        jsonObject.put(keys, values);
    }

//    @When("I edit the JSON data")
//    public void iEditTheJSONData() {
//        jsonObject.put("accuracy", "100");
//        jsonObject.put("phone_number", "8106006612");
//        jsonObject.put("website", "www.facebook.com");
//        System.out.println();
//    }

    @And("I upload the updated JSON file")
    public void iUploadTheUpdatedJSONFile() throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toJSONString());
        }
    }

        @Then("the JSON file should be updated successfully")
        public void theJSONFileShouldBeUpdatedSuccessfully () throws IOException, ParseException {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject actualJsonObject = (JSONObject) obj;

            // Verify that the expected changes are reflected in the JSON file
            Assert.assertEquals("www.facebook.com", actualJsonObject.get("website"));
        }



}

