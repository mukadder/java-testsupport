package net.lkrnac.testingexamples.mockautowired;

import org.springframework.stereotype.Repository;
//t is Spring singleton bean. This class will be mocked in the test.
@Repository
public class OrderDao {
	public Order getOrder(int irderId){
		throw new UnsupportedOperationException("Fail is not mocked!");
	}
}
