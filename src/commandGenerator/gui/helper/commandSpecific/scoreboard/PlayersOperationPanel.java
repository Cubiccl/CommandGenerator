package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class PlayersOperationPanel extends ScoreboardPanel
{
	public static final String[] operationList = { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" };

	private HelpButton buttonHelp;
	private JComboBox<String> comboboxOperation;
	private CEntry entryObj1, entryObj2;
	private CLabel labelOperation;
	private TargetSelectionPanel panelEntity1, panelEntity2;

	public PlayersOperationPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryObj1);
		add(entryObj2);
		addLine(labelOperation, comboboxOperation, buttonHelp);
		add(panelEntity1);
		add(panelEntity2);
	}

	@Override
	protected void createComponents()
	{
		labelOperation = new CLabel("GUI:scoreboard.operation");

		entryObj1 = new CEntry(Constants.DATAID_NAME, "GUI:scoreboard.operation.player1", "");
		entryObj2 = new CEntry(Constants.DATAID_NAME2, "GUI:scoreboard.operation.player2", "");

		buttonHelp = new HelpButton(Lang.get("HELP:scoreboard.operation_0"), "+=");

		comboboxOperation = new JComboBox<String>(operationList);

		panelEntity1 = new TargetSelectionPanel(Constants.PANELID_TARGET, "GUI:scoreboard.player1", Constants.ENTITIES_ALL);
		panelEntity2 = new TargetSelectionPanel(Constants.PANELID_TARGET2, "GUI:scoreboard.player2", Constants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{
		comboboxOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				buttonHelp.setData(Lang.get("HELP:scoreboard.operation_" + comboboxOperation.getSelectedItem()), (String) comboboxOperation.getSelectedItem());
			}
		});
	}

	@Override
	public String generateText()
	{
		Target entity1 = panelEntity1.generateEntity();
		Target entity2 = panelEntity2.generateEntity();

		if (entity1 == null || entity2 == null) return null;
		if (entryObj1.getText().equals("") || entryObj1.getText().contains(" ") || entryObj2.getText().equals("") || entryObj2.getText().contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		return entity1.commandStructure() + " " + entryObj1.getText() + " " + (String) comboboxOperation.getSelectedItem() + " " + entity2.commandStructure()
				+ " " + entryObj2.getText();
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		comboboxOperation.setSelectedIndex((int) data.get(Constants.DATAID_VALUE));
	}

}
