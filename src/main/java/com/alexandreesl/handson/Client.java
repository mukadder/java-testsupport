package com.alexandreesl.handson;

import java.util.List;

public class Client {

	private String name;

	private Long phone;

	private String sex;

	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void markClientSpecial() {

		System.out.println("The client " + getName() + " is special! ");

	}

}
