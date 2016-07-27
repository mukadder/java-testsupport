package chameli.ki.shadi;

import java.util.Arrays;
/*
// this class uses thred local  demo in the applications' http://veerasundar.com/blog/2010/11/java-thread-local-how-to-use-and-code-sample/
public class context {
	public final Histogram buildHistogram(String inputs) {
	    Histogram histogram = new Histogram();
	    // this takes a simple input
	    Arrays.stream(inputs.trim().split(" "))
	            .filter(s -> s.length() < 3)
	            .map(String::toLowerCase)
	            .map(this::getToken)
	            .forEach(histogram::add);
	    return histogram;
	}
	public void getToken(String str){
		//return str;
		
	}
	public final Histogram buildHistogram(String inputs) {
	    Histogram histogram = new Histogram();
	    // this takes a simple input
	    Arrays.stream(inputs.trim().split(" "))
	            .map(this::evaluateMappings)
	            .filter(this::evaluatePredicates)
	            .map(s -> getToken(s))
	            .forEach(histogram::add);
	    return histogram;
	}
	private String evaluateMappings(final String s) {
	    String text = s;
	    for (Function<String, String> f : getMappings()) {
	        text = f.apply(text);
	        if (s == null || s.isEmpty()) {
	            return "";
	        }
	    }
	    return text;
	}
	private boolean evaluatePredicates(String s) {
	    if (s == null || s.isEmpty()) {
	        return false;
	    }
	    for (Predicate<String> p : getPredicates()) {
	        if (!p.test(s)) {
	            return false;
	        }
	    }
	    return true;
	}
	protected final Predicate<String> minLengthFilter = text -> text.length() > 2;

	@SuppressWarnings("unchecked")
	protected Predicate<String>[] getPredicates() {
	    return new Predicate[]{};
	}

	protected final Function<String, String> toAlphanumeric = s -> {
	    StringBuilder sb = new StringBuilder();
	    for (char ch : s.toCharArray()) {
	        if (Character.isLetterOrDigit(ch)) {
	            sb.append(Character.toLowerCase(ch));
	        }
	    }
	    return sb.toString();
	};
	protected final Function<String, String> toTrimmedForm = this::normalized;

	@SuppressWarnings("unchecked")
	protected Function<String, String>[] getMappings() {
	    return new Function[]{toTrimmedForm, toAlphanumeric,};
	}
	public class StemmingTokenizer extends Tokenizer {
	    final static Set<String> stopWords;
	    static { 
	        /* Code to initialize the stop words goes here, but isn't copied
	        stopWords=new HashSet<>();
	    }

	    Predicate<String> stopWordsFilter=s -> !stopWords.contains(s);

	    @SuppressWarnings("unchecked")
	    @Override
	    protected Predicate<String>[] getPredicates() {
	        return new Predicate[]{minLengthFilter, stopWordsFilter,};
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    protected Function<String, String>[] getMappings() {
	        return new Function[]{toTrimmedForm, toAlphanumeric};
	    }
	}


}*/
