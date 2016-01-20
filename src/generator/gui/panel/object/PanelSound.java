package generator.gui.panel.object;

import generator.CommandGenerator;
import generator.gui.button.CMessageButton;
import generator.gui.combobox.CSearchBox;
import generator.gui.panel.CPanelHorizontal;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.Text;
import generator.registry.Sound;

import java.awt.AWTEvent;

/** Panel to select a Sound. */
@SuppressWarnings("serial")
public class PanelSound extends CPanelHorizontal implements IClickEvent
{
	private static final int SELECT = 0;

	/** Displays info about the selected sound. */
	private CMessageButton buttonInfo;
	/** Used to select a sound. */
	private CSearchBox comboboxSound;
	private Sound[] sounds;

	public PanelSound()
	{
		super();

		this.sounds = CommandGenerator.getRegistry().getSounds();

		this.comboboxSound = new CSearchBox();
		this.comboboxSound.addActionListener(new ClickEvent(this, SELECT));
		this.buttonInfo = new CMessageButton();

		this.add(this.comboboxSound);
		this.add(this.buttonInfo);

		this.updateLang();
	}

	/** @return The currently selected Sound. */
	public Sound getSelectedSound()
	{
		return this.sounds[this.comboboxSound.getSelectedIndex()];
	}

	@Override
	public void onEvent(int eventID, AWTEvent event)
	{
		this.buttonInfo.setMessage(new Text(this.getSelectedSound().getDescription()));
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[] values = new String[this.sounds.length];
		for (int i = 0; i < values.length; i++)
			values[i] = this.sounds[i].getName();
		this.comboboxSound.setValues(values);
		this.buttonInfo.setTitle(new Text(this.getSelectedSound().getName()));
		this.buttonInfo.setMessage(new Text(this.getSelectedSound().getDescription()));
	}

}
