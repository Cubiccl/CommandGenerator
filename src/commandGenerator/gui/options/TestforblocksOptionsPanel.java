package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TestforblocksOptionsPanel extends OptionsPanel implements IBox
{

	private static final String[] modes = { "masked", "all" };
	private HelpButton buttonHelp;
	private LangComboBox comboboxMode;
	private CLabel labelMode;
	private CoordSelectionPanel panelCoord1, panelCoord2, panelCoordDest;

	public TestforblocksOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord1);
		add(panelCoord2);
		add(panelCoordDest);
		addLine(labelMode, comboboxMode, buttonHelp);
	}

	@Override
	protected void createComponents()
	{

		labelMode = new CLabel("GUI:testforblocks.mode");

		buttonHelp = new HelpButton(Lang.get("HELP:testforblocks_0"), Lang.get("RESOURCES:testforblocks.mode_0"));

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:testforblocks.mode", 2);
		comboboxMode.addListener(this);

		panelCoord1 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:testforblocks.start", true, false);
		panelCoord2 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:testforblocks.end", true, false);
		panelCoordDest = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:testforblocks.destination", true, false);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{

		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		Coordinates coordDestination = panelCoordDest.generateCoord();

		if (coord1 == null || coord2 == null || coordDestination == null) return null;

		return "clone " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + coordDestination.commandStructure() + " "
				+ modes[comboboxMode.getSelectedIndex()];
	}

	@Override
	public void updateCombobox()
	{
		buttonHelp.setData(Lang.get("HELP:testforblocks_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
	}

}
