package commandGenerator.gui.helper.components.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.combobox.LabeledSearchBox;
import commandGenerator.main.FileHelper;
import commandGenerator.main.Resources;
import commandGenerator.main.Settings;

@SuppressWarnings("serial")
public class SettingsPanel extends CPanel
{

	private JRadioButton buttonDefault, buttonCustom;
	private JRadioButton buttonId, buttonName;
	private CButton buttonFolder;
	private LabeledSearchBox comboboxLang;
	private JTextField fieldCustom;
	private ButtonGroup groupFolder, groupSort;
	private CLabel labelFolder, labelSort;
	private Settings opt;

	public SettingsPanel(Settings opt)
	{
		super("GUI:menu.settings");
		this.opt = opt;
		this.initGui();
	}

	private void askFolder()
	{
		JFileChooser chooser = new JFileChooser(new File(this.fieldCustom.getText()));
		chooser.setDialogTitle(Generator.translate("GUI:settings.folder.custom"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
		this.fieldCustom.setText(chooser.getSelectedFile().getPath());
	}

	public String getFolder()
	{
		return this.fieldCustom.getText();
	}

	public String getLang()
	{
		return (String) this.comboboxLang.getSelectedItem();
	}

	public String getSortType()
	{
		if (this.buttonId.isSelected()) return Settings.IDS;
		return Settings.NAMES;
	}

	@Override
	protected void addComponents()
	{
		this.add(this.comboboxLang);
		this.addLine(this.labelSort, this.buttonId, this.buttonName);
		this.add(this.labelFolder);
		this.addLine(this.buttonDefault, this.buttonCustom);
		this.addLine(this.fieldCustom, this.buttonFolder);
	}

	@Override
	protected void createComponents()
	{

		this.fieldCustom = new JTextField(18);
		this.fieldCustom.setEditable(false);
		this.fieldCustom.setText(Resources.folder);

		this.labelFolder = new CLabel("GUI:settings.folder", true);
		this.labelFolder.setSize(new Dimension(600, 60));
		this.labelSort = new CLabel("GUI:settings.sort");

		this.buttonFolder = new CButton("GUI:settings.folder.select");

		this.comboboxLang = new LabeledSearchBox("GUI:settings.lang", this.opt.getLangs(), null);
		this.comboboxLang.setSelectedItem(this.opt.getLanguage());

		this.buttonDefault = new JRadioButton(Generator.translate("GUI:settings.folder.default"));
		this.buttonCustom = new JRadioButton(Generator.translate("GUI:settings.folder.custom"));

		this.buttonDefault.setSelected(this.fieldCustom.getText().equals(FileHelper.getDefaultFolder()));
		this.buttonCustom.setSelected(true);
		this.buttonCustom.setSelected(!this.fieldCustom.getText().equals(FileHelper.getDefaultFolder()));

		this.groupFolder = new ButtonGroup();
		this.groupFolder.add(this.buttonCustom);
		this.groupFolder.add(this.buttonDefault);

		this.buttonId = new JRadioButton(Generator.translate("GUI:settings.sort.id"));
		this.buttonId.setSelected(this.opt.getSortType().equals(Settings.IDS));
		this.buttonName = new JRadioButton(Generator.translate("GUI:settings.sort.name"));
		this.buttonName.setSelected(this.opt.getSortType().equals(Settings.NAMES));

		this.groupSort = new ButtonGroup();
		this.groupSort.add(this.buttonId);
		this.groupSort.add(this.buttonName);
	}

	@Override
	protected void createListeners()
	{
		this.buttonFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				askFolder();
			}
		});

		this.buttonDefault.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setText(FileHelper.getDefaultFolder());
			}
		});
		this.buttonCustom.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				fieldCustom.setEnabled(buttonCustom.isSelected());
				buttonFolder.setEnabled(buttonCustom.isSelected());
			}
		});
	}

}
