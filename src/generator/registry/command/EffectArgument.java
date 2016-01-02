package generator.registry.command;

import generator.gui.panel.object.PanelEffect;
import generator.main.GenerationException;

import java.awt.Component;

/** An effect. */
public class EffectArgument extends Argument
{
	private PanelEffect panelEffect;

	/** Creates a new Effect Argument. */
	public EffectArgument()
	{
		super(true, 4);
	}

	@Override
	public void createGui()
	{
		this.panelEffect = new PanelEffect();
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.panelEffect.generateEffect().toCommand();
	}

	@Override
	public Component getComponent()
	{
		return this.panelEffect;
	}

	@Override
	public void updateLang()
	{
		if (this.panelEffect != null) this.panelEffect.updateLang();
	}

}
