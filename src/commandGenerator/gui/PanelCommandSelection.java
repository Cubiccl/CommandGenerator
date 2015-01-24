package commandGenerator.gui;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import commandGenerator.CommandGenerator;
import commandGenerator.arguments.objects.Command;
import commandGenerator.arguments.objects.Commands;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.options.AchievementOptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class PanelCommandSelection extends JPanel
{

	private CLabel labelChooseCommand, labelWarning;
	private JButton buttonHelpCommand;
	private CButton buttonGenerate, buttonCopy, buttonGenerator;
	private CCheckBox checkboxEdit;
	private JTextField textfieldSearchbar, textfieldCommand;
	private JTextArea textareaStructure;
	private JComboBox<String> comboboxCommands;
	public OptionsPanel panelOptions;
	private JScrollPane scrollpane;
	private GridBagConstraints gbc = new GridBagConstraints();
	public static PanelCommandSelection instance;

	/** The panel used to select the Command. */
	public PanelCommandSelection(boolean main)
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:command.title")));
		setLayout(new GridBagLayout());
		instance = this;

		labelChooseCommand = new CLabel("GUI:command.choose");
		labelWarning = new CLabel("GUI:command.warning");
		labelWarning.setVisible(false);
		labelWarning.setForeground(Color.RED);

		buttonHelpCommand = new JButton("?");
		buttonHelpCommand.setPreferredSize(new Dimension(50, 50));
		buttonHelpCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Commands.getCommandFromId((String) comboboxCommands.getSelectedItem()).getDescription(),
						(String) comboboxCommands.getSelectedItem());
			}
		});

		if (main)
		{
			buttonGenerate = new CButton("GUI:command.generate");
			buttonGenerate.setIcon(((Item) ObjectBase.getObjectFromId("command_block")).getTexture(0));
			buttonGenerate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					DisplayHelper.log("Generating /" + CommandGenerator.opt.selectedCommand.getId()
							+ "  -----------------------------------------------------------");

					String command = panelOptions.generateCommand();
					if (!(command == null))
					{
						textfieldCommand.setText("/" + command);
						DisplayHelper.log("/" + CommandGenerator.opt.selectedCommand.getId() + " successfully generated!");
					}
				}
			});
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

			textfieldCommand = new JTextField();
			textfieldCommand.setEditable(false);
			textfieldCommand.setPreferredSize(new Dimension(600, 20));

			checkboxEdit = new CCheckBox(CGConstants.DATAID_NONE, "GUI:command.edit");
			checkboxEdit.addActionListener(new ActionListener() {
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

					if (comboboxCommands.getSelectedItem() == null) return;
					String commandName = ((String) comboboxCommands.getSelectedItem()).toLowerCase();

					for (int i = 0; i < Commands.commands.length; i++)
					{
						if (commandName.equals(Commands.commands[i].getId().toLowerCase()))
						{
							String[] names = new String[Commands.commands.length];
							for (int j = 0; j < Commands.commands.length; j++)
							{
								names[j] = Commands.commands[j].getId();
							}
							comboboxCommands.setModel(new JComboBox<String>(names).getModel());
							comboboxCommands.setSelectedIndex(i);
						}
					}

					textfieldSearchbar.setText("");
					return;
				}

				List<String> matchingNames = new ArrayList<String>();
				String name = textfieldSearchbar.getText().toLowerCase();

				for (int i = 0; i < Commands.commands.length; i++)
				{
					if (Commands.commands[i].getId().contains(name)) matchingNames.add(Commands.commands[i].getId());
				}

				comboboxCommands.setModel(new JComboBox<String>(matchingNames.toArray(new String[matchingNames.size()])).getModel());
			}

			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});

		textareaStructure = new JTextArea(Resources.structureList[0]);
		textareaStructure.setEditable(false);

		String[] names = new String[Commands.commands.length];
		for (int j = 0; j < Commands.commands.length; j++)
		{
			names[j] = Commands.commands[j].getId();
		}
		comboboxCommands = new JComboBox<String>(names);
		comboboxCommands.setPreferredSize(new Dimension(300, 20));
		comboboxCommands.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CommandGenerator.opt.selectedCommand = Commands.getCommandFromId((String) comboboxCommands.getSelectedItem());

				String[] names = new String[Commands.commands.length];
				for (int j = 0; j < Commands.commands.length; j++)
				{
					names[j] = Commands.commands[j].getId();
				}
				comboboxCommands.setModel(new JComboBox<String>(names).getModel());
				comboboxCommands.setSelectedItem(CommandGenerator.opt.selectedCommand.getId());
				textfieldSearchbar.setText("");
				textareaStructure.setText(Resources.structureList[comboboxCommands.getSelectedIndex()]);
				DisplayHelper.log("Setting up /" + CommandGenerator.opt.selectedCommand.getId());
				setOptionsPanel(CommandGenerator.opt.selectedCommand.getOptionsPanel());
			}
		});

		panelOptions = new AchievementOptionsPanel();
		scrollpane = new JScrollPane(panelOptions);
		scrollpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), Lang.get("GENERAL:options")));
		scrollpane.setPreferredSize(new Dimension(1000, 550));
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(20);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(labelChooseCommand, gbc);
		gbc.gridx++;
		gbc.gridheight = 1;
		add(textfieldSearchbar, gbc);
		gbc.gridy++;
		add(comboboxCommands, gbc);
		gbc.gridx++;
		gbc.gridheight = 2;
		if (main) add(checkboxEdit, gbc);
		if (main) gbc.gridx++;
		add(buttonHelpCommand, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		add(textareaStructure, gbc);
		if (main) gbc.gridy++;
		if (main) add(textfieldCommand, gbc);
		gbc.gridwidth = 1;

		if (main)
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
		add(scrollpane, gbc);
		gbc.gridwidth = 1;

	}

	private void generator()
	{
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel(Lang.get("GUI:command.import"));
		JTextField field = new JTextField(30);
		panel.add(label);
		panel.add(field);

		if (DisplayHelper.showQuestion(panel, Lang.get("GUI:command.generator"))) return;
		DisplayHelper.log("Generating display from command : " + field.getText() + "  -----------------------------------------------------------");
		Command selected;
		try
		{
			if (field.getText().startsWith("/")) field.setText(field.getText().substring(1));
			selected = Commands.getCommandFromId(field.getText().split(" ")[0]);
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:wrong_command");
			return;
		}
		CommandGenerator.opt.selectedCommand = selected;
		comboboxCommands.setSelectedItem(CommandGenerator.opt.selectedCommand.getId());

		OptionsPanel panelNew = selected.getOptionsPanel();
		Map<String, Object> data = selected.generateSetup(field.getText());
		if (data != null)
		{
			panelNew.setupFrom(data);
		}
		setOptionsPanel(panelNew);
	}

	public String generateCommand()
	{
		return panelOptions.generateCommand();
	}

	/** Changes the command panel. */
	public void setOptionsPanel(OptionsPanel panel)
	{

		remove(scrollpane);
		panelOptions = panel;

		scrollpane = new JScrollPane(panelOptions);
		scrollpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), Lang.get("GENERAL:options")));
		scrollpane.setPreferredSize(new Dimension(1000, 550));
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(20);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		add(scrollpane, gbc);
		gbc.gridwidth = 1;

		CommandGenerator.gui.setVisible(true);

	}

	public void updateLang()
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), Lang.get("GUI:command")));
		scrollpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), Lang.get("GENERAL:options")));
		labelChooseCommand.updateLang();
		labelWarning.updateLang();
		buttonCopy.updateLang();
		buttonGenerate.updateLang();
		panelOptions.updateLang();
	}

	public void setSelectedCommand(Command command)
	{
		comboboxCommands.setSelectedItem(command.getId());
		setOptionsPanel(command.getOptionsPanel());
	}

}
