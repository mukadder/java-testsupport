
The palest ink is better than the best memory
My understanding of being a good software engineer

Sunday, 28 June 2015
Use Java 8 Optional and Stream to completely remove if else statements
There have been some articles on how to use Java 8 Optional to avoid making verbose checking of nulls in the code.

This article shows a way to combine Java 8 Optional and Stream to completely remove if else statements.

Let's look at some code:
public class ExpressionBuilder {

    public String buildExpression(List<Integer> list1, List<Integer> list2) {

        String expression = "";

        String list1Expression = buildList1Expression(list1);
        String list2Expression = buildList2Expression(list2);

        if (list1Expression == null && list2Expression != null) {
            expression = list2Expression;
        }

        if (list1Expression != null && list2Expression == null) {
            expression = list1Expression;
        }

        if (list1Expression != null && list2Expression != null) {
            expression = list1Expression + " and " + list2Expression;
        }

        return expression;

    }

    private String buildList1Expression(List<Integer> list1) {
        String expression = null;
        if (list1 != null && !list1.isEmpty()) {
            expression = "(";
            for (int i = 0; i < list1.size(); i++) {
                if (i != list1.size() - 1) {
                    expression = expression + "list1:" + list1.get(i) + " or ";
                } else {
                    expression = expression + "list1:" + list1.get(i);
                }
            }
            expression = expression + ")";
        }
        return expression;
    }

    private String buildList2Expression(List<Integer> list2) {
        String expression = null;
        if (list2 != null && !list2.isEmpty()) {
            expression = "!(";
            for (int i = 0; i < list2.size(); i++) {
                if (i != list2.size() - 1) {
                    expression = expression + "list2:" + list2.get(i) + " or ";
                } else {
                    expression = expression + "list2:" + list2.get(i);
                }
            }
            expression = expression + ")";
        }
        return expression;
    }

}
The code above will convert two list of Integers into a String. For example, if you have:

list1 = 10,  20,  30
list2 = 40,  50,  60,  70

The result would be:

"(list1:10 or list1:20 or list1:30) and !(list2:40 or list2:50 or list2:60 or list2:70)"

If you only have list1:

list1 = 10, 20, 30

Then the result would be:

"(list1:10 or list1:20 or list1:30)"

If you only have list2:

 list2 = 40,  50,  60,  70

Then the result would be:

"!(list2:40 or list2:50 or list2:60 or list2:70)" 

 The code needs to handle the following cases:
list1 is null
list1 is empty
list1 has only 1 element
list2 is null
list2 is empty
list2 has only 1 element
Therefore, we can see there are a lot of if and else statements in the code which makes it hard to read and prone to bugs.

Let's look how Java 8 Optional and Stream can help in this case:
public class Java8ExpressionBuilder {

 public String buildExpression(List<Integer> list1, List<Integer> list2) {
  return Stream.of(buildList1Expression(list1), buildList2Expression(list2))
               .filter(op -> op.isPresent())
               .map(op -> op.get())
               .reduce((result, s) -> result + " and " + s).orElse("");
 }

 private Optional<String> buildList1Expression(List<Integer> list1) {
  return Optional.ofNullable(list1).flatMap(list -> list.stream()
                 .map(i -> "list1:" + i)
                 .reduce((result, s) -> result + " or " + s)
                 .map(s -> "(" + s + ")"));

 }

 private Optional<String> buildList2Expression(List<Integer> list2) {
  return Optional.ofNullable(list2).flatMap(list -> list.stream()
                 .map(i -> "list2:" + i)
                 .reduce((result, s) -> result + " or " + s)
                 .map(s -> "!(" + s + ")"));
 }
}
A couple of things worth mentioning here: 
The reduce method is very good for concatenating a list of elements into a single String.
Put two Optionals into a Stream so that Stream Api and Optional Api can be used together.
However, the time complexity of the above algorithm is O(N2)O(N2) which is not very efficient as string concatenations are used. To fix this problem, we can use StringBuilder:
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Java8ExpressionBuilder {

    public String buildExpression(List<Integer> list1, List<Integer> list2) {
        return Stream.of(buildList1Expression(list1), buildList2Expression(list2))
                .filter(op -> op.isPresent()).map(op -> op.get())
                .reduce((result, s) -> result + " and " + s).orElse("");

    }

    private Optional<String> buildList1Expression(List<Integer> list1) {
        return Optional.ofNullable(list1).flatMap(
                list -> list.stream().map(i -> new StringBuilder("list1:")
                        .append(i)).reduce((result, s) -> result.append(" or ").append(s))
                        .map(s -> "(" + s.toString() + ")"));

    }

    private Optional<String> buildList2Expression(List<Integer> list2) {
        return Optional.ofNullable(list2).flatMap(
                list -> list.stream().map(i -> new StringBuilder("list2:")
                        .append(i)).reduce((result, s) -> result.append(" or ").append(s))
                        .map(s -> "!(" + s.toString() + ")"));
    }
}

Posted by Ke Wang at 04:20 
Email This
BlogThis!
Share to Twitter
Share to Facebook
Share to Pinterest

No comments:
Post a Comment
 
Newer Post Older Post Home
Subscribe to: Post Comments (Atom)
About Me

Ke Wang  

View my complete profile
Blog Archive

▼  2015 (4)
►  July (2)
▼  June (2)
Use Java 8 Optional and Stream to completely remov...
What is the maximum number of nodes in a binary tr...
Simple template. Powered by Blogger.
