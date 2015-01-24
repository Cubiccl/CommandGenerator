package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EnchantOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelPlayer;
	private EnchantSelectionPanel panelEnchant;

	public EnchantOptionsPanel()
	{
		super();

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelEnchant = new EnchantSelectionPanel(CGConstants.PANELID_ENCHANT, "GENERAL:enchant", true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPlayer, gbc);
		gbc.gridy++;
		add(panelEnchant, gbc);
	}

	@Override
	public String generateCommand()
	{
		EntitySelector player = panelPlayer.generateEntity();
		Enchantment enchant = panelEnchant.generateEnchantment();

		if (player == null || enchant == null) return null;

		return "enchant " + player.commandStructure() + " " + enchant.getId() + " " + Integer.toString(enchant.getLevel());
	}

}
