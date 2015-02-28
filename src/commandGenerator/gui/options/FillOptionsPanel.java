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
public class FillOptionsPanel extends OptionsPanel implements IBox
{
	private static final String[] modes = { "destroy", "hollow", "keep", "outline", "replace" };

	private HelpButton buttonHelp;
	private LangComboBox comboboxMode, comboboxModeFill;
	private CoordSelectionPanel panelCoordStart, panelCoordEnd;
	private BlockSelectionPanel panelBlock, panelBlockReplace;

	public FillOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxModeFill);
		addLine(panelCoordStart, panelCoordEnd);
		addLine(comboboxMode, buttonHelp);
		add(panelBlock);
		add(panelBlockReplace);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new HelpButton(Lang.get("HELP:fill.mode_0"), Lang.get("RESOURCES:fill.mode_0"));

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:fill.mode", 5);
		comboboxModeFill = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:fill.mode2", 2);
		comboboxMode.addListener(this);
		comboboxModeFill.addListener(this);

		panelCoordStart = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:fill.start", true, false);
		panelCoordEnd = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:fill.end", true, false);

		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:fill.block", Registry.getList(CGConstants.LIST_BLOCKS), true);
		panelBlockReplace = new BlockSelectionPanel(CGConstants.PANELID_ITEM, "GUI:fill.block.replace", Registry.getList(CGConstants.LIST_BLOCKS), false);
		panelBlockReplace.setEnabledContent(false);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Coordinates start = panelCoordStart.generateCoord(), end = panelCoordEnd.generateCoord();
		Item block = panelBlock.generateBlock(), blockReplace = panelBlockReplace.generateBlock();

		if (start == null || end == null) return null;

		String command = "fill " + start.commandStructure() + " " + end.commandStructure() + " " + block.getCommandId() + " " + panelBlock.getDamage() + " ";

		if (comboboxModeFill.getSelectedIndex() == 0)
		{
			command += modes[comboboxMode.getSelectedIndex()] + " ";
			TagCompound tag = panelBlock.getBlockTag();
			if (!tag.commandStructure().endsWith("{}")) command += tag.commandStructure().substring(tag.getId().length() + 1);
		}

		else command += "replace " + blockReplace.getCommandId() + " " + panelBlockReplace.getDamage();

		return command;
	}

	@Override
	public void updateCombobox()
	{
		buttonHelp.setData(Lang.get("HELP:fill.mode_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
		boolean replace = comboboxModeFill.getSelectedIndex() == 1;
		comboboxMode.setEnabledContent(!replace);
		buttonHelp.setEnabledContent(!replace);
		panelBlockReplace.setEnabledContent(replace);
	}

}
