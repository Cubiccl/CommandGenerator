package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class PatternsSelectionPanel extends HelperPanel
{

	private String[] patternIds = { "bs", "ts", "ls", "rs", "ms", "cs", "ss", "drs", "dls", "cr", "sc", "ld", "rd", "hh", "vh", "bl", "br", "tl", "tr", "bt",
			"tt", "bts", "tts", "mc", "mr", "bo", "cbo", "bri", "cre", "sku", "flo", "moj", "gra" };

	private CLabel labelColor, labelPattern;

	private JLabel labelImage;
	private CButton buttonAdd, buttonRemove;
	private LangComboBox comboboxColor, comboboxPattern;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;
	private List<Tag> tags;

	public PatternsSelectionPanel()
	{
		super(CGConstants.DATAID_NONE, "TAG:pattern", 600, 400);

		tags = new ArrayList<Tag>();

		buttonAdd = new CButton("GUI:pattern.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagCompound tag = new TagCompound() {
					public void askValue()
					{}
				};
				tag.addTag(new TagInt("Color").setValue(15 - comboboxColor.getSelectedIndex()));
				tag.addTag(new TagString("Pattern").setValue(patternIds[comboboxPattern.getSelectedIndex()]));
				tags.add(tag);
				displayPatterns();
			}
		});
		buttonRemove = new CButton("GUI:pattern.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemove(tags);
				displayPatterns();
			}
		});

		labelColor = new CLabel("GUI:pattern.color");
		labelPattern = new CLabel("GUI:pattern.type");
		labelImage = new JLabel();
		try
		{
			labelImage.setIcon(new ImageIcon("generator/textures/banners/bs.png"));
		} catch (Exception e)
		{
			DisplayHelper.missingTexture("textures/banners/bs.png");
		}

		comboboxColor = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:color_wool", 16);
		comboboxColor.setPreferredSize(new Dimension(200, 20));
		comboboxPattern = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:pattern", patternIds.length);
		comboboxPattern.setPreferredSize(new Dimension(300, 20));
		comboboxPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					labelImage.setIcon(new ImageIcon("generator/textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png"));
				} catch (Exception e)
				{
					DisplayHelper.missingTexture("textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png");
				}
			}
		});

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(400, 120));

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelColor, gbc);
		gbc.gridy++;
		add(labelPattern, gbc);

		gbc.gridx++;
		gbc.gridy--;
		add(comboboxColor, gbc);
		gbc.gridy++;
		add(comboboxPattern, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		add(labelImage, gbc);
		gbc.gridheight = 1;

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(buttonAdd, gbc);
		gbc.gridy++;
		add(buttonRemove, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		gbc.gridwidth = 2;
		add(scrollpane, gbc);
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
	}

	private void displayPatterns()
	{
		String text = "";

		for (int i = 0; i < tags.size(); i++)
		{
			if (i != 0) text += "<br />";
			text += "Color : " + Lang.get("RESOURCES:color_wool_" + ((TagInt) ((TagCompound) tags.get(i)).getTag(0)).getValue());
			for (int j = 0; j < patternIds.length; j++)
			{
				if (patternIds[j].equals(((TagString) ((TagCompound) tags.get(i)).getTag(1)).getValue())) text += ", Pattern : "
						+ Lang.get("RESOURCES:pattern_" + j);
			}

		}

		editorpane.setText(text);
	}

	public List<Tag> getSelectedPatterns()
	{
		return tags;
	}

	@Override
	public void updateLang()
	{}

}
