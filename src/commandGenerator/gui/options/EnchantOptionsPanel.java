package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class EnchantOptionsPanel extends OptionsPanel
{

	private EnchantSelectionPanel panelEnchant;
	private TargetSelectionPanel panelPlayer;

	public EnchantOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelPlayer);
		add(panelEnchant);
	}

	@Override
	protected void createComponents()
	{
		panelPlayer = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.player", Constants.ENTITIES_PLAYERS);
		panelEnchant = new EnchantSelectionPanel(Constants.PANELID_ENCHANT, "GENERAL:enchant", true);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();
		Enchantment enchant = panelEnchant.generateEnchantment();

		if (player == null || enchant == null) return null;

		return "enchant " + player.commandStructure() + " " + enchant.getType().getId() + " " + Integer.toString(enchant.getLevel());
	}

}
