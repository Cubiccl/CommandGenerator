package generator.main;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.gui.button.CButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JPanel;

/** Contains various constants and and methods. */
public final class Utils
{
	/** Background Color of a Component being clicked. */
	public static final Color BACKGROUND_CLICKED = new Color(250, 220, 220);
	/** Background Color of a Component being hovered. */
	public static final Color BACKGROUND_HOVERED = new Color(220, 250, 220);
	/** Background Color of a regular Component. */
	public static final Color BACKGROUND_NORMAL = new Color(220, 220, 250);
	/** Border Color of a Component. */
	public static final Color BORDER_COLOR = Color.GRAY;
	/** Font to be used by most Components. */
	public static final Font font = new Font("Dialog", Font.BOLD, 13);

	/** Object types :
	 * <ul>
	 * <li>ITEM = 0;</li>
	 * <li>BLOCK = 1;</li>
	 * <li>ENTITY = 2;</li>
	 * <li>EFFECT = 3;</li>
	 * <li>ENCHANTMENT = 4;</li>
	 * <li>ACHIEVEMENT = 5;</li>
	 * <li>ATTRIBUTE = 6;</li>
	 * <li>PARTICLE = 7;</li>
	 * <li>SOUND = 8;</li>
	 * <li>COMMAND = 100;</li>
	 * <li>STRUCTURE = 101;</li>
	 * </ul> */
	public static final int ITEM = 0, BLOCK = 1, ENTITY = 2, EFFECT = 3, ENCHANTMENT = 4, ACHIEVEMENT = 5, ATTRIBUTE = 6, PARTICLE = 7, SOUND = 8,
			COMMAND = 100, STRUCTURE = 101;
	/** Names for Objects types. */
	public static final String[] OBJECT_TYPES_NAMES = { "item", "block", "entity", "effect", "enchantment", "achievement", "attribute", "particle", "sound" };

	/** @param text - The input text.
	 * @param substring - The occurrences to look for.
	 * @return The number of times the substring appears in the text. */
	public static int countOccurrences(String text, String substring)
	{
		int count = 0;
		while (text.length() > 0)
		{
			if (text.startsWith(substring)) count++;
			text = text.substring(1);
		}
		return count;
	}

	/** @param type - The type of the object.
	 * @return - Its name.
	 * @see Utils#ITEM */
	public static String getObjectTypeName(int type)
	{
		return CommandGenerator.translate("GUI:object." + getObjectTypeNameId(type));
	}

	/** @param type - The type of the object.
	 * @return - Its name ID.
	 * @see Utils#ITEM */
	public static String getObjectTypeNameId(int type)
	{
		if (type == COMMAND) return "command";
		if (type == STRUCTURE) return "structure";
		if (type < 0 || type >= OBJECT_TYPES_NAMES.length) return "object";
		return OBJECT_TYPES_NAMES[type];
	}

	/** Shows a popup window, with an "OK" button to close it..
	 * 
	 * @param title - Its title.
	 * @param message - Its message. */
	public static void showMessage(String title, String message)
	{
		JDialog dialog = new JDialog(CommandGenerator.getWindow(), title, true);
		int width = CommandGenerator.getWindow().getWidth() / 3, height = CommandGenerator.getWindow().getHeight() / 3;

		JPanel panel = new JPanel(new GridBagLayout());
		CTextArea textArea = new CTextArea(message);
		CButton button = new CButton("GUI:state.ok");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dialog.dispose();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(textArea, gbc);
		gbc.gridy++;
		panel.add(Box.createRigidArea(new Dimension(100, 20)), gbc);
		gbc.gridy++;
		panel.add(button, gbc);

		dialog.getContentPane().add(panel);
		dialog.setSize(width, height);
		dialog.setLocationRelativeTo(CommandGenerator.getWindow());
		dialog.setVisible(true);
	}

	/** @param array - The array.
	 * @param separator - A String to put between each component of the array.
	 * @return A String representation of the array. */
	public static String toString(String[] array, String separator)
	{
		String string = "";
		for (int i = 0; i < array.length; i++)
		{
			if (i != 0) string += separator;
			string += array[i];
		}
		return string;
	}

}
