package com.alexandreesl.handson;

import java.util.List;
import java.util.stream.Collectors;

public class StreamsShowcase {

	public static void main(String[] args) {

		List<Client> clients = CollectionUtils.getData();

		// for each that doesn't use the stream

		System.out.println("FOR EACH WITHOUT THE STREAM!");

		clients.stream().filter(
				c -> c.getName().equals("Alexandre Eleuterio Santos Lourenco"));

		clients.forEach(c -> System.out.println(c.getName()));

		// for each using the stream

		System.out.println("FOR EACH WITH THE STREAM!");

		clients.stream()
				.filter(c -> c.getName().equals(
						"Alexandre Eleuterio Santos Lourenco"))
				.forEach(c -> System.out.println(c.getName()));

		// filtering the stream and retrieving

		System.out.println("FILTERING THE STREAM AND RETRIEVING!");

		List<Client> filteredList = clients
				.stream()
				.filter(c -> c.getName().equals(
						"Alexandre Eleuterio Santos Lourenco"))
				.collect(Collectors.toList());

		filteredList.forEach(c -> System.out.println(c.getName()));

		// stream filtering by a substream and maping only the names

		System.out.println("USING THE MAP METHOD!");

		clients.stream()
				.filter(c -> c.getOrders().stream()
						.anyMatch(o -> o.getTotal() > 90)).map(Client::getName)
				.forEach(System.out::println);

		// stream geting the max value from the orders

		System.out.println("RETRIEVING THE NAME AND HIGHEST TOTAL!");

		clients.stream().forEach(
				c -> System.out.println("Name: "
						+ c.getName()
						+ " Highest Order Total: "
						+ c.getOrders().stream().mapToDouble(Order::getTotal)
								.max().orElse(0)));

		// streams using peek() to see what is happening

		System.out.println("INSPECTING THE STREAM WITH PEEK!");

		clients.stream()
				.filter(c -> c.getName().equals(
						"Alexandre Eleuterio Santos Lourenco"))
				.peek(System.out::println);

		System.out.println("*********** SECOND PEEK TEST ******************");

		clients.stream()
				.filter(c -> c.getName().equals(
						"Alexandre Eleuterio Santos Lourenco"))
				.peek(System.out::println)
				.forEach(c -> System.out.println(c.getName()));

		// stream with a custom reduce

		System.out.println("STREAM WITH THE VALUES SUBTRACTED!");

		clients.stream().forEach(
				c -> System.out.println("Name: "
						+ c.getName()
						+ " TOTAL SUBTRACTED: "
						+ c.getOrders().stream().mapToDouble(Order::getTotal)
								.reduce(0, (a, b) -> a - b)));

		// parallel streams

		System.out.println("PARALLEL STREAMS!");

		clients.parallelStream()
				.filter(c -> c.getOrders().stream()
						.anyMatch(o -> o.getTotal() > 90)).map(Client::getName)
				.forEach(System.out::println);

	}
}
