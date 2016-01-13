package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.panel.object.PanelEnchantment;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;
import generator.registry.Enchantment;

import java.awt.Component;

/** An Enchantment. */
public class EnchantmentArgument extends Argument
{
	/** The Enchantment panel. */
	private PanelEnchantment panel;

	/** Creates a new Enchantment Argument. */
	public EnchantmentArgument()
	{
		super(true, 1, 2);
	}

	@Override
	public void createGui()
	{
		this.panel = new PanelEnchantment(true);
	}

	@Override
	protected String generateValue() throws GenerationException
	{
		return this.panel.generateEnchantment().toCommand();
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
		String[] elements = value.split(" ");

		Enchantment[] enchantments = CommandGenerator.getRegistry().getEnchantments();
		boolean foundId = false;
		for (Enchantment enchantment : enchantments)
			if (enchantment.getId().equals(elements[0])) foundId = true;
		if (!foundId) throw new GenerationException(new Text("GUI", "error.id", false).addReplacement("<value>", elements[0]).addReplacement("<object>",
				Utils.getObjectTypeName(Utils.ENCHANTMENT)));

		if (elements.length > 1) try
		{
			Integer.parseInt(elements[1]);
		} catch (Exception e)
		{
			throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", elements[1]));
		}
	}
}
