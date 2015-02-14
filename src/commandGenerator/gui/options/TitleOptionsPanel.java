package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.gui.helper.commandSpecific.TitleDetailsPanel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TitleOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "title", "subtitle", "times" };
	private HelpButton buttonHelp;
	private LangComboBox comboboxMode;
	private TitleDetailsPanel panelDetails;
	private ListSelectionPanel panelJson;
	private TargetSelectionPanel panelPlayer;

	public TitleOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxMode, buttonHelp);
		add(panelPlayer);
		add(panelDetails);
		add(panelJson);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new HelpButton(Lang.get("HELP:title_0"), Lang.get("RESOURCES:title.mode_0"));

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:title.mode", 3);

		panelPlayer = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelDetails = new TitleDetailsPanel("GENERAL:options");
		panelDetails.setVisible(false);
		panelJson = new ListSelectionPanel("GUI:title.json", CGConstants.OBJECT_JSON);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelDetails.setVisible(comboboxMode.getSelectedIndex() == 2);
				panelJson.setVisible(comboboxMode.getSelectedIndex() != 2);
				panelPlayer.updateLang();
				buttonHelp.setData(Lang.get("HELP:title_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
			}
		});
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
