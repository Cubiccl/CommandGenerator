package generator.gui.panel.object;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.gui.button.CButton;
import generator.gui.combobox.CChoiceCombobox;
import generator.gui.combobox.CCombobox;
import generator.gui.panel.CPanel;
import generator.gui.panel.CPanelVertical;
import generator.gui.textfield.CEntry;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.interfaces.IConfirmState;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;
import generator.registry.instance.Target;
import generator.registry.instance.TargetArgument;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

/** Allows selecting a Target. */
@SuppressWarnings("serial")
public class PanelTarget extends CPanel implements IClickEvent, IConfirmState
{
	private static final int SELECTOR = 0, ADD = 1, REMOVE = 2;

	/** The area to display added Arguments. */
	private CTextArea areaArguments;
	private ArrayList<TargetArgument> arguments;
	/** Buttons to add or remove Arguments. */
	private CButton buttonAdd, buttonRemove;
	/** Combobox to choose an Argument to add. */
	private CChoiceCombobox comboboxArgument;
	/** Combobox to choose the selector. */
	private CChoiceCombobox comboboxSelector;
	/** Entry to type in a Player's name. */
	private CEntry entryName;
	/** The ID of the title. */
	private Text title;

	/** Creates a new Panel Target.
	 * 
	 * @param title - Its title.
	 * @param targetType - The type of targets to allow. */
	public PanelTarget(Text title, int targetType)
	{
		super();
		this.title = title;
		this.arguments = new ArrayList<TargetArgument>();

		this.buttonAdd = new CButton(new Text("GUI", "general.add"));
		this.buttonAdd.addActionListener(new ClickEvent(this, ADD));
		this.buttonRemove = new CButton(new Text("GUI", "general.remove"));
		this.buttonRemove.addActionListener(new ClickEvent(this, REMOVE));

		this.comboboxArgument = new CChoiceCombobox("argument", TargetArgument.ARGUMENT_IDS);
		this.comboboxSelector = new CChoiceCombobox("target", Target.SELECTORS);
		this.comboboxSelector.addActionListener(new ClickEvent(this, SELECTOR));

		this.entryName = new CEntry(new Text("GUI", "target.name"));
		this.entryName.setVisible(false);

		this.areaArguments = new CTextArea(new Text("GUI", "target.argument.nothing", false).getValue());
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(this.areaArguments);
		scrollpane.setPreferredSize(new Dimension(200, 100));
		scrollpane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		this.gbc.gridwidth = 2;
		this.add(this.comboboxSelector, this.gbc);
		this.gbc.gridy++;
		this.add(this.entryName, this.gbc);
		this.gbc.gridy++;
		this.add(this.comboboxArgument, this.gbc);
		this.gbc.gridy++;
		this.gbc.gridwidth = 1;
		this.add(this.buttonAdd, this.gbc);
		this.gbc.gridx++;
		this.add(this.buttonRemove, this.gbc);
		this.gbc.gridx--;
		this.gbc.gridy++;
		this.gbc.gridwidth = 2;
		this.add(scrollpane, this.gbc);

		this.updateLang();
	}

	@Override
	public boolean confirm(boolean cancel, CPanel component)
	{
		if (component.getName() != null)
		{
			this.arguments.remove(((CCombobox) component.getComponent(0)).getSelectedIndex());
			this.updateTextArea();
			return true;
		}

		TargetArgument managing = this.arguments.get(this.arguments.size() - 1);
		if (cancel)
		{
			this.arguments.remove(managing);
			return true;
		}

		if (managing.createValue(component))
		{
			this.updateTextArea();
			return true;
		}
		return false;
	}

	/** @return The Target input by the user.
	 * @throws GenerationException if a value is not correct. */
	public Target generateTarget() throws GenerationException
	{
		if (this.comboboxSelector.getSelectedValue().equals(Target.PLAYER_NAME))
		{
			if (this.entryName.getText().equals("")) throw new GenerationException(new Text("GUI", "error.missing", false));
			if (this.entryName.getText().contains(" ")) throw new GenerationException(new Text("GUI", "error.space", false));
			return new Target(this.entryName.getText());
		}
		return new Target(this.comboboxSelector.getSelectedIndex(), this.arguments);
	}

	@Override
	public void onEvent(int eventID, AWTEvent event)
	{
		switch (eventID)
		{
			case SELECTOR:
				boolean playerName = this.comboboxSelector.getSelectedValue().equals(Target.SELECTORS[Target.PLAYER_NAME]);
				this.entryName.setVisible(playerName);
				this.comboboxArgument.setVisible(!playerName);
				this.buttonAdd.setVisible(!playerName);
				this.buttonRemove.setVisible(!playerName);
				this.areaArguments.setVisible(!playerName);
				break;

			case ADD:
				if (!TargetArgument.canBeAdded(this.comboboxArgument.getSelectedIndex(), this.arguments))
				{
					GenerationException exception = new GenerationException(new Text("GUI", "error.argument.already_added", false));
					CommandGenerator.log(exception.getMessage());
					Utils.showMessage(new Text("GUI", "error.generation", false), new Text(exception.getMessage()));
					break;
				}
				TargetArgument argument = new TargetArgument(this.comboboxArgument.getSelectedIndex());
				this.arguments.add(argument);
				argument.askValue(this);
				break;

			case REMOVE:
				if (this.arguments.size() == 0) break;
				String[] choices = new String[this.arguments.size()];
				for (int i = 0; i < choices.length; i++)
					choices[i] = this.arguments.get(i).toString();
				CPanelVertical panel = new CPanelVertical();
				panel.add(new CCombobox(choices));
				panel.setName("Remove");
				CommandGenerator.addStateWithConfirm(new Text("GUI", "state.target_argument.remove", false), panel, this);

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		this.setBorder(BorderFactory.createTitledBorder(CommandGenerator.translate(this.title)));
	}

	private void updateTextArea()
	{
		if (this.arguments.size() == 0) this.areaArguments.setText(new Text("GUI", "target.argument.nothing", false).getValue());
		else
		{
			String text = "";
			for (TargetArgument argument : this.arguments)
				text += argument.toString() + "<br />";
			this.areaArguments.setText(text);
		}
	}
}
