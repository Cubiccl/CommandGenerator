package generator.gui.panel.object;

import java.awt.AWTEvent;

import generator.CommandGenerator;
import generator.gui.CImage;
import generator.gui.combobox.CSearchBox;
import generator.gui.panel.CPanelHorizontal;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.registry.Achievement;

import javax.swing.BorderFactory;

/** Panel to select an Achievement. */
@SuppressWarnings("serial")
public class PanelAchievement extends CPanelHorizontal implements IClickEvent
{
	private static final int SELECT = 0;

	private Achievement[] achievements;
	private CSearchBox combobox;
	private CImage labelTexture;

	public PanelAchievement()
	{
		super();
		this.achievements = CommandGenerator.getRegistry().getAchievements();

		this.combobox = new CSearchBox();
		this.combobox.addActionListener(new ClickEvent(this, SELECT));
		this.labelTexture = new CImage();

		this.add(this.combobox);
		this.add(this.labelTexture);

		this.updateLang();
	}

	public Achievement generateAchievement()
	{
		return this.achievements[this.combobox.getSelectedIndex()];
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case SELECT:
				this.labelTexture.setIcon(this.generateAchievement().getIcon());
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		this.setBorder(BorderFactory.createTitledBorder(CommandGenerator.translate("GUI:object.achievement")));

		String[] names = new String[this.achievements.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = this.achievements[i].getName();
		}

		int selection = this.combobox.getSelectedIndex();
		if (selection == -1) selection = 0;
		this.combobox.setValues(names);
		this.combobox.setSelectedIndex(selection);
	}

}
