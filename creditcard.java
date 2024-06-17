package stepdef;

import io.cucumber.java.en.*;
import ReUseable.ReUseable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdbclearn.ReadAadharDB;
import org.junit.Assert;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;


public class creditcard {

    String Credit_Card_Number ="";

    ReadAadharDB obj = new ReadAadharDB();

    String name ="";
    String Year ="";
    String Limit ="";
    String expdate="";
    String cardtype="";
    String requestbody="";
    Response response;

    String Credit_card_number_db="";
    String URL="https://api.restful-api.dev/objects";


    @Given("Retrieve the Credit card data from excel for each Testcase {string}")
    public void retrieve_the_credit_card_data_from_excel_for_each_testcase(String TCname) {
        ReUseable Credit_excel = new ReUseable();
        Credit_Card_Number = Credit_excel.read_And_Print_XL_AsPerTestData(TCname,"Credit Card Number" );
        System.out.println("Cerdir123"+Credit_Card_Number);
    }

    @When("Get the credit card information from Database")
    public void get_the_credit_card_information_from_database() {
        Credit_card_number_db = obj.readCreditTable(Credit_Card_Number,"Creditcardnumber");
          name = obj.readCreditTable(Credit_Card_Number,"Name");
        Year = obj.readCreditTable(Credit_Card_Number,"year");
        Limit = obj.readCreditTable(Credit_Card_Number,"Limits");
        expdate = obj.readCreditTable(Credit_Card_Number,"expdate");
        cardtype = obj.readCreditTable(Credit_Card_Number,"cardtype");
        System.out.println("kani" +name);
        System.out.println("from DB"+Credit_card_number_db);
        System.out.println("from DByear"+Year);
        System.out.println("from DByear"+Limit);
        System.out.println("from DByear"+expdate);
        System.out.println("from DByear"+cardtype);
    }
    @When("Generate a request body")
    public void generate_a_request_body() {
        requestbody = "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"data\": {\n" +
                "        \"year\": "+Year+",\n" +
                "        \"CreditCardNumber\": \""+Credit_Card_Number+"\",\n" +
                "        \"Limit\": \""+Limit+"\",\n" +
                "        \"EXPDate\": \""+expdate+"\",\n" +
                "        \"CardType\": \""+cardtype+"\"\n" +
                "    }\n" +
                "}";


    }
    @Then("Do a post call and validate the response of an API.")
    public void do_a_post_call_and_validate_the_response_of_an_api() {
        response = given().contentType(ContentType.JSON).body(requestbody).when().post(URL);
        System.out.println("API call to create Credit card is completed");
        System.out.println("Response body: " + response.getBody().asString());

        String name_res = response.getBody().jsonPath().getString("name");
        Assert.assertEquals(name,name_res);

        String year_res = response.getBody().jsonPath().getString("data.year");
        Assert.assertEquals(Year,year_res);

        String CreditCardNumber_res = response.getBody().jsonPath().getString("data.CreditCardNumber");
        Assert.assertEquals(Credit_card_number_db,CreditCardNumber_res);

        String Limit_res = response.getBody().jsonPath().getString("data.Limit");
        Assert.assertEquals(Limit,Limit_res);

        String EXPDate_res = response.getBody().jsonPath().getString("data.EXPDate");
        Assert.assertEquals(expdate,EXPDate_res);

        String CardType_res = response.getBody().jsonPath().getString("data.CardType");
        Assert.assertEquals(cardtype,CardType_res);


    }

    @Then("Validate Credit card number is Mapped with a PAN card in DB")
    public void validate_credit_card_number_is_mapped_with_a_pan_card_in_db() {
       String pannumber = obj.readPANTable(Credit_Card_Number,"pancardnumber");
        System.out.println("mapped PAN number: "+pannumber);
    }
}
