package commandGenerator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.CommandGenerator;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.TextCombobox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.FileHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel
{

	private JTextField fieldCustom;
	private CLabel labelFolder;
	private CButton buttonFolder;
	private TextCombobox comboboxLang;
	private JRadioButton buttonDefault, buttonCustom;
	private ButtonGroup group;
	private GridBagConstraints gbc = new GridBagConstraints();

	public SettingsPanel()
	{
		super(new GridBagLayout());

		fieldCustom = new JTextField(18);
		fieldCustom.setEditable(false);
		fieldCustom.setText(Resources.folder);

		labelFolder = new CLabel("GUI:settings.folder", true);

		buttonFolder = new CButton("GUI:settings.folder.select");
		buttonFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				askFolder();
			}
		});

		comboboxLang = new TextCombobox(CGConstants.DATAID_NONE, "GUI:settings.lang", CommandGenerator.opt.getLangs(), null);

		buttonDefault = new JRadioButton(Lang.get("GUI:settings.folder.default"));
		buttonDefault.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setText(FileHelper.getDefaultFolder());
			}
		});
		buttonCustom = new JRadioButton(Lang.get("GUI:settings.folder.custom"));
		buttonCustom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setEnabled(buttonCustom.isSelected());
				buttonFolder.setEnabled(buttonCustom.isSelected());
			}
		});

		buttonDefault.setSelected(fieldCustom.getText().equals(FileHelper.getDefaultFolder()));
		buttonCustom.setSelected(true);
		buttonCustom.setSelected(!fieldCustom.getText().equals(FileHelper.getDefaultFolder()));

		group = new ButtonGroup();
		group.add(buttonCustom);
		group.add(buttonDefault);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(comboboxLang, gbc);

		gbc.gridy++;
		add(labelFolder, gbc);

		gbc.gridwidth = 1;
		gbc.gridy++;
		add(buttonDefault, gbc);
		gbc.gridx++;
		add(buttonCustom, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		add(fieldCustom, gbc);
		gbc.gridx++;
		add(buttonFolder, gbc);
	}

	private void askFolder()
	{
		JFileChooser chooser = new JFileChooser(new File(fieldCustom.getText()));
		chooser.setDialogTitle(Lang.get("GUI:settings.folder.custom"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
		fieldCustom.setText(chooser.getSelectedFile().getPath());
	}

	public String getLang()
	{
		return comboboxLang.getValue();
	}

	public String getFolder()
	{
		return fieldCustom.getText();
	}

}
