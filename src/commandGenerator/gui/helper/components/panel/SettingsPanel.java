package commandGenerator.gui.helper.components.panel;

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

import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.combobox.TextCombobox;
import commandGenerator.main.FileHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;
import commandGenerator.main.Settings;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel
{

	private JRadioButton buttonDefault, buttonCustom;
	private JRadioButton buttonId, buttonName;
	private CButton buttonFolder;
	private TextCombobox comboboxLang;
	private JTextField fieldCustom;
	private GridBagConstraints gbc = new GridBagConstraints();
	private ButtonGroup groupFolder, groupSort;
	private CLabel labelFolder, labelSort;

	public SettingsPanel(Settings opt)
	{
		super(new GridBagLayout());

		fieldCustom = new JTextField(18);
		fieldCustom.setEditable(false);
		fieldCustom.setText(Resources.folder);

		labelFolder = new CLabel("GUI:settings.folder", true);
		labelSort = new CLabel("GUI:settings.sort");

		buttonFolder = new CButton("GUI:settings.folder.select");
		buttonFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				askFolder();
			}
		});

		comboboxLang = new TextCombobox("GUI:settings.lang", opt.getLangs(), null);
		comboboxLang.setSelected(opt.getLanguage());

		buttonDefault = new JRadioButton(Lang.get("GUI:settings.folder.default"));
		buttonDefault.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setText(FileHelper.getDefaultFolder());
			}
		});
		buttonCustom = new JRadioButton(Lang.get("GUI:settings.folder.custom"));
		buttonCustom.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setEnabled(buttonCustom.isSelected());
				buttonFolder.setEnabled(buttonCustom.isSelected());
			}
		});

		buttonDefault.setSelected(fieldCustom.getText().equals(FileHelper.getDefaultFolder()));
		buttonCustom.setSelected(true);
		buttonCustom.setSelected(!fieldCustom.getText().equals(FileHelper.getDefaultFolder()));

		groupFolder = new ButtonGroup();
		groupFolder.add(buttonCustom);
		groupFolder.add(buttonDefault);

		buttonId = new JRadioButton(Lang.get("GUI:settings.sort.id"));
		buttonId.setSelected(opt.getSortType().equals(Settings.IDS));
		buttonName = new JRadioButton(Lang.get("GUI:settings.sort.name"));
		buttonName.setSelected(opt.getSortType().equals(Settings.NAMES));

		groupSort = new ButtonGroup();
		groupSort.add(buttonId);
		groupSort.add(buttonName);

		JPanel panelSort = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelSort.add(labelSort, gbc);
		gbc.gridx++;
		panelSort.add(buttonId);
		gbc.gridx++;
		panelSort.add(buttonName);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(comboboxLang, gbc);
		gbc.gridy++;
		add(panelSort, gbc);
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

	public String getFolder()
	{
		return fieldCustom.getText();
	}

	public String getLang()
	{
		return comboboxLang.getValue();
	}

	public String getSortType()
	{
		if (this.buttonId.isSelected()) return Settings.IDS;
		return Settings.NAMES;
	}

}
