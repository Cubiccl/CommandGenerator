package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectLists;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TestforblockOptionsPanel extends OptionsPanel
{

	private CoordSelectionPanel panelCoord;
	private BlockSelectionPanel panelBlock;

	public TestforblockOptionsPanel()
	{
		super();

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:block.coords", true, false);
		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:testforblocks.block", ObjectLists.get(CGConstants.LIST_BLOCKS), true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelCoord, gbc);
		gbc.gridy++;
		add(panelBlock, gbc);
	}

	@Override
	public String generateCommand()
	{

		Coordinates coords = panelCoord.generateCoord();
		Item block = panelBlock.generateBlock();
		int damage = panelBlock.getDamage();
		TagCompound tag = panelBlock.getBlockTag();

		if (coords == null || block == null) return null;

		String command = "testforblock " + coords.commandStructure() + " " + block.getId() + " " + Integer.toString(damage);

		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) command += " " + tag.commandStructure().substring(tag.getId().length() + 1);

		return command;
	}

}
