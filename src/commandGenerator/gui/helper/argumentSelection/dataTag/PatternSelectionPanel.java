package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class PatternSelectionPanel extends HelperPanel
{

	private String[] patternIds = { "bs", "ts", "ls", "rs", "ms", "cs", "ss", "drs", "dls", "cr", "sc", "ld", "rd", "hh", "vh", "bl", "br", "tl", "tr", "bt",
			"tt", "bts", "tts", "mc", "mr", "bo", "cbo", "bri", "cre", "sku", "flo", "moj", "gra" };

	private CLabel labelColor, labelPattern;

	private JLabel labelImage;
	private LangComboBox comboboxColor, comboboxPattern;

	public PatternSelectionPanel()
	{
		super(CGConstants.DATAID_NONE, "TAG:pattern", 600, 400);

		labelColor = new CLabel("GUI:pattern.color");
		labelPattern = new CLabel("GUI:pattern.type");
		labelImage = new JLabel();
		try
		{
			labelImage.setIcon(new ImageIcon(Resources.folder + "/textures/banners/bs.png"));
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
					labelImage.setIcon(new ImageIcon(Resources.folder + "/textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png"));
				} catch (Exception e)
				{
					DisplayHelper.missingTexture("textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png");
				}
			}
		});

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

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(labelImage, gbc);
		gbc.gridwidth = 1;
	}

	public TagCompound getPattern()
	{
		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("Color").setValue(comboboxColor.getSelectedIndex()));
		tag.addTag(new TagString("Pattern").setValue(patternIds[comboboxPattern.getSelectedIndex()]));
		return tag;
	}

	@Override
	public void updateLang()
	{}

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Color")) comboboxColor.setSelectedIndex(((TagInt) tag).getValue());
			if (tag.getId().equals("Pattern"))
			{
				for (int j = 0; j < patternIds.length; j++)
					if (patternIds[j].equals(((TagString) tag).getValue())) comboboxPattern.setSelectedIndex(j);
			}
		}
	}

}
