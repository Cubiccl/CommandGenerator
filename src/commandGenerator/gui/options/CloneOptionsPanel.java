package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CloneOptionsPanel extends OptionsPanel implements IBox
{

	private static final String[] modes1 = { "replace", "masked", "filtered" }, modes2 = { "normal", "force", "move" };

	private HelpButton buttonHelp1, buttonHelp2;
	private LangComboBox comboboxMode1, comboboxMode2;
	private CLabel labelMode1, labelMode2;
	private CoordSelectionPanel panelCoord1, panelCoord2, panelDestination;

	public CloneOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord1);
		add(panelCoord2);
		add(panelDestination);
		addLine(labelMode1, comboboxMode1, buttonHelp1);
		addLine(labelMode2, comboboxMode2, buttonHelp2);
	}

	@Override
	protected void createComponents()
	{
		labelMode1 = new CLabel("GUI:clone.mode1");
		labelMode2 = new CLabel("GUI:clone.mode2");

		buttonHelp1 = new HelpButton(Lang.get("HELP:setblock.mode_0"), Lang.get("RESOURCES:setblock.mode_0"));
		buttonHelp2 = new HelpButton(Lang.get("HELP:clone.mode_0"), Lang.get("RESOURCES:clone.mode_0"));

		panelCoord1 = new CoordSelectionPanel(Constants.PANELID_COORDS_START, "GUI:clone.start", true, false);
		panelCoord2 = new CoordSelectionPanel(Constants.PANELID_COORDS_END, "GUI:clone.end", true, false);
		panelDestination = new CoordSelectionPanel(Constants.PANELID_COORDS, "GUI:clone.destination", true, false);

		comboboxMode1 = new LangComboBox(Constants.DATAID_MODE, "RESOURCES:setblock.mode", 3);
		comboboxMode2 = new LangComboBox(Constants.DATAID_MODE2, "RESOURCES:clone.mode", 3);
		comboboxMode1.addListener(this);
		comboboxMode2.addListener(this);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{

		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		Coordinates coordDestination = panelDestination.generateCoord();

		if (coord1 == null || coord2 == null || coordDestination == null) return null;

		return "clone " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + coordDestination.commandStructure() + " "
				+ modes1[comboboxMode1.getSelectedIndex()] + " " + modes2[comboboxMode2.getSelectedIndex()];
	}

	@Override
	public void updateCombobox()
	{
		buttonHelp1.setData(Lang.get("HELP:setblock.mode_" + comboboxMode1.getSelectedIndex()), (String) comboboxMode1.getSelectedItem());
		buttonHelp2.setData(Lang.get("HELP:clone.mode_" + comboboxMode2.getSelectedIndex()), (String) comboboxMode2.getSelectedItem());
	}

}
