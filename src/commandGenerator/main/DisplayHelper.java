package commandGenerator.main;

import java.awt.Component;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.SavedObjects;

public class DisplayHelper
{

	public static String askObjectName(byte type)
	{
		String name = "name";
		do
		{
			if (name.equals("") || name.contains(" ")) showWarning("WARNING:name");
			if (SavedObjects.getList(type).containsKey(name)) showWarning("WARNING:name_already");
			name = JOptionPane.showInputDialog(null, Generator.translate("GENERAL:name"));
			if (name == null) return null;
		} while (name.contains(" ") || name.equals("") || SavedObjects.getList(type).containsKey(name));
		return name;
	}

	/** Returns a String version of the selector to be displayed to the user.
	 * 
	 * @param selector
	 *            - <i>String[]</i> - The Selector to display. */
	public static String displaySelector(String[] selector)
	{
		String display = selector[0] + " =";
		if (Boolean.parseBoolean(selector[2])) display += "!";
		display += " " + selector[1];
		return display;
	}

	/** Returns the current time as a String. */
	public static String getTime()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return "[" + strDate + "]  ";
	}

	/** Adds some text to the log file. Also prints it onto the console.
	 * 
	 * @param text
	 *            - <i>String</i> - The text to add. */
	public static void log(String text)
	{
		System.out.println(getTime() + text);
		try
		{
			List<String> prev = new ArrayList<String>();
			Scanner scanner = new Scanner(new File(Resources.folder + "log.txt"));
			while (scanner.hasNextLine())
				prev.add(scanner.nextLine());
			scanner.close();

			Writer writer = new PrintWriter(Resources.folder + "log.txt");
			for (int i = 0; i < prev.size(); i++)
			{
				writer.write(prev.get(i) + "\n");
			}
			writer.write(getTime() + text + "\n");
			writer.close();
		} catch (Exception e)
		{}

	}

	/** Warns about a missing texture.
	 * 
	 * @param url
	 *            - <i>String</i> - The URL of the missing texture. */
	public static void missingTexture(String url)
	{
		DisplayHelper.log("Missing texture : " + url);
	}

	/** Shows the last changelog to the user. */
	public static void showChangelog()
	{
		JOptionPane.showMessageDialog(null, Resources.changelog(Resources.versions[Resources.versions.length - 1]).replaceAll("<br />", "\n"),
				Generator.translate("GENERAL:changelog"), JOptionPane.PLAIN_MESSAGE);
	}

	/** Shows an Dialog Pane containing a Help message.
	 * 
	 * @param text
	 *            - <i>String</i> - The text to show
	 * @param title
	 *            - <i>String</i> - The title of the dialog. */
	public static void showHelp(String text, String title)
	{
		JOptionPane.showMessageDialog(null, text, Generator.translate("HELP:title") + " : " + title, JOptionPane.INFORMATION_MESSAGE);
	}

	/** Shows a message in an Option Panel.
	 * 
	 * @param item
	 *            - <i>Component</i> - The Component to show.
	 * @param title
	 *            - <i>String</i> - The title of the dialog. */
	public static void showMessage(Component item, String title)
	{
		JOptionPane.showMessageDialog(null, item, title, JOptionPane.PLAIN_MESSAGE);
	}

	/** Shows a message in an Option Panel. Returns true if the user canceled.
	 * 
	 * @param item
	 *            - <i>Component</i> - The Component to show.
	 * @param title
	 *            - <i>String</i> - The title of the dialog. */
	public static boolean showQuestion(Component item, String title)
	{
		return JOptionPane.showConfirmDialog(null, item, title, JOptionPane.OK_CANCEL_OPTION) == 2;
	}

	/** Shows a warning in an Option Panel.
	 * 
	 * @param textId
	 *            - <i>String</i> - The ID of the warning message to show. */
	public static void showWarning(String textId)
	{
		log(Generator.translate(textId));
		JOptionPane.showMessageDialog(null, Generator.translate(textId), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	public static String[] splitCommand(String command)
	{
		List<String> elements = new ArrayList<String>();
		String[] base = command.split(" ");

		int realIndex = 0;
		boolean inString = false;
		for (int i = 0; i < base.length; i++)
		{
			for (int j = 0; j < base[i].length(); j++)
			{

				if (base[i].charAt(j) == '"')
				{
					if (j == 0) inString = !inString;
					else if (base[i].charAt(j - 1) != '\\') inString = !inString;
				}
			}
			if (elements.size() <= realIndex) elements.add(base[i]);
			else elements.set(realIndex, elements.get(realIndex) + " " + base[i]);
			if (!inString) realIndex++;
		}

		return elements.toArray(new String[0]);
	}

	/** Displays an error warning the user that he should use an integer between min and max. */
	public static void warningBounds(double min, double max)
	{
		log(Generator.translate("WARNING:number_bound").replaceAll("<min>", String.valueOf(min)).replaceAll("<max>", String.valueOf(max)));
		JOptionPane.showMessageDialog(null, Generator.translate("WARNING:number_bound").replaceAll("<min>", String.valueOf(min)).replaceAll("<max>", String.valueOf(max)),
				Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);

	}

	/** Displays an error warning the user that he should use an integer. */
	public static void warningInteger()
	{
		log(Generator.translate("WARNING:integer"));
		JOptionPane.showMessageDialog(null, Generator.translate("WARNING:integer"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Displays an error warning the user that he should use a name without space. */
	public static void warningName()
	{
		log(Generator.translate("WARNING:name"));
		JOptionPane.showMessageDialog(null, Generator.translate("WARNING:name"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Displays an error warning the user that he should use a number. */
	public static void warningNumber()
	{
		log(Generator.translate("WARNING:number"));
		JOptionPane.showMessageDialog(null, Generator.translate("WARNING:number"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Displays an error warning the user that he should use a positive integer. */
	public static void warningPositiveInteger()
	{
		log(Generator.translate("WARNING:positive_integer"));
		JOptionPane.showMessageDialog(null, Generator.translate("WARNING:positive_integer"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

}