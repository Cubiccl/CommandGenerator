package commandGenerator.gui.helper.argumentSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.combobox.ObjectComboBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class ObjectiveSelectionPanel extends CPanel
{
	private static final String[] criteriaList = { "dummy", "trigger", "deathCount", "playerKillCount", "totalKillCount", "health", "achievement",
			"stat.craftItem", "stat.useItem", "stat.breakItem", "stat.mineBlock", "stat.killEntity", "stat.entityKilledBy", "stat", "teamKill", "killedByTeam" };
	private static final String[] statList = { "animalsBred", "boatOneCm", "climbOneCm", "crouchOneCm", "damageDealt", "damageTaken", "deaths", "diveOneCm",
			"drop", "fallOneCm", "fishCaught", "flyOneCm", "horseOneCm", "jump", "junkFished", "leaveGame", "minecartOneCm", "mobKills", "pigOneCm",
			"playerKills", "playOneMinute", "sprintOneCm", "swimOneCm", "timeSinceDeath", "treasureFished", "walkOneCm" };

	private ChoiceComboBox comboboxMain, comboboxPrecise2;
	private ObjectComboBox comboboxPrecise;

	public ObjectiveSelectionPanel(String title)
	{
		super(title);
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMain);
		add(comboboxPrecise);
		add(comboboxPrecise2);
	}

	@Override
	protected void createComponents()
	{
		comboboxMain = new ChoiceComboBox("criteria", criteriaList, false);
		comboboxPrecise = new ObjectComboBox("", new ObjectBase[0], null);
		comboboxPrecise2 = new ChoiceComboBox("color", Resources.colors, false);
		comboboxPrecise2.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
		comboboxMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setupChoices();
			}
		});
	}

	public String generateObjective()
	{
		int index = comboboxMain.getSelectedIndex();
		if (index < 6) return criteriaList[index];
		if (index >= 7 && index <= 10) return criteriaList[index] + ".minecraft." + (comboboxPrecise.getValue().getId());
		if (index == 6 || index == 11 || index == 12) return criteriaList[index] + "." + (comboboxPrecise.getValue().getId());
		if (index == 13) return criteriaList[index] + "." + statList[comboboxPrecise2.getSelectedIndex()];
		return criteriaList[index] + "." + Resources.colors[comboboxPrecise2.getSelectedIndex()];
	}

	public void setSelected(String criteria)
	{
		for (int i = 0; i < criteriaList.length; i++)
		{
			if (criteria.startsWith(criteriaList[i]))
			{
				comboboxMain.setSelected(criteriaList[i]);
				criteria = criteria.substring(criteriaList[i].length());
				break;
			}
		}
		setupChoices();
		int index = comboboxMain.getSelectedIndex();

		if (index >= 6 && index <= 12) comboboxPrecise.setSelected(Generator.registry.getObjectFromId(criteria.substring(1)));
		if (index == 13)
		{
			for (int i = 0; i < statList.length; i++)
				if (criteria.substring(1).equals(statList[i])) comboboxPrecise2.setSelected(statList[i]);
		}
		if (index >= 14) for (int i = 0; i < Resources.colors.length; i++)
			if (criteria.substring(1).equals(Resources.colors[i])) comboboxPrecise2.setSelected(Resources.colors[i]);

	}

	private void setupChoices()
	{
		int index = comboboxMain.getSelectedIndex();

		comboboxPrecise.setVisible(index < 13);
		comboboxPrecise2.setVisible(index >= 13);

		if (index < 6) comboboxPrecise.setData(new ObjectBase[0]);

		else if (index == 6) comboboxPrecise.setData(Generator.registry.getObjectList(ObjectBase.ACHIEVEMENT));

		else if (index == 13) comboboxPrecise2.setData("stat", statList);

		else if (index == 11 || index == 12) comboboxPrecise.setData(Generator.registry.getList(CGConstants.LIST_MOBS));

		else if (index >= 14) comboboxPrecise2.setData("color", Resources.colors);

		else
		{
			comboboxPrecise.setEnabled(true);
			ObjectBase[] itemList = new ObjectBase[0];
			if (index == 7) itemList = Generator.registry.getList(CGConstants.LIST_CRAFT);
			else if (index == 8) itemList = Generator.registry.getList(CGConstants.LIST_USE);
			else if (index == 9) itemList = Generator.registry.getList(CGConstants.LIST_ITEMS);
			else itemList = Generator.registry.getList(CGConstants.LIST_MINE);

			comboboxPrecise.setData(itemList);
		}
	}

}
