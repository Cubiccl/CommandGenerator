package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.argumentSelection.ColorSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CTextField;
import commandGenerator.gui.helper.components.button.CButton;

@SuppressWarnings("serial")
public class DisplaySelectionPanel extends JPanel
{

	private CButton buttonAdd, buttonRemove;
	private CCheckBox checkboxName, checkboxColor;
	private JEditorPane editorpane;
	private GridBagConstraints gbc;
	private List<String> lores;
	private ColorSelectionPanel panel;
	private JScrollPane scrollpane;
	private CTextField textfieldName, textfieldLore;

	public DisplaySelectionPanel()
	{
		super(new GridBagLayout());

		lores = new ArrayList<String>();

		textfieldName = new CTextField(20);
		textfieldName.setEnabled(false);
		textfieldLore = new CTextField(20);

		buttonAdd = new CButton("GUI:text.add");
		buttonAdd.setDrawType(GuiHandler.LEFT);
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				lores.add(textfieldLore.getText());
				textfieldLore.setText("");
				displayLores();
			}
		});
		buttonRemove = new CButton("GUI:text.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemoveText(lores);
				displayLores();
			}
		});

		checkboxName = new CCheckBox("GUI:display.custom_name");
		checkboxName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldName.setEnabled(checkboxName.isSelected());
			}
		});
		checkboxName.setSelected(false);
		checkboxColor = new CCheckBox("GUI:display.color");
		checkboxColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				panel.setEnabledContent(checkboxColor.isSelected());
			}
		});
		checkboxColor.setSelected(false);

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(200, 120));
		editorpane.setMinimumSize(new Dimension(200, 120));

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		panel = new ColorSelectionPanel("GUI:display.leather");
		panel.setEnabledContent(false);

		gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(textfieldName, gbc);
		gbc.gridx++;
		add(checkboxName, gbc);

		gbc.gridx--;
		gbc.gridy++;
		add(textfieldLore, gbc);
		gbc.gridx++;
		add(buttonAdd, gbc);

		gbc.gridx--;
		gbc.gridy++;
		add(checkboxColor, gbc);
		gbc.gridx++;
		add(buttonRemove, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 3;
		add(panel, gbc);
		gbc.gridwidth = 1;

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 3;
		add(scrollpane, gbc);

	}

	private void displayLores()
	{
		String text = "";
		for (int i = 0; i < lores.size(); i++)
		{
			if (i != 0) text += "<br />";
			text += lores.get(i);
		}
		editorpane.setText(text);
	}

	public String getColor()
	{
		if (checkboxColor.isSelected()) return Integer.toString(panel.getColor());
		return null;
	}

	public String getCustomName()
	{
		if (checkboxName.isSelected()) return textfieldName.getText();
		return null;
	}

	public TagList getLores()
	{
		List<Tag> tags = new ArrayList<Tag>();
		TagList tag = new TagList("Lore") {
			@Override
			public void askValue()
			{}
		};
		for (int i = 0; i < lores.size(); i++)
		{
			tags.add(new TagString().setValue(lores.get(i)));
		}
		tag.setValue(tags);
		return tag;
	}

	public void setup(String name, List<Tag> loreList, int color)
	{
		if (name != null)
		{
			checkboxName.setSelected(true);
			textfieldName.setEnabled(true);
			textfieldName.setText(name);
		}
		lores = new ArrayList<String>();
		for (int i = 0; i < loreList.size(); i++)
			lores.add(((TagString) loreList.get(i)).getValue());
		displayLores();
		// panel.setup(color);
	}
}
