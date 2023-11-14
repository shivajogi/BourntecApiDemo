@tag1
Feature: Validating Place API's

  Scenario Outline: Verify if Place is being succesfully added using AddPlaceAPI

    And   user enter quaryParam key value
    |Key|value|
    |keys|qaclick123|
    |numb|asbd12345 |
    And   user enter header value
    |name        |value           |
    |Content-Type|application/json|
    And   endPoint for post request "<END POINT>"
    And   user enters body for post request as "<BODY>"
    And   Then the API call got success with status code "<StatusCode>"
    And   Store the place_id in one variable

    Examples:
      | END POINT              |BODY         |StatusCode|
      |/maps/api/place/add/json|AddPlace.json|200       |


  Scenario Outline: Verify if Place is being succesfully added using GetPlaceAPI

    And   user enter quaryParam key value
      |Key|value|
      |keys|qaclick123|
    And   user enter header value
      |name        |value           |
      |Content-Type|application/json|
    And   endPoint for Get request "<END POINT>"
    And   Then the API call got success with status code "<StatusCode>"
    Examples:
      | END POINT              |StatusCode|
      |/maps/api/place/get/json|200       |


  Scenario Outline: Verify if Place is being succesfully added using UpdatePlaceAPI

    And   user enter quaryParam key value
      |Key|value|
      |keys|qaclick123|
    And   user enter header value
      |name        |value           |
      |Content-Type|application/json|
    And   endPoint for Put request "<END POINT>"
    And   user enters body for Update request as "<BODY>"
    And   Then the API call got success with status code "<StatusCode>"
    Examples:
     | END POINT                 |BODY|StatusCode|
     |/maps/api/place/update/json|UpdateBody.json|200|


 Scenario Outline: Edit JSON data and upload
    Given the JSON file is present in the project
    When  I edit the JSON data "<Key>" "<Value>"
    And   I upload the updated JSON file
    Then  the JSON file should be updated successfully
   Examples:
     |Key  |Value|
     |accuracy|150|
     |phone_number|9398540174|


  Scenario Outline: Verify if Place is being succesfully added using DeletePlaceAPI
#     Given BaseUrl for Delete "<Baseurl>"
      And   user enter quaryParam key value
      |Key|value|
      |keys|qaclick123|
      And   user enter header value

        |name        |value           |
        |Content-Type|application/json|
      And   endPoint for Delete request "<END POINT>"
      And   Then the API call got success with status code "<StatusCode>"
    Examples:
      | Baseurl                      | END POINT                 | StatusCode|
      |https://rahulshettyacademy.com|/maps/api/place/delete/json|200        |