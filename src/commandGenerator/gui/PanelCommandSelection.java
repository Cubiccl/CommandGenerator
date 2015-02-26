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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.gui.options.AchievementOptionsPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class PanelCommandSelection extends JPanel
{

	public static PanelCommandSelection instance;
	private CButton buttonGenerate, buttonCopy, buttonGenerator;
	private JButton buttonHelpCommand;
	private CCheckBox checkboxEdit;
	private JComboBox<String> comboboxCommand;
	private GridBagConstraints gbc = new GridBagConstraints();
	private CLabel labelChooseCommand, labelWarning;
	public OptionsPanel panelOptions;
	private JScrollPane scrollpane;
	private JTextArea textareaStructure;
	private JTextField textfieldSearchbar, textfieldCommand;

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
				DisplayHelper.showHelp(Registerer.getCommandFromId((String) comboboxCommand.getSelectedItem()).getDescription(),
						(String) comboboxCommand.getSelectedItem());
			}
		});

		if (main)
		{
			buttonGenerate = new CButton("GUI:command.generate");
			buttonGenerate.setIcon(((Item) Registerer.getObjectFromId("command_block")).getTexture(0));
			buttonGenerate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					DisplayHelper.log("Generating /" + CommandGenerator.opt.selectedCommand.getId()
							+ "  -----------------------------------------------------------");

					String command = panelOptions.generateCommand();
					if (command != null)
					{
						textfieldCommand.setText("/" + command);
						DisplayHelper.log("/" + CommandGenerator.opt.selectedCommand.getId() + " successfully generated!");
					}
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

			checkboxEdit = new CCheckBox(Constants.DATAID_NONE, "GUI:command.edit");
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

					if (comboboxCommand.getSelectedItem() == null) return;
					String commandName = ((String) comboboxCommand.getSelectedItem()).toLowerCase();
					Command[] commands = Registerer.getCommandArray();

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
				Command[] commands = Registerer.getCommandArray();

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

		textareaStructure = new JTextArea(Resources.structureList[0]);
		textareaStructure.setEditable(false);

		Command[] commands = Registerer.getCommandArray();
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
				CommandGenerator.opt.selectedCommand = Registerer.getCommandFromId((String) comboboxCommand.getSelectedItem());
				Command[] commands = Registerer.getCommandArray();

				String[] names = new String[commands.length];
				for (int j = 0; j < commands.length; j++)
				{
					names[j] = commands[j].getId();
				}
				comboboxCommand.setModel(new JComboBox<String>(names).getModel());
				comboboxCommand.setSelectedItem(CommandGenerator.opt.selectedCommand.getId());
				textfieldSearchbar.setText("");
				textareaStructure.setText(Resources.structureList[comboboxCommand.getSelectedIndex()]);
				DisplayHelper.log("Setting up /" + CommandGenerator.opt.selectedCommand.getId());
				setOptionsPanel(CommandGenerator.opt.selectedCommand.getOptionsPanel());
			}
		});

		panelOptions = new AchievementOptionsPanel();
		scrollpane = new JScrollPane(panelOptions);
		scrollpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), Lang.get("GENERAL:options")));
		scrollpane.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));
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
		add(comboboxCommand, gbc);
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

		addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent arg0)
			{
			}
			public void componentResized(ComponentEvent arg0)
			{
				scrollpane.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));
			}
			public void componentMoved(ComponentEvent arg0)
			{
			}
			public void componentHidden(ComponentEvent arg0)
			{
			}
		});
	}

	public String generateCommand()
	{
		return panelOptions.generateCommand();
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
			selected = Registerer.getCommandFromId(field.getText().split(" ")[0]);
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:wrong_command");
			return;
		}
		CommandGenerator.opt.selectedCommand = selected;
		comboboxCommand.setSelectedItem(CommandGenerator.opt.selectedCommand.getId());

		OptionsPanel panelNew = selected.getOptionsPanel();
		Map<String, Object> data = selected.generateSetup(field.getText());
		if (data != null)
		{
			panelNew.setupFrom(data);
		}
		setOptionsPanel(panelNew);
	}

	/** Changes the command panel. */
	public void setOptionsPanel(OptionsPanel panel)
	{
		remove(scrollpane);
		panelOptions = panel;

		scrollpane = new JScrollPane(panelOptions);
		scrollpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), Lang.get("GENERAL:options")));
		scrollpane.setPreferredSize(new Dimension(getSize().width - 50, getSize().height - 180));
		scrollpane.getVerticalScrollBar().setUnitIncrement(20);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(20);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		add(scrollpane, gbc);
		gbc.gridwidth = 1;

		CommandGenerator.gui.setVisible(true);

	}

	public void setSelectedCommand(Command command)
	{
		comboboxCommand.setSelectedItem(command.getId());
		setOptionsPanel(command.getOptionsPanel());
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

}
