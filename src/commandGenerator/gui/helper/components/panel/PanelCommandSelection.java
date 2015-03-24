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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import commandGenerator.CommandGenerator;
import commandGenerator.arguments.command.Command;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.OptionsTab;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class PanelCommandSelection extends JPanel
{

	private CButton buttonGenerate, buttonCopy, buttonGenerator;
	private CCheckBox checkboxEdit;
	private JComboBox<String> comboboxCommand;
	private GridBagConstraints gbc = new GridBagConstraints();
	private CLabel labelChooseCommand, labelWarning;
	private boolean main;
	private Command selectedCommand;
	public OptionsTab tabOptions;
	private JTextArea textareaStructure;
	private JTextField textfieldSearchbar, textfieldCommand;

	/** The panel used to select the Command. */
	public PanelCommandSelection(boolean main)
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:command.title")));
		setLayout(new GridBagLayout());
		this.selectedCommand = Command.achievement;
		this.main = main;

		labelChooseCommand = new CLabel("GUI:command.choose");
		labelWarning = new CLabel("GUI:command.warning");
		labelWarning.setVisible(false);
		labelWarning.setForeground(Color.RED);

		if (this.main)
		{
			buttonGenerate = new CButton("GUI:command.generate");
			buttonGenerate.setIcon(((Item) Registry.getObjectFromId("command_block")).getTexture(0));
			buttonGenerate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					generate();
				}
			});
			buttonGenerate.setPreferredSize(new Dimension(200, 40));
			buttonGenerate.setForeground(Color.BLUE);

			buttonCopy = new CButton("GUI:command.clipboard");
			buttonCopy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					StringSelection copy = new StringSelection(textfieldCommand.getText());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(copy, null);
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
			buttonGenerator.setPreferredSize(new Dimension(200, 20));

			textfieldCommand = new JTextField();
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

		textfieldSearchbar = new JTextField(27);
		textfieldSearchbar.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0)
			{}

			@Override
			public void keyReleased(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == 10)
				{

					if (comboboxCommand.getSelectedItem() == null) return;
					String commandName = ((String) comboboxCommand.getSelectedItem()).toLowerCase();
					Command[] commands = Registry.getCommands();

					for (int i = 0; i < commands.length; i++)
					{
						if (commandName.equals(commands[i].getId().toLowerCase()))
						{
							String[] names = new String[commands.length];
							for (int j = 0; j < commands.length; j++)
							{
								names[j] = commands[j].getId();
							}
							comboboxCommand.setModel(new JComboBox<String>(names).getModel());
							comboboxCommand.setSelectedIndex(i);
						}
					}

					textfieldSearchbar.setText("");
					return;
				}

				List<String> matchingNames = new ArrayList<String>();
				String name = textfieldSearchbar.getText().toLowerCase();
				Command[] commands = Registry.getCommands();

				for (int i = 0; i < commands.length; i++)
				{
					if (commands[i].getId().contains(name)) matchingNames.add(commands[i].getId());
				}

				comboboxCommand.setModel(new JComboBox<String>(matchingNames.toArray(new String[matchingNames.size()])).getModel());
			}

			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});

		textareaStructure = new JTextArea(Command.achievement.getStructure());
		textareaStructure.setEditable(false);

		Command[] commands = Registry.getCommands();
		String[] names = new String[commands.length];
		for (int j = 0; j < commands.length; j++)
		{
			names[j] = commands[j].getId();
		}

		comboboxCommand = new JComboBox<String>(names);
		comboboxCommand.setPreferredSize(new Dimension(300, 20));
		comboboxCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectedCommand = Registry.getCommandFromId((String) comboboxCommand.getSelectedItem());
				Command[] commands = Registry.getCommands();

				String[] names = new String[commands.length];
				for (int j = 0; j < commands.length; j++)
				{
					names[j] = commands[j].getId();
				}
				comboboxCommand.setModel(new JComboBox<String>(names).getModel());
				comboboxCommand.setSelectedItem(selectedCommand.getId());
				textfieldSearchbar.setText("");
				textareaStructure.setText(selectedCommand.getStructure());
				DisplayHelper.log("Setting up /" + selectedCommand.getId());
				setOptionsPanel(selectedCommand.getOptionsTab());
			}
		});

		tabOptions = this.selectedCommand.getOptionsTab();
		tabOptions.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(labelChooseCommand, gbc);
		gbc.gridx++;
		gbc.gridheight = 1;
		add(textfieldSearchbar, gbc);
		gbc.gridy++;
		add(comboboxCommand, gbc);
		gbc.gridx++;
		gbc.gridheight = 2;
		if (this.main) add(checkboxEdit, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		add(textareaStructure, gbc);
		if (this.main) gbc.gridy++;
		if (this.main) add(textfieldCommand, gbc);
		gbc.gridwidth = 1;

		if (this.main)
		{
			gbc.gridx = 0;
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

	public String generate()
	{
		DisplayHelper.log("Generating /" + this.selectedCommand.getId() + "  -----------------------------------------------------------");

		String command = this.selectedCommand.generate();
		if (command != null)
		{
			if (this.main) this.textfieldCommand.setText("/" + command);
			DisplayHelper.log("/" + this.selectedCommand.getId() + " successfully generated!");
		}
		return command;
	}

	private void generator()
	{
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel(Lang.get("GUI:command.import"));
		JTextField field = new JTextField(30);
		panel.add(label);
		panel.add(field);

		if (DisplayHelper.showQuestion(panel, Lang.get("GUI:command.generator"))) return;
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

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		add(tabOptions, gbc);
		gbc.gridwidth = 1;

		CommandGenerator.gui.setVisible(true);

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
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:command")));
		labelChooseCommand.updateLang();
		labelWarning.updateLang();
		buttonCopy.updateLang();
		buttonGenerate.updateLang();
		tabOptions.updateLang();
	}

}
