package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;
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

	private LangComboBox comboboxColor, comboboxPattern;

	private CLabel labelColor, labelPattern;

	private JLabel labelImage;
	private String[] patternIds = { "bs", "ts", "ls", "rs", "ms", "cs", "ss", "drs", "dls", "cr", "sc", "ld", "rd", "hh", "vh", "bl", "br", "tl", "tr", "bt",
			"tt", "bts", "tts", "mc", "mr", "bo", "cbo", "bri", "cre", "sku", "flo", "moj", "gra" };

	public PatternSelectionPanel()
	{
		super(CGConstants.DATAID_NONE, "TAG:pattern");
	}

	@Override
	protected void addComponents()
	{
		addLine(labelColor, comboboxColor);
		addLine(labelPattern, comboboxPattern);
		add(labelImage);
	}

	@Override
	protected void createComponents()
	{
		labelColor = new CLabel("GUI:pattern.color");
		labelPattern = new CLabel("GUI:pattern.type");
		labelImage = new JLabel();
		try
		{
			labelImage.setIcon(new ImageIcon(Resources.folder + "textures/banners/bs.png"));
		} catch (Exception e)
		{
			DisplayHelper.missingTexture("textures/banners/bs.png");
		}

		comboboxColor = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:color_wool", 16);
		comboboxColor.setPreferredSize(new Dimension(200, 20));
		comboboxPattern = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:pattern", patternIds.length);
		comboboxPattern.setPreferredSize(new Dimension(300, 20));
	}

	@Override
	protected void createListeners()
	{
		comboboxPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					labelImage.setIcon(new ImageIcon(Resources.folder + "textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png"));
				} catch (Exception e)
				{
					DisplayHelper.missingTexture("textures/banners/" + patternIds[comboboxPattern.getSelectedIndex()] + ".png");
				}
			}
		});
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

	@Override
	public void updateLang()
	{}

}
