package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.gui.helper.commandSpecific.TitleDetailsPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TitleOptionsPanel extends OptionsPanel
{

	private LangComboBox comboboxMode;
	private EntitySelectionPanel panelPlayer;
	private TitleDetailsPanel panelDetails;
	private ListSelectionPanel panelJson;
	private static final String[] modes = { "title", "subtitle", "times" };

	public TitleOptionsPanel()
	{
		super();

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:title.mode", 3);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelDetails.setVisible(comboboxMode.getSelectedIndex() == 2);
				panelJson.setVisible(comboboxMode.getSelectedIndex() != 2);
				panelPlayer.updateLang();
			}
		});

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelDetails = new TitleDetailsPanel("GENERAL:options");
		panelDetails.setVisible(false);
		panelJson = new ListSelectionPanel("GUI:title.json", CGConstants.OBJECT_JSON);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridy++;
		add(panelPlayer, gbc);
		gbc.gridy++;
		add(panelDetails, gbc);
		add(panelJson, gbc);
	}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();

		if (player == null) return null;

		if (comboboxMode.getSelectedIndex() == 2)
		{
			String[] options = panelDetails.generateOptions();
			if (options == null) return null;
			return "title " + player.commandStructure() + " " + modes[comboboxMode.getSelectedIndex()] + " " + options[0] + " " + options[1] + " " + options[2];
		} else
		{

			TagList list = new TagList() {
				public void askValue()
				{}
			};
			list.setValue(panelJson.getList());
			return "title " + player.commandStructure() + " " + modes[comboboxMode.getSelectedIndex()] + " " + list.commandStructure();
		}
	}

}
