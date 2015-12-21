package generator.registry.command;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import generator.gui.CTextArea;
import generator.gui.panel.CPanelVertical;
import generator.main.GenerationException;
import generator.main.Utils;
import generator.registry.ObjectDescribed;

/** A single way to use a Command. */
public class Structure extends ObjectDescribed
{
	private ArrayList<Argument> arguments;
	private Command command;
	private CPanelVertical component;
	private CTextArea textAreaDescription;

	public Structure(String id)
	{
		super(Utils.STRUCTURE, id);
		this.arguments = new ArrayList<Argument>();
	}

	/** Adds a new Argument to this Structure.
	 * 
	 * @param argument - The input argument. */
	public void addArgument(Argument argument)
	{
		if (argument != null && !this.arguments.contains(argument)) this.arguments.add(argument);
	}

	/** Creates the GUI for this Structure. */
	public void createGui()
	{
		this.component = new CPanelVertical();
		this.textAreaDescription = new CTextArea(this.getDescription());
		this.textAreaDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		this.component.add(this.textAreaDescription);
		for (Argument argument : this.arguments)
		{
			argument.createGui();
			if (argument.getComponent() != null) this.component.add(argument.getComponent());
		}
	}

	/** @return The generated command. */
	public String generate() throws GenerationException
	{
		String command = "/" + this.command.getId();
		for (Argument argument : this.arguments)
		{
			if (argument.isUsed() && argument.generate() != null) command += " " + argument.generate();
		}
		return command;
	}

	/** @return The Component used to select the structure details. */
	public CPanelVertical getComponent()
	{
		return this.component;
	}

	/** Changes the Command this Structure belongs to. Called when added to a Command.
	 * 
	 * @param command - The input Command. */
	protected void setCommand(Command command)
	{
		this.command = command;
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		if (this.component != null) this.component.updateLang();
		if (this.textAreaDescription != null) this.textAreaDescription.setText(this.getDescription());
		for (Argument argument : this.arguments)
		{
			argument.updateLang();
		}
	}

}
