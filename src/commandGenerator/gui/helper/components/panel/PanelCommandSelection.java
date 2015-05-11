package commandGenerator.gui.helper.components.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Command;
import commandGenerator.arguments.objects.Item;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.CTextField;
import commandGenerator.gui.helper.components.OptionsTab;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.combobox.LabeledSearchBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PanelCommandSelection extends JPanel implements IBox
{

	private CButton buttonGenerate, buttonCopy, buttonGenerator;
	private CCheckBox checkboxEdit;
	private LabeledSearchBox comboboxCommand;
	private CLabel labelWarning;
	private boolean main;
	private Command selectedCommand;
	public OptionsTab tabOptions;
	private JTextArea textareaStructure;
	private CTextField textfieldCommand;

	/** The panel used to select the Command. */
	public PanelCommandSelection(boolean main)
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Generator.translate("GUI:command.title")));
		setLayout(new GridBagLayout());
		this.selectedCommand = Command.achievement;
		this.main = main;

		labelWarning = new CLabel("GUI:command.warning");
		labelWarning.setVisible(false);
		labelWarning.setForeground(Color.RED);

		if (this.main)
		{
			buttonGenerate = new CButton("GUI:command.generate");
			buttonGenerate.setIcon(((Item) Generator.registry.getObjectFromId("command_block")).getTexture(0));
			buttonGenerate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					generate();
				}
			});
			buttonGenerate.setForeground(Color.BLUE);
			buttonGenerate.setPreferredSize(new Dimension(200, 40));
			buttonGenerate.setMinimumSize(new Dimension(200, 40));

			buttonCopy = new CButton("GUI:command.clipboard");
			buttonCopy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					copy();
				}
			});

			buttonGenerator = new CButton("GUI:command.generator");
			buttonGenerator.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					generator();
				}
			});

			textfieldCommand = new CTextField();
			textfieldCommand.setEditable(false);
			textfieldCommand.setPreferredSize(new Dimension(600, 20));
			textfieldCommand.setMinimumSize(new Dimension(600, 20));

			checkboxEdit = new CCheckBox("GUI:command.edit");
			checkboxEdit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					textfieldCommand.setEditable(checkboxEdit.isSelected());
				}
			});
		}

		textareaStructure = new JTextArea(Command.achievement.getStructure());
		textareaStructure.setEditable(false);

		Command[] commands = Generator.registry.getCommands();
		String[] names = new String[commands.length];
		for (int j = 0; j < commands.length; j++)
		{
			names[j] = commands[j].getId();
		}

		this.comboboxCommand = new LabeledSearchBox("GUI:command.choose", names, this);

		tabOptions = this.selectedCommand.getOptionsTab();
		tabOptions.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.add(this.comboboxCommand, gbc);
		gbc.gridx++;
		gbc.gridx++;
		gbc.gridwidth = 1;
		if (this.main) this.add(this.checkboxEdit, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		add(textareaStructure, gbc);
		if (this.main) gbc.gridy++;
		if (this.main) add(textfieldCommand, gbc);
		gbc.gridwidth = 1;

		if (this.main)
		{
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.gridwidth = 3;
			add(labelWarning, gbc);
			gbc.gridwidth = 1;
			gbc.gridy++;
			add(buttonGenerate, gbc);
			gbc.gridx++;
			add(buttonCopy, gbc);
			gbc.gridx++;
			add(buttonGenerator, gbc);
		}

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 4;
		add(tabOptions, gbc);
		gbc.gridwidth = 1;

		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0)
			{}

			@Override
			public void componentMoved(ComponentEvent arg0)
			{}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				tabOptions.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 190));
			}

			@Override
			public void componentShown(ComponentEvent arg0)
			{}
		});
	}

	public void copy()
	{
		StringSelection copy = new StringSelection(textfieldCommand.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(copy, null);
	}

	public String generate()
	{
		DisplayHelper.log("Generating /" + this.selectedCommand.getId() + "  -----------------------------------------------------------");

		String command = this.selectedCommand.generate();
		if (command != null)
		{
			if (main)
			{
				this.textfieldCommand.setText("/" + command);
				this.labelWarning.setVisible(command.length() > 100);
			}
			DisplayHelper.log("/" + this.selectedCommand.getId() + " successfully generated!");
		}
		return command;
	}

	private void generator()
	{
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel(Generator.translate("GUI:command.import"));
		CTextField field = new CTextField(30);
		panel.add(label);
		panel.add(field);

		if (DisplayHelper.showQuestion(panel, Generator.translate("GUI:command.generator"))) return;
		String command = field.getText();
		if (command == null) return;
		DisplayHelper.log("Generating display from command : " + command + "  -----------------------------------------------------------");

		if (command.startsWith("/")) command = command.substring(1);
		Command newCommand = Command.identify(command);
		if (newCommand == null) return;

		setSelectedCommand(newCommand);
		newCommand.generateFrom(command);
		setOptionsPanel(newCommand.getOptionsTab());
	}

	/** Changes the command panel. */
	public void setOptionsPanel(OptionsTab panel)
	{
		if (panel == null) return;
		tabOptions.setVisible(false);
		remove(tabOptions);
		tabOptions = panel;
		tabOptions.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));
		tabOptions.setVisible(true);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		add(tabOptions, gbc);
		gbc.gridwidth = 1;

		Generator.gui.setVisible(true);

	}

	public void setSelectedCommand(Command command)
	{
		comboboxCommand.setSelectedItem(command.getId());
		setOptionsPanel(command.getOptionsTab());
	}

	public void setupFrom(String command)
	{
		if (command.startsWith("/")) command = command.substring(1);
		Command newCommand = Command.identify(command);
		if (newCommand == null) return;

		this.setSelectedCommand(newCommand);
		newCommand.generateFrom(command);
		this.setOptionsPanel(newCommand.getOptionsTab());
	}

	public void updateLang()
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Generator.translate("GUI:command")));
		labelWarning.updateLang();
		buttonCopy.updateLang();
		buttonGenerate.updateLang();
		tabOptions.updateLang();
		comboboxCommand.updateLang();
	}

	public void reset()
	{
		this.selectedCommand.reset();
	}

	@Override
	public void updateCombobox()
	{
		Command selection = Generator.registry.getCommandFromId(this.comboboxCommand.getSelectedItem());
		if (selection != null)
		{
			this.selectedCommand = selection;
			this.setOptionsPanel(this.selectedCommand.getOptionsTab());
		}
	}

}
