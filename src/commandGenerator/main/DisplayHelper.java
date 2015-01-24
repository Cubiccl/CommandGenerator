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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DisplayHelper
{

	/** Displays the selector in the selector list. */
	public static String displaySelector(String[] selector)
	{
		return selector[0] + "=" + selector[1];
	}

	/** Displays an error warning the user that he should use a positive integer. */
	public static void warningPositiveInteger()
	{
		log(Lang.get("WARNING:positive_integer"));
		JOptionPane.showMessageDialog(null, Lang.get("WARNING:positive_integer"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Shows an Option Pane containing a Help message. */
	public static void showHelp(String text, String title)
	{
		JOptionPane.showMessageDialog(null, text, Lang.get("HELP:title") + " : " + title, JOptionPane.INFORMATION_MESSAGE);
	}

	/** Displays an error warning the user that he should use a name without space. */
	public static void warningName()
	{
		log(Lang.get("WARNING:name"));
		JOptionPane.showMessageDialog(null, Lang.get("WARNING:name"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Displays an error warning the user that he should use an integer. */
	public static void warningInteger()
	{
		log(Lang.get("WARNING:integer"));
		JOptionPane.showMessageDialog(null, Lang.get("WARNING:integer"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Prints a missing texture onto the console. */
	public static void missingTexture(String url)
	{
		DisplayHelper.log("Missing texture : " + url);
	}

	/** Displays an error warning the user that he should use a number. */
	public static void warningNumber()
	{
		log(Lang.get("WARNING:number"));
		JOptionPane.showMessageDialog(null, Lang.get("WARNING:number"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	/** Displays an error warning the user that he should use an integer between min and max. */
	public static void warningInteger(int min, int max)
	{
		log(Lang.get("WARNING:integer_bound").replaceAll("<min>", Integer.toString(min)).replaceAll("<max>", Integer.toString(max)));
		JOptionPane.showMessageDialog(null,
				Lang.get("WARNING:integer_bound").replaceAll("<min>", Integer.toString(min)).replaceAll("<max>", Integer.toString(max)),
				Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);

	}

	/** Shows the last changelog to the user. */
	public static void showChangelog()
	{
		JOptionPane.showMessageDialog(null, Resources.changelog(Resources.versions[Resources.versions.length - 1]).replaceAll("<br />", "\n"),
				Lang.get("GENERAL:changelog"), JOptionPane.PLAIN_MESSAGE);
	}

	/** Shows a message in an Option Panel. Returns true if the user canceled. */
	public static boolean showQuestion(Component item, String title)
	{
		return JOptionPane.showConfirmDialog(null, item, title, JOptionPane.OK_CANCEL_OPTION) == 2;
	}

	public static void showWarning(String textId)
	{
		log(Lang.get(textId));
		JOptionPane.showMessageDialog(null, Lang.get(textId), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
	}

	public static String askStringFromCombobox(String name, String description, String[] choices)
	{
		JPanel panelS = new JPanel();
		JLabel label = new JLabel(description);
		JComboBox<String> box = new JComboBox<String>(choices);

		panelS.add(label);
		panelS.add(box);

		if (DisplayHelper.showQuestion(panelS, name)) return null;
		return (String) box.getSelectedItem();
	}

	public static void showMessage(Component item, String title)
	{
		JOptionPane.showMessageDialog(null, item, title, JOptionPane.PLAIN_MESSAGE);
	}

	public static void log(String txt)
	{
		System.out.println(getTime() + txt);
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
			writer.write(getTime() + txt + "\n");
			writer.close();
		} catch (Exception e)
		{}

	}

	public static String getTime()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return "[" + strDate + "]  ";
	}

}