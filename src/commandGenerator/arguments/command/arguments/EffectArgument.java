package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;

public class EffectArgument extends Argument
{
	private EffectSelectionPanel panel;

	public EffectArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.EFFECT, isCompulsery, 1);
		this.setMaximumLength(4);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EffectSelectionPanel(this.getId(), "GENERAL:effect");
	}

	@Override
	public String generateCommand()
	{
		Effect effect = this.panel.generateEffect();
		if (effect == null) return null;
		else return effect.commandStructure();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
