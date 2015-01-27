package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class ObjectiveSelectionPanel extends HelperPanel
{
	private static final String[] criteriaList = { "dummy", "trigger", "deathCount", "playerKillCount", "totalKillCount", "health", "achievement",
			"stat.craftItem", "stat.useItem", "stat.breakItem", "stat.mineBlock", "stat.killEntity", "stat.entityKilledBy", "stat", "teamkill", "killedByTeam" };
	private static final String[] statList = { "animalsBred", "boatOneCm", "climbOneCm", "crouchOneCm", "damageDealt", "damageTaken", "deaths", "diveOneCm",
			"drop", "fallOneCm", "fishCaught", "flyOneCm", "horseOneCm", "jump", "junkFished", "leaveGame", "minecartOneCm", "mobKills", "pigOneCm",
			"playerKills", "playOneMinute", "sprintOneCm", "swimOneCm", "timeSinceDeath", "treasureFished", "walkOneCm" };

	private CComboBox comboboxPrecise;
	private LangComboBox comboboxMain, comboboxPrecise2;

	public ObjectiveSelectionPanel(String title)
	{
		super(CGConstants.PANELID_OPTIONS, title, 600, 150);
		comboboxMain = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:criteria", criteriaList.length);
		comboboxMain.setPreferredSize(new Dimension(200, 20));
		comboboxMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setupChoices();
			}
		});
		comboboxPrecise = new CComboBox(CGConstants.DATAID_NONE, "", new ObjectBase[0], null);
		comboboxPrecise2 = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:color", 16);
		comboboxPrecise2.setVisible(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMain, gbc);
		gbc.gridy++;
		add(comboboxPrecise, gbc);
		add(comboboxPrecise2, gbc);
	}

	private void setupChoices()
	{
		int index = comboboxMain.getSelectedIndex();

		comboboxPrecise.setVisible(index < 13);
		comboboxPrecise2.setVisible(index >= 13);

		if (index < 6) comboboxPrecise.setData(new ObjectBase[0]);

		else if (index == 6) comboboxPrecise.setData(Registerer.getObjectList(CGConstants.OBJECT_ACHIEVEMENT));

		else if (index == 13) comboboxPrecise2.setText("RESOURCES:stat", statList.length);

		else if (index == 11 || index == 12) comboboxPrecise.setData(Registerer.getList(CGConstants.LIST_MOBS));

		else if (index >= 14) comboboxPrecise2.setText("RESOURCES:color", 16);

		else
		{
			comboboxPrecise.setEnabled(true);
			ObjectBase[] itemList = new ObjectBase[0];
			if (index == 7) itemList = Registerer.getList(CGConstants.LIST_CRAFT);
			else if (index == 8) itemList = Registerer.getList(CGConstants.LIST_USE);
			else if (index == 9) itemList = Registerer.getList(CGConstants.LIST_ITEMS);
			else itemList = Registerer.getList(CGConstants.LIST_MINE);

			comboboxPrecise.setData(itemList);
		}
	}

	public String generateObjective()
	{
		int index = comboboxMain.getSelectedIndex();
		if (index < 6) return criteriaList[index];
		if (index >= 7 && index <= 10) return criteriaList[index] + ".minecraft." + ((String) comboboxPrecise.getValue().getId());
		if (index == 6 || index == 11 || index == 12) return criteriaList[index] + "." + ((String) comboboxPrecise.getValue().getId());
		if (index == 13) return criteriaList[index] + "." + statList[comboboxPrecise2.getSelectedIndex()];
		return criteriaList[index] + "." + Resources.colors[comboboxPrecise2.getSelectedIndex()];
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		String criteria = (String) data.get(CGConstants.DATAID_VALUE);
		for (int i = 0; i < criteriaList.length; i++)
		{
			if (criteria.startsWith(criteriaList[i]))
			{
				comboboxMain.setSelectedIndex(i);
				criteria = criteria.substring(criteriaList[i].length());
				break;
			}
		}
		setupChoices();
		int index = comboboxMain.getSelectedIndex();

		if (index >= 6 && index <= 12) comboboxPrecise.setSelected(Registerer.getObjectFromId(criteria.substring(1)));
		if (index == 13)
		{
			for (int i = 0; i < statList.length; i++)
				if (criteria.substring(1).equals(statList[i])) comboboxPrecise2.setSelectedIndex(i);
		}
		if (index >= 14) for (int i = 0; i < Resources.colors.length; i++)
			if (criteria.substring(1).equals(Resources.colors[i])) comboboxPrecise2.setSelectedIndex(i);

	}

}
