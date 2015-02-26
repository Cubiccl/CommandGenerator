package commandGenerator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.CommandGenerator;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.Lang;
import commandGenerator.main.Settings;

@SuppressWarnings("serial")
public class SettingsPanel extends HelperPanel
{

	private JLabel labelFolder, labelLang;
	private JTextField fieldCustom;
	private JButton buttonSelectCustom;
	private JComboBox<String> boxLang;
	private JRadioButton buttonAppdata, buttonNormal, buttonCustom;
	private ButtonGroup group;

	public SettingsPanel()
	{
		super(Constants.DATAID_NONE, "GUI:menu.settings");
	}

	@Override
	protected void addComponents()
	{
		addLine(labelLang, boxLang);
		add(labelFolder);
		addLine(buttonAppdata, buttonNormal);
		addLine(buttonCustom, fieldCustom, buttonSelectCustom);
	}

	@Override
	protected void createComponents()
	{
		labelFolder = new JLabel(Lang.get("GUI:options.folder"));
		labelLang = new JLabel(Lang.get("GUI:options.lang"));

		fieldCustom = new JTextField(20);

		buttonSelectCustom = new JButton(Lang.get("GUI:options.folder.select"));

		boxLang = new JComboBox<String>(Settings.languageNames);

		buttonAppdata = new JRadioButton(Lang.get("GUI:options.folder.appdata"));
		buttonNormal = new JRadioButton(Lang.get("GUI:options.folder.normal"));
		buttonCustom = new JRadioButton(Lang.get("GUI:options.folder.custom"));

		group = new ButtonGroup();
		group.add(buttonAppdata);
		group.add(buttonNormal);
		group.add(buttonCustom);

		buttonAppdata.setSelected(true);
		fieldCustom.setEnabled(false);
		buttonSelectCustom.setEnabled(false);
	}

	@Override
	protected void createListeners()
	{
		buttonSelectCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser dialog = new JFileChooser();
				dialog.setDialogTitle(Lang.get("GUI:options.folder.custom"));
				dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dialog.setAcceptAllFileFilterUsed(false);
				if (dialog.showOpenDialog(CommandGenerator.gui) != JFileChooser.APPROVE_OPTION) return;
				fieldCustom.setText(dialog.getSelectedFile().getPath());
			}
		});
		buttonCustom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0)
			{
				fieldCustom.setEnabled(buttonCustom.isSelected());
				buttonSelectCustom.setEnabled(buttonCustom.isSelected());
			}
		});
	}

	public int getLanguage()
	{
		return boxLang.getSelectedIndex();
	}

}
