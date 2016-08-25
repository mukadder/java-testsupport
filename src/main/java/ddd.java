
public class ddd {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		public static void main(String[] args){
	        getUnsafe().throwException(new IOException());
	    }

	    private static Unsafe getUnsafe(){
	        try {
	            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
	            theUnsafe.setAccessible(true);
	            return (Unsafe) theUnsafe.get(null);
	        } catch (Exception e) {
	            throw new AssertionError(e);
	        }
	    }

	}

}
