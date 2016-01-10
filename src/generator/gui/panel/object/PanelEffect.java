package generator.gui.panel.object;

import generator.CommandGenerator;
import generator.gui.CImage;
import generator.gui.CLabel;
import generator.gui.checkbox.CCheckbox;
import generator.gui.combobox.CSearchBox;
import generator.gui.panel.CPanel;
import generator.gui.textfield.CEntry;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.GenerationException;
import generator.main.Text;
import generator.registry.Effect;
import generator.registry.instance.ActiveEffect;

import java.awt.AWTEvent;

/** Used to input an Effect. */
@SuppressWarnings("serial")
public class PanelEffect extends CPanel implements IClickEvent
{
	private static final int SELECT = 0;

	/** Checkbox to indicate if particles are hidden. */
	private CCheckbox checkboxHideParticles;
	/** The combobox to select an effect. */
	private CSearchBox comboboxEffect;
	/** A list of all effects. */
	private Effect[] effects;
	/** Entry to input the amplifier. */
	private CEntry entryAmplifier;
	/** Entry to input the duration. */
	private CEntry entryDuration;
	/** Label for effect. */
	private CLabel labelEffect;
	/** The texture of the effect. */
	private CImage texture;

	public PanelEffect()
	{
		super();
		this.effects = CommandGenerator.getRegistry().getEffects();

		this.labelEffect = new CLabel(new Text("GUI", "effect.effect"));
		this.texture = new CImage();

		this.checkboxHideParticles = new CCheckbox(new Text("GUI", "effect.particles"));

		this.entryAmplifier = new CEntry(new Text("GUI", "effect.amplifier"));
		this.entryDuration = new CEntry(new Text("GUI", "effect.duration"));

		this.comboboxEffect = new CSearchBox();
		this.comboboxEffect.addActionListener(new ClickEvent(this, SELECT));

		this.add(this.labelEffect, this.gbc);
		this.gbc.gridx++;
		this.add(this.comboboxEffect, this.gbc);
		this.gbc.gridx++;
		this.gbc.gridheight = 4;
		this.add(this.texture, this.gbc);

		this.gbc.gridx = 0;
		this.gbc.gridy++;
		this.gbc.gridwidth = 2;
		this.gbc.gridheight = 1;
		this.add(this.entryDuration, this.gbc);
		this.gbc.gridy++;
		this.add(this.entryAmplifier, this.gbc);
		this.gbc.gridy++;
		this.add(this.checkboxHideParticles, this.gbc);
		this.gbc.gridy++;

		this.updateLang();
	}

	/** @return The generated Active Effect.
	 * @throws GenerationException If an input value is incorrect. */
	public ActiveEffect generateEffect() throws GenerationException
	{
		int duration = 0, amplifier = 0;

		try
		{
			duration = Integer.parseInt(this.entryDuration.getText());
		} catch (NumberFormatException e)
		{
			throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", this.entryDuration.getText()));
		}

		try
		{
			amplifier = Integer.parseInt(this.entryAmplifier.getText());
		} catch (NumberFormatException e)
		{
			throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", this.entryAmplifier.getText()));
		}

		if (duration < 0) throw new GenerationException(new Text("GUI", "error.number.min", false).addReplacement("<value>", this.entryDuration.getText())
				.addReplacement("<min>", "0"));

		return new ActiveEffect(this.getSelectedEffect(), duration, amplifier, this.checkboxHideParticles.isSelected());
	}

	/** @return The currently selected Effect type. */
	public Effect getSelectedEffect()
	{
		return this.effects[this.comboboxEffect.getSelectedIndex()];
	}

	@Override
	public void onEvent(int eventID, AWTEvent event)
	{
		switch (eventID)
		{
			case SELECT:
				this.texture.setIcon(this.getSelectedEffect().getIcon());
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		String[] names = new String[this.effects.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = this.effects[i].getName();
		}

		int selection = this.comboboxEffect.getSelectedIndex();
		if (selection == -1) selection++;
		this.comboboxEffect.setValues(names);
		this.comboboxEffect.setSelectedIndex(selection);
	}

}
