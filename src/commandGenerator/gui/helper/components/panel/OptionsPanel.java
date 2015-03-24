package commandGenerator.gui.helper.components.panel;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import commandGenerator.arguments.command.Structure;
import commandGenerator.gui.helper.components.CComponent;

@SuppressWarnings("serial")
public class OptionsPanel extends HelperPanel implements CComponent
{// TODO Delete this

	/** The default width for an Option Panel. */
	private static final int WIDTH = 950;

	private JTabbedPane tabbedpane;

	/** Creates a new Options Panel. */
	public OptionsPanel(Structure... structures)
	{
		super("GENERAL:options");
		tabbedpane = new JTabbedPane();

		for (Structure structure : structures)
			tabbedpane.add(structure.getName(), structure.generatePanel());

		add(tabbedpane);
		tabbedpane.setPreferredSize(new Dimension(WIDTH, 400));
		tabbedpane.setMinimumSize(new Dimension(WIDTH, 400));

	}

	@Override
	protected void addComponents()
	{}

	@Override
	protected void createComponents()
	{}

	@Override
	protected void createListeners()
	{}

	/** Generates the command corresponding to the arguments selected by the user. */
	public String generateCommand()
	{
		return null;
	}

	@Override
	protected void setupSize()
	{
		super.setupSize();
		setPreferredSize(new Dimension(WIDTH, getPreferredSize().height));
	}
}
