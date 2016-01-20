package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.panel.object.PanelSound;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;
import generator.registry.Sound;

import java.awt.Component;

/** A Sound. */
public class SoundArgument extends Argument
{
	/** The GUI. */
	private PanelSound panel;

	public SoundArgument()
	{
		super(true, 1);
	}

	@Override
	public void createGui()
	{
		this.panel = new PanelSound();
	}

	@Override
	protected String generateValue() throws GenerationException
	{
		return this.panel.getSelectedSound().getId();
	}

	@Override
	public Component getComponent()
	{
		return this.panel;
	}

	@Override
	public void updateLang()
	{
		if (this.panel != null) this.panel.updateLang();
	}

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		for (Sound sound : CommandGenerator.getRegistry().getSounds())
		{
			if (sound.getId().equals(value)) return;
		}
		throw new GenerationException(new Text("GUI", "error.id", false).addReplacement("<value>", value).addReplacement("<object>",
				Utils.getObjectTypeName(Utils.SOUND)));
	}

}
