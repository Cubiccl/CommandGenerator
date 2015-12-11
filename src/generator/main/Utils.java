package generator.main;

public class Utils
{

	public static String toString(String[] array, String separator)
	{
		String string = "";
		for (int i = 0; i < array.length; i++)
		{
			if (i != 0) string += separator;
			string += array;
		}
		return string;
	}

}
