import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Optional;
public class Testalambda {
	static String sentence =" Whats up Leonne";
	public static void main(String[] args){
		
   System.out.println(countCapitalLetterWithLambda(sentence));
	Optional<Character>	firstcapitalleter=getfirstCapitalLetterWithLambda(sentence);
	firstcapitalleter.ifPresent(System.out::println);
		
	}

	private static Optional<Character> getfirstCapitalLetterWithLambda(String sentence2) {
		for(char c:sentence.toCharArray()){
			if(c >= 'A' && c <= 'Z'){
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}

	private static int countCapitalLetterWithLambda(String sentence) {
		Predicate<Integer> isBetweenAZ = c -> c >= 'A' && c <= 'Z';
		return (int) sentence.codePoints().mapToObj(Integer::valueOf)
				
				//.peek(System.out::println)
				.filter(isBetweenAZ).count();

	}
	
}
