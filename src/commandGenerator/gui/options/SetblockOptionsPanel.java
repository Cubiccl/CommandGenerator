package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SetblockOptionsPanel extends OptionsPanel implements IBox
{

	private static final String[] modes = { "keep", "destroy", "replace" };

	private HelpButton buttonHelp;
	private LangComboBox comboboxMode;
	private BlockSelectionPanel panelBlock;
	private CoordSelectionPanel panelCoord;

	public SetblockOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxMode, buttonHelp);
		add(panelCoord);
		add(panelBlock);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new HelpButton(Lang.get("HELP:placeblock.mode_0"), Lang.get("RESOURCES:placeblock.mode_0"));

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:placeblock.mode", 3);
		comboboxMode.addListener(this);

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:setblock.coords", true, false);
		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:setblock.block", Registry.getList(CGConstants.LIST_BLOCKS), true);
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

		String command = "setblock " + coords.commandStructure() + " " + block.getCommandId() + " " + Integer.toString(damage) + " "
				+ modes[comboboxMode.getSelectedIndex()];

		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) command += " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);

		return command;
	}

	@Override
	public void updateCombobox()
	{
		buttonHelp.setData(Lang.get("HELP:placeblock.mode_" + comboboxMode.getSelectedIndex()),
				Lang.get("RESOURCES:placeblock.mode_" + comboboxMode.getSelectedIndex()));
	}

}
