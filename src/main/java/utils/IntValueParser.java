package utils;

public class IntValueParser {

	public static int getIntValue(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch (Exception e) {
		}
		return -1;
	}
}
