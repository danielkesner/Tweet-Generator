package util;

public class StringUtil {
	
	public boolean isNumber(String s) {
		
		try {
			Integer.parseInt(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
