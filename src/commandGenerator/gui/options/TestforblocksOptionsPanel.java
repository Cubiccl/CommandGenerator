package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TestforblocksOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "masked", "all" };
	private JButton buttonHelp;
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

		buttonHelp = new JButton("?");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:testforblocks.mode", 2);

		panelCoord1 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:testforblocks.start", true, false);
		panelCoord2 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:testforblocks.end", true, false);
		panelCoordDest = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:testforblocks.destination", true, false);
	}

	@Override
	protected void createListeners()
	{
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Lang.get("HELP:testforblocks_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
			}
		});
	}

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

}
