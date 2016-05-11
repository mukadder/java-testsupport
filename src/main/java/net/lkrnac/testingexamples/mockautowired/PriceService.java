package net.lkrnac.testingexamples.mockautowired;

import org.springframework.stereotype.Service;
//Here is second dependency of testing class.
//It is also Spring component. This class will be spied (partially mocked) in test
//Its method calculatePriceForOrder will be invoked unchanged. Second method will be stubbed.
@Service
public class PriceService {
	public int getActualPrice(Item item){
		throw new UnsupportedOperationException("Fail is not mocked!");
	}
	
	public int calculatePriceForOrder(Order order){
		int orderPrice = 0;
		for (Item item : order.getItems()){
			orderPrice += getActualPrice(item);
		}
		return orderPrice;
	}
}
