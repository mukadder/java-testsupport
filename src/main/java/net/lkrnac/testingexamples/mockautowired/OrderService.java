package net.lkrnac.testingexamples.mockautowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//And here is class under test. It  autowires dependencies above.


@Service
public class OrderService {
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private OrderDao orderDao;
	
	public int getOrderPrice(int orderId){
		Order order = orderDao.getOrder(orderId);
		return priceService.calculatePriceForOrder(order);
	}
}
