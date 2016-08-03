
public class exception {

}private void demonstrate() {
    IntStream ones = IntStream.generate(() -> 1);
    ones.map(i -> {
        try {
            return riskyTask(i);
        } catch (PrinterException e) {
            System.out.println(
                "Your printer is out of ink or laser beams!"
            );
        }
        return i;
    });
}

private int ri
One does not have to use lambdas in Java 8 long before running into the obstacle of checked Exceptions. Because the PrinterException is checked, the compiler forces us to deal with it, even within a lambda:

skyTask(int a) throws PrinterException {
    return 2;
}
