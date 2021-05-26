package org.mybatis.utility;

public class StringUtils {
	public static boolean TRUE = true;

	public static boolean isBlank(String parameter) {
		int length;
		if ((parameter == null) || ((length = parameter.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; ++i) {
			if (!(Character.isWhitespace(parameter.charAt(i)))) {
				return false;
			}
		}

		return true;
	}
}
