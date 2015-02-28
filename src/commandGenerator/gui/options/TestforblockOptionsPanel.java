package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TestforblockOptionsPanel extends OptionsPanel
{

	private BlockSelectionPanel panelBlock;
	private CoordSelectionPanel panelCoord;

	public TestforblockOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord);
		add(panelBlock);
	}

	@Override
	protected void createComponents()
	{
		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:block.coords", true, false);
		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:testforblocks.block", Registry.getList(CGConstants.LIST_BLOCKS), true);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{

		Coordinates coords = panelCoord.generateCoord();
		Item block = panelBlock.generateBlock();
		int damage = panelBlock.getDamage();
		TagCompound tag = panelBlock.getBlockTag();

		if (coords == null || block == null) return null;

		String command = "testforblock " + coords.commandStructure() + " " + block.getCommandId() + " " + Integer.toString(damage);

		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) command += " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);

		return command;
	}

}
