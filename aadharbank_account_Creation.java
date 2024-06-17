package stepdef;

import io.cucumber.java.en.*;
import ReUseable.ReUseable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdbclearn.ReadAadharDB;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class aadharbank_account_Creation {

    String databaseName = "";
    String  adharNoFromPropertyFile ="";

    Boolean isAadharMatching;

    String fname ="";
    String lname ="";

    String address ="";

    String phone ="";
    String requestBody="";
    Response response;

    ReadAadharDB DBobj=new ReadAadharDB();

    @When("aadha_no from properties file matches with {string} database")
    public void aadha_no_from_properties_file_matches_with_database(String dbName) {
        databaseName = dbName;


        ReUseable readProprtyFileObj = new ReUseable();
        adharNoFromPropertyFile = readProprtyFileObj.read_Properties_file("id");
        isAadharMatching = DBobj.isAadharNumberCorrect(adharNoFromPropertyFile,dbName);
        if (isAadharMatching == true) {
            System.out.println("Aadha_no matches!");
        }
        else {
            System.out.println("Aadhar_no not matching!");
            System.exit(0);
        }
    }
    @Given("applicant firstname {string}, lastname {string}, address {string} and phone {string}")
    public void applicant_firstname_lastname_address_and_phone(String firstname, String lastname, String applicant_address, String applicant_phone) {
        fname = firstname;
        lname = lastname;
        address = applicant_address;
        phone = applicant_phone;
    }
    @Then("create request body for API call")
    public void create_request_body_for_api_call() {
        requestBody = "{\n" +
                "    \"Fname\": \""+fname+"\",\n" +
                "    \"Lname\": \""+lname+"\",\n" +
                "    \"Aadhar_No\": \""+adharNoFromPropertyFile+"\",\n" +
                "    \"Address\": \""+address+"\",\n" +
                "    \"Phone\": \""+phone+"\"\n" +
                "}";
    }
    @Then("send post call to create bank account with {string}")
    public void send_post_call_to_create_bank_account_with(String URL) {
        response = given().contentType(ContentType.JSON).body(requestBody).when().post(URL);
        System.out.println("API call to create new bank account is completed");
        System.out.println("Response body: " + response.getBody().asString());
    }
    @Then("read and Verify fname in response with DB")
    public void read_and_verify_fname_in_response_with_db() {
        String res_Fname = response.getBody().jsonPath().getString("Fname");
        String db_Fname = DBobj.readAadharTable(adharNoFromPropertyFile,"Fname");
        Assert.assertEquals(res_Fname,db_Fname);
    }
    @Then("Verify lname in response with DB")
    public void verify_lname_in_response_with_db() {
        String res_lname = response.getBody().jsonPath().getString("Lname");
        String db_lname = DBobj.readAadharTable(adharNoFromPropertyFile,"Lname");
        Assert.assertEquals(res_lname,db_lname);
    }
    @Then("Verify AadharNo in response with DB")
    public void verify_aadhar_no_in_response_with_db() {
        String res_Aadharno = response.getBody().jsonPath().getString("Aadhar_No");
        String db_Aadharno = DBobj.readAadharTable(adharNoFromPropertyFile,"Aadharno");
        Assert.assertEquals(res_Aadharno,db_Aadharno);
    }
    @Then("Verify Address in response with DB")
    public void verify_address_in_response_with_db() {
        String res_Address = response.getBody().jsonPath().getString("Address");
        String db_Address = DBobj.readAadharTable(adharNoFromPropertyFile,"Address");
        Assert.assertEquals(res_Address,db_Address);
    }
    @Then("match phone in response with DB")
    public void match_phone_in_response_with_db() {
        String res_Phone = response.getBody().jsonPath().getString("Phone");
        String db_Phone = DBobj.readAadharTable(adharNoFromPropertyFile,"Phone");
        Assert.assertEquals(res_Phone,db_Phone);
    }
    @Then("validate AccountID is created in response")
    public void validate_account_id_is_created_in_response() {
        boolean isAccountIdCreated = response.getBody().asString().contains("id");
        System.out.println("Is account id created: "+isAccountIdCreated);
        Assert.assertTrue(isAccountIdCreated);
    }
    @Then("validate AccountID is numeric")
    public void validate_account_id_is_numeric() {
        String res_id = response.getBody().jsonPath().getString("id");
        boolean idIsNumeric = res_id.chars().allMatch( Character::isDigit );
        System.out.println("Is Account Id numeric: "+idIsNumeric);
        Assert.assertTrue(idIsNumeric);

    }
    @Then("validate createAt in response and its date should be current date")
    public void validate_create_at_in_response_and_its_date_should_be_current_date() {
        String createAt = response.getBody().jsonPath().getString("createdAt");
        ReUseable ReUseable= new ReUseable();
        String expectedDate = ReUseable.getCurrentDate();
        Assert.assertTrue(createAt.contains(expectedDate));
    }
}
