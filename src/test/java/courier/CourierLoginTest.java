package courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CourierLoginTest {
    private CourierInvalidCredentials courierInvalidCredentials;
    private CourierCredentials courierCredentials;
    private CreateCourier createCourier;
    private CourierRequests courierRequests;
    private CourierDelete courierDelete;
    int courierId;
    @Before
    public void setUp() {
        courierRequests = new CourierRequests();
    }
    @Step("Create courier and return login")
    public void createCourier() {
        createCourier = new CreateCourier("maryPopins", "1234", "mary");
        courierRequests.create(createCourier);
    }
    @Step("Delete courier")
    public void deleteCourier() {
        courierCredentials = new CourierCredentials("maryPopins", "1234");
        ValidatableResponse loginResponse = courierRequests.login(courierCredentials);
        int loginId = loginResponse.extract().path("id");
        String idLogin = String.valueOf(loginId);
        courierDelete = new CourierDelete(idLogin);
        courierRequests.delete(courierDelete, loginId);
    }

    @Test
    @DisplayName("Login courier with valid credential")
    public void courierAuthorization() {
        courierCredentials = new CourierCredentials("maryPopins", "1234");
        createCourier();
        ValidatableResponse loginResponse = courierRequests.login(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertThat("Courier cannot login", statusCode, equalTo(SC_OK));
        assertThat("Courier cannot login", courierId, is(not(0)));
        deleteCourier();
    }

    @Test
    @DisplayName("Login courier without password")
    public void courierAuthorizationErrorWithWrongData() {
        courierCredentials = new CourierCredentials("maryPopins", "");
        ValidatableResponse loginResponse = courierRequests.login(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        String messageText = loginResponse.extract().path("message");
        assertThat("Success login, Enough data to login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Success login, Enough data to login", messageText, is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier authorization return not found")
    public void  courierAccountNotFoundOrNotExist() {
        courierCredentials = new CourierCredentials("maryPopin", "123");
        ValidatableResponse loginResponse = courierRequests.login(courierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        String messageText = loginResponse.extract().path("message");
        assertThat("Success login, Account found", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Success login, Account found", messageText, is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier without login")
    public void courierLoginWithoutLogin() {
        courierInvalidCredentials = new CourierInvalidCredentials("1234" );
        ValidatableResponse loginResponse = courierRequests.invalidLogin(courierInvalidCredentials);
        int statusCode = loginResponse.extract().statusCode();
        String messageText = loginResponse.extract().path("message");
        assertThat("Success login, Enough data to login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Success login, Enough data to login", messageText, is("Недостаточно данных для входа"));
    }
}

