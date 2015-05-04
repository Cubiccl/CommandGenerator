package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class PatternSelectionPanel extends CPanel
{

	private static final String[] patternIds = { "bs", "ts", "ls", "rs", "ms", "cs", "ss", "drs", "dls", "cr", "sc", "ld", "rd", "hh", "vh", "bl", "br", "tl",
			"tr", "bt", "tt", "bts", "tts", "mc", "mr", "bo", "cbo", "bri", "cre", "sku", "flo", "moj", "gra" };

	private ChoiceComboBox comboboxColor, comboboxPattern;

	private CLabel labelColor, labelPattern;
	private JLabel labelImage;

	public PatternSelectionPanel()
	{
		super("GENERAL:pattern");
		
		this.initGui();
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

		comboboxColor = new ChoiceComboBox("color", Resources.colorsDye, false);
		comboboxPattern = new ChoiceComboBox("pattern", patternIds, false);
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
					labelImage.setIcon(new ImageIcon(Resources.folder + "textures/banners/" + comboboxPattern.getSelectedValue() + ".png"));
				} catch (Exception e)
				{
					DisplayHelper.missingTexture("textures/banners/" + comboboxPattern.getSelectedValue() + ".png");
				}
			}
		});
	}

	public TagCompound getPattern()
	{
		TagCompound tag = new TagCompound() {
			@Override
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("Color").setValue(comboboxColor.getSelectedIndex()));
		tag.addTag(new TagString("Pattern").setValue(this.comboboxPattern.getSelectedValue()));
		return tag;
	}

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Color")) comboboxColor.setSelected(Resources.colorsDye[((TagInt) tag).getValue()]);
			if (tag.getId().equals("Pattern")) comboboxPattern.setSelected(((TagString) tag).getValue());
		}
	}

}
