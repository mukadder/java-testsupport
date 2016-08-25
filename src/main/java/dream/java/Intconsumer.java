package dream.java;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Intconsumer {
	private static Random generator = new Random();
	public static void main(String[] args) {
		repeat(10, i -> System.out.println("Countdown: " + (9 - i)));
		BufferedImage frenchFlag = createImage(150, 100,
				(x, y) -> x < 50 ? Color.BLUE : x < 100 ? Color.WHITE : Color.RED);
		repeatMessage("Hello"+Thread.currentThread(), 1000); // Prints Hello 1000 times in a separate thread
	}
	public static void repeatMessage(String text, int count) {
		Runnable r = () -> {
		for (int i = 0; i < count; i++) {
		System.out.println(text);
		}
		};
		new Thread(r).start();
		}
	
	 public  static BufferedImage createImage(int width, int height, PixelFunction f) {
		BufferedImage image = new BufferedImage(width, height,
		BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++)
		for (int y = 0; y < height; y++) {
		Color color = f.apply(x, y);
		image.setRGB(x, y, color.getRGB());
		}
		return image;
		}
	public static void repeat(int n, IntConsumer action) {
		for (int i = 0; i < n; i++) action.accept(i);
		}
	public interface IntConsumer {
		void accept(int value);
		}
	@FunctionalInterface
	public interface PixelFunction {
	Color apply(int x, int y);
	}
	
	public interface IntSequence {
		public static IntSequence randomInts(int low, int high) {
			class RandomSequence implements IntSequence {
			public int next() { return low + generator.nextInt(high - low + 1); }
			public boolean hasNext() { return true; }
			}
			return new RandomSequence();
			}
	}
}
