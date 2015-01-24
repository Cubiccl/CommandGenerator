package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class GameruleOptionsPanel extends OptionsPanel
{

	public static final String[] gamerules = { "commandBlockOutput", "doDaylightCycle", "doFireTick", "doMobLoot", "doMobSpawning", "doTileDrops",
			"keepInventory", "mobGriefing", "naturalRegeneration", "logAdminCommands", "randomTickSpeed", "sendCommandFeedback", "showDeathMessages" };

	private CLabel labelGamerule;
	private JTextField textfieldTickSpeed;
	private JButton buttonHelp;
	private JComboBox<String> comboboxGamerule, comboboxTrueFalse;

	public GameruleOptionsPanel()
	{
		super();

		labelGamerule = new CLabel("GUI:gamerule.choose");

		textfieldTickSpeed = new JTextField();
		textfieldTickSpeed.setEnabled(false);

		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Lang.get("HELP:gamerule." + (String) comboboxGamerule.getSelectedItem()), (String) comboboxGamerule.getSelectedItem());
			}
		});

		comboboxGamerule = new JComboBox<String>(gamerules);
		comboboxGamerule.setPreferredSize(new Dimension(200, 20));
		comboboxGamerule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				comboboxTrueFalse.setEnabled(!comboboxGamerule.getSelectedItem().equals("randomTickSpeed"));
				textfieldTickSpeed.setEnabled(comboboxGamerule.getSelectedItem().equals("randomTickSpeed"));
			}
		});
		comboboxTrueFalse = new JComboBox<String>(new String[] { Lang.get("GENERAL:true"), Lang.get("GENERAL:false") });
		comboboxTrueFalse.setPreferredSize(new Dimension(200, 20));

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelGamerule, gbc);
		gbc.gridx++;
		add(comboboxGamerule, gbc);
		gbc.gridx--;
		gbc.gridy++;
		add(comboboxTrueFalse, gbc);
		gbc.gridx++;
		add(textfieldTickSpeed, gbc);
		gbc.gridx++;
		gbc.gridy--;
		add(buttonHelp, gbc);

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
