package commandGenerator.gui.helper.components.panel;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.command.Structure;
import commandGenerator.gui.helper.components.CLabel;

@SuppressWarnings("serial")
public class StructurePanel extends HelperPanel
{
	private Structure structure;
	private CLabel label;

	public StructurePanel(Structure structure)
	{
		super("GENERAL:options");
		this.structure = structure;

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		this.add(this.label);
		for (Argument arg : this.structure.getArguments())
		{
			Component c = arg.getComponent();
			if (c != null) this.add(c);
		}
	}

	@Override
	protected void createComponents()
	{
		this.label = new CLabel("HELP:structure." + this.structure.getId(), true);
		this.label.setBorder(BorderFactory.createBevelBorder(0));
		this.label.setFont(new Font(this.label.getFont().getName(), Font.BOLD, 13));
	}

	@Override
	protected void createListeners()
	{}

}
