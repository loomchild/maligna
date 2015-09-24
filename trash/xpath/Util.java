package align.util;

import static loomchild.util.Utils.readAll;
import static loomchild.util.res.Util.getResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import loomchild.util.exceptions.IORuntimeException;

public class Util {

	public static String readAllFromResource(String resourceName) {
		try {
			String fileName = getResource(resourceName);
			Reader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "utf-8"));
			String text = readAll(reader);
			return text;
		} catch (UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new IORuntimeException(e);
		}
	}

}
