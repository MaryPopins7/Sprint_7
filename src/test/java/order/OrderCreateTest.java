package order;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.notNullValue;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    Faker faker = new Faker();
    private final String[] color;
    private OrderCreate orderCreate;
    private OrderClient orderClient;

    public OrderCreateTest(String[] color) {

        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GREY", "BLACK"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Create order")
    @Description("Send post request to /api/v1/orders")
    public void createOrderWithDifferentColors() {
        orderCreate = new OrderCreate(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.number().digit(),
                faker.phoneNumber().phoneNumber(),
                faker.number().randomDigitNotZero(),
                "2022-05-11",
                faker.backToTheFuture().quote(),
                color);
        ValidatableResponse response = orderClient.create(orderCreate);
        response.assertThat().body("track", notNullValue());

    }
}

