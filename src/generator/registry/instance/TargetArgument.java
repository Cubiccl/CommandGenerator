package generator.registry.instance;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.panel.CPanel;
import generator.gui.panel.CPanelHorizontal;
import generator.interfaces.IConfirmState;
import generator.main.GenerationException;
import generator.main.Text;

public class TargetArgument implements IConfirmState
{
	/** The IDs of the Arguments. */
	public static final String[] ARGUMENT_IDS = { "x", "y", "z", "dx", "dy", "dz", "r", "rm", "rx", "rxm", "ry", "rym", "m", "c", "l", "lm", "name", "type",
			"score", "score_min", "team" };
	/** The names of the Arguments. */
	public static Text[] NAMES = new Text[ARGUMENT_IDS.length];
	/** The different Target Argument types.
	 * <ul>
	 * <li>X = 0;</li>
	 * <li>Y = 1;</li>
	 * <li>Z = 2;</li>
	 * <li>DX = 3;</li>
	 * <li>DY = 4;</li>
	 * <li>DZ = 5;</li>
	 * <li>R = 6;</li>
	 * <li>RM = 7;</li>
	 * <li>RX = 8;</li>
	 * <li>RXM = 9;</li>
	 * <li>RY = 10;</li>
	 * <li>RYM = 11;</li>
	 * <li>M = 12;</li>
	 * <li>C = 13;</li>
	 * <li>L = 14;</li>
	 * <li>LM = 15;</li>
	 * <li>NAME = 16;</li>
	 * <li>TYPE = 17;</li>
	 * <li>SCORE = 18;</li>
	 * <li>SCORE_MIN = 19;</li>
	 * <li>TEAM = 20;</li>
	 * </ul> */
	public static final int X = 0, Y = 1, Z = 2, DX = 3, DY = 4, DZ = 5, R = 6, RM = 7, RX = 8, RXM = 9, RY = 10, RYM = 11, M = 12, C = 13, L = 14, LM = 15,
			NAME = 16, TYPE = 17, SCORE = 18, SCORE_MIN = 19, TEAM = 20;

	/** @param type - The type of the Argument.
	 * @return The name of this Argument */
	public static Text getName(int type)
	{
		if (NAMES == null) for (int i = 0; i < ARGUMENT_IDS.length; i++)
		{
			NAMES[i] = new Text("CHOICE", "target." + ARGUMENT_IDS[type]);
		}
		return NAMES[type];
	}

	/** The type of the Selector. */
	private int type;

	/** Its value. */
	private String value;

	/** Creates a new Target Argument. Asks the user for its value.
	 * 
	 * @param type - Its type. */
	public TargetArgument(int type)
	{
		this.type = type;
		this.askValue();
	}

	/** Asks the user the value of this Argument. */
	public void askValue()
	{
		CommandGenerator.addStateWithConfirm(new Text("GUI", "state.target_argument"), this.createGui(), this);
	}

	@Override
	public boolean confirm(boolean cancel, CPanel component)
	{
		if (cancel) return false;

		String newValue = this.getValue(component);
		try
		{
			this.verifyValue(newValue);
		} catch (GenerationException e)
		{
			CommandGenerator.log(e);
			return true;
		}

		this.value = newValue;
		return false;
	}

	private CPanel createGui()
	{
		// TODO Auto-generated method stub
		CPanel panel = new CPanelHorizontal();
		panel.add(new CLabel(new Text("CHOICE", "target." + ARGUMENT_IDS[this.type])));
		return panel;
	}

	public String getValue()
	{
		return this.value;
	}

	/** @param component - The GUI.
	 * @return The value input by the user. */
	private String getValue(CPanel component)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/** @return The String representation of this Object instance to use in a Command. */
	public String toCommand()
	{
		return this.type + "=" + this.value;
	}

	/** Verifies the input value.
	 * 
	 * @param newValue - The input value.
	 * @throws GenerationException If the value is wrong. */
	private void verifyValue(String newValue) throws GenerationException
	{
		// TODO Auto-generated method stub

	}

}
