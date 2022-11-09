package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderClient orderClient;
    @Before
    public void setUp() {
        orderClient= new OrderClient();
    }
    @Test
    @DisplayName("Return order list")

    public void orderListReturns() {
        ValidatableResponse responseOrder = orderClient.getList();
        ArrayList<String> orderBody = responseOrder.extract().path("orders");
        assertNotEquals(Collections.EMPTY_LIST, orderBody);
    }
}

