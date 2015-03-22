package commandGenerator.arguments.command.arguments;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;

public class EnchantmentArgument extends Argument
{

	private EnchantSelectionPanel panel;

	public EnchantmentArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.ENCHANTMENT, isCompulsery, 2);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EnchantSelectionPanel(this.getId(), "GENERAL:enchant", true);
	}

	@Override
	public String generateCommand()
	{
		Enchantment enchant = this.panel.generateEnchantment();
		if (enchant == null) return null;
		return enchant.getType().getId() + " " + enchant.getLevel();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}
