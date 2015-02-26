package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class GameruleOptionsPanel extends OptionsPanel
{

	public static final String[] gamerules = { "commandBlockOutput", "doDaylightCycle", "doFireTick", "doMobLoot", "doMobSpawning", "doTileDrops",
			"keepInventory", "mobGriefing", "naturalRegeneration", "logAdminCommands", "randomTickSpeed", "sendCommandFeedback", "showDeathMessages" };

	private HelpButton buttonHelp;
	private JComboBox<String> comboboxGamerule, comboboxTrueFalse;
	private CLabel labelGamerule;
	private JTextField textfieldTickSpeed;

	public GameruleOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelGamerule, comboboxGamerule);
		addLine(comboboxTrueFalse, textfieldTickSpeed, buttonHelp);
	}

	@Override
	protected void createComponents()
	{
		labelGamerule = new CLabel("GUI:gamerule.choose");

		textfieldTickSpeed = new JTextField(18);
		textfieldTickSpeed.setEnabled(false);

		buttonHelp = new HelpButton(Lang.get("HELP:gamerule.commandBlockOutput"), "commandBlockOutput");

		comboboxGamerule = new JComboBox<String>(gamerules);
		comboboxGamerule.setPreferredSize(new Dimension(200, 20));
		comboboxGamerule.setMinimumSize(new Dimension(200, 20));
		comboboxTrueFalse = new JComboBox<String>(new String[] { Lang.get("GENERAL:true"), Lang.get("GENERAL:false") });
		comboboxTrueFalse.setPreferredSize(new Dimension(200, 20));
		comboboxTrueFalse.setMinimumSize(new Dimension(200, 20));
	}

	@Override
	protected void createListeners()
	{
		comboboxGamerule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				buttonHelp.setData(Lang.get("HELP:gamerule." + (String) comboboxGamerule.getSelectedItem()), (String) comboboxGamerule.getSelectedItem());
				comboboxTrueFalse.setEnabled(!comboboxGamerule.getSelectedItem().equals("randomTickSpeed"));
				textfieldTickSpeed.setEnabled(comboboxGamerule.getSelectedItem().equals("randomTickSpeed"));
			}
		});
	}

	@Override
	public String generateCommand()
	{

		String value;
		if (comboboxGamerule.getSelectedItem().equals("randomTickSpeed"))
		{
			try
			{
				int flag = Integer.parseInt(textfieldTickSpeed.getText());
				if (flag < 0)
				{
					DisplayHelper.warningPositiveInteger();
					return null;
				}
			} catch (Exception ex)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}

			value = textfieldTickSpeed.getText();
		} else value = ((String) comboboxTrueFalse.getSelectedItem()).toLowerCase();

		return "gamerule " + comboboxGamerule.getSelectedItem() + " " + value;

	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		for (int i = 0; i < gamerules.length; i++)
		{
			if (gamerules[i].equals(data.get(CGConstants.DATAID_MODE))) comboboxGamerule.setSelectedIndex(i);
		}
		if (data.get(CGConstants.DATAID_MODE).equals("randomTickSpeed")) textfieldTickSpeed.setText((String) data.get(CGConstants.DATAID_VALUE));
		else if (data.get(CGConstants.DATAID_VALUE).equals("true")) comboboxTrueFalse.setSelectedIndex(0);
		else comboboxTrueFalse.setSelectedIndex(1);

	}

}
