package net.lkrnac.testingexamples.mockautowired;

import java.util.Arrays;

import junit.framework.Assert;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderServiceTest {
	private static final int TEST_ORDER_ID = 15;
	private static final int TEST_SHOES_PRICE = 2;   
	private static final int TEST_SHIRT_PRICE = 1;
	//@InjectMocks - Instantiates testing object instance and tries to inject fields annotated with
	@InjectMocks
	private OrderService testingObject;
	//@Mock or @Spy into private fields of testing object
	//@Spy - Creates spy for instance of annotated field

	@Spy
	private PriceService priceService;
	//@Mock - Creates mock instance of the field it annotates
	@Mock
	private OrderDao orderDao;
	//First of all TestNG framework picks up @BeforeMethodannotation and invokes initMocks method
	//This method invokes special Mockito call (MockitoAnnotations.initMocks(this)) to initialize 
	//annotated fields. Without this call, these objects would be null.
	//Common mistake with this approach is to forget this invocation.
	//When all the test fields are populated with desired values, test is called.
	@BeforeMethod
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetOrderService(){
		Order order = new Order(Arrays.asList(Item.SHOES, Item.SHIRT));
		Mockito.when(orderDao.getOrder(TEST_ORDER_ID)).thenReturn(order);
		
		//notice different Mockito syntax for spy
		Mockito.doReturn(TEST_SHIRT_PRICE).when(priceService).getActualPrice(Item.SHIRT);
		Mockito.doReturn(TEST_SHOES_PRICE).when(priceService).getActualPrice(Item.SHOES);
		
		//call testing method
		int actualOrderPrice = testingObject.getOrderPrice(TEST_ORDER_ID);
		
		Assert.assertEquals(TEST_SHIRT_PRICE + TEST_SHOES_PRICE, actualOrderPrice);
	}
}
