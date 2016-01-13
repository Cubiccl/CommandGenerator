package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.panel.object.PanelEffect;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;
import generator.registry.Effect;

import java.awt.Component;

/** An effect. */
public class EffectArgument extends Argument
{
	private PanelEffect panelEffect;

	/** Creates a new Effect Argument. */
	public EffectArgument()
	{
		super(true, 1, 4);
	}

	@Override
	public void createGui()
	{
		this.panelEffect = new PanelEffect();
	}

	@Override
	protected String generateValue() throws GenerationException
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

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		String[] elements = value.split(" ");

		Effect[] effects = CommandGenerator.getRegistry().getEffects();
		boolean foundId = false;
		for (Effect effect : effects)
			if (elements[0].equals(effect.getId())) foundId = true;
		if (!foundId) throw new GenerationException(new Text("GUI", "error.id", false).addReplacement("<value>", elements[0]).addReplacement("<object>",
				Utils.getObjectTypeName(Utils.EFFECT)));

		if (elements.length > 1) try
		{
			Integer.parseInt(elements[1]);
		} catch (Exception e)
		{
			throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", elements[1]));
		}

		if (elements.length > 2) try
		{
			Integer.parseInt(elements[2]);
		} catch (Exception e)
		{
			throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", elements[2]));
		}

		if (elements.length > 3 && !elements[3].equals("true") && !elements[3].equals("false")) throw new GenerationException(new Text("GUI", "error.boolean",
				false).addReplacement("<true>", "true").addReplacement("<false>", "false").addReplacement("<value>", elements[3]));
	}
}
