package generator.registry.instance;

import generator.CommandGenerator;
import generator.gui.CLabel;
import generator.gui.combobox.CChoiceCombobox;
import generator.gui.panel.CPanel;
import generator.gui.panel.CPanelVertical;
import generator.gui.spinner.CSpinner;
import generator.gui.textfield.CEntry;
import generator.gui.textfield.CTextfield;
import generator.interfaces.IConfirmState;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;

import java.util.ArrayList;

public class TargetArgument
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

	/** @param argument - The type of the argument to add
	 * @param previousArguments - The list of all already added arguments.
	 * @return True if the Argument can be added. */
	public static boolean canBeAdded(int argument, ArrayList<TargetArgument> previousArguments)
	{
		if (argument == TEAM || argument == SCORE || argument == SCORE_MIN) return true;

		for (TargetArgument targetArgument : previousArguments)
			if (argument == targetArgument.type) return false;

		return true;
	}

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
	}

	/** Asks the user the value of this Argument.
	 * 
	 * @param listener - The listener to call when the user is done. */
	public void askValue(IConfirmState listener)
	{
		CommandGenerator.addStateWithConfirm(new Text("GUI", "state.target_argument"), this.createGui(), listener);
	}

	private CPanel createGui()
	{
		CPanel panel = new CPanelVertical();
		panel.add(new CLabel(new Text("HELP", "target.argument." + ARGUMENT_IDS[this.type])));

		switch (this.type)
		{
			case X:
			case Y:
			case Z:
			case DX:
			case DY:
			case DZ:
			case R:
			case RM:
			case C:
			case L:
			case LM:
			case NAME:
			case TEAM:
				panel.add(new CTextfield());
				break;

			case RX:
			case RXM:
				panel.add(new CSpinner(-90, 90));
				break;

			case RY:
			case RYM:
				panel.add(new CSpinner(-180, 179));
				break;

			case M:
				panel.add(new CChoiceCombobox("gamemode", new String[] { "survival", "creative", "adventure", "spectator" }));
				break;

			case TYPE:
				// TODO TargetArgument : PanelEntity
				panel.add(new CLabel(new Text("Add PanelEntity")));
				break;

			case SCORE:
			case SCORE_MIN:
				panel.add(new CEntry(new Text("GUI", "scoreboard.name")));
				panel.add(new CEntry(new Text("GUI", "scoreboard.score")));
				break;

			default:
				break;
		}
		return panel;
	}

	/** @param component - The component shown to input the values.
	 * @return True if the value was accepted, false else. */
	public boolean createValue(CPanel component)
	{

		String newValue = this.getValue(component);
		try
		{
			this.verifyValue(newValue);
		} catch (GenerationException e)
		{
			Utils.showMessage(new Text("GUI", "error.generation", false), new Text(e.getMessage()));
			CommandGenerator.log(e.getMessage());
			return false;
		}

		this.value = newValue;
		return true;
	}

	public String getValue()
	{
		return this.value;
	}

	/** @param component - The GUI.
	 * @return The value input by the user. */
	private String getValue(CPanel component)
	{
		switch (this.type)
		{
			case X:
			case Y:
			case Z:
			case DX:
			case DY:
			case DZ:
			case R:
			case RM:
			case C:
			case L:
			case LM:
			case NAME:
			case TEAM:
				return ((CTextfield) component.getComponent(1)).getText();

			case RX:
			case RXM:
			case RY:
			case RYM:
				return Integer.toString((int) ((CSpinner) component.getComponent(1)).getValue());

			case M:
				return Integer.toString(((CChoiceCombobox) component.getComponent(1)).getSelectedIndex());

			case TYPE:
				// TODO TargetArgument : getValueFromPanelEntity
				break;

			case SCORE:
			case SCORE_MIN:
				return ((CEntry) component.getComponent(1)).getText() + " " + ((CEntry) component.getComponent(2)).getText();

			default:
				break;
		}
		return null;
	}

	/** @return The String representation of this Object instance to use in a Command. */
	public String toCommand()
	{
		int separation = this.value.lastIndexOf(" ") + 1;
		if (this.type == SCORE) return "score_" + this.value.substring(0, separation) + "=" + this.value.substring(separation + 1);
		if (this.type == SCORE_MIN) return "score_" + this.value.substring(0, separation) + "_min=" + this.value.substring(separation + 1);
		return ARGUMENT_IDS[this.type] + "=" + this.value;
	}

	@Override
	public String toString()
	{
		return this.toCommand().replaceAll("=", " = ");
	}

	/** Verifies the input value.
	 * 
	 * @param newValue - The input value.
	 * @throws GenerationException If the value is wrong. */
	private void verifyValue(String newValue) throws GenerationException
	{
		// TODO TargetArgument.verifyValue(String)

		switch (this.type)
		{
			case X:
			case Y:
			case Z:
			case DX:
			case DY:
			case DZ:
			case R:
			case RM:
			case C:
			case M:
			case L:
			case LM:
				try
				{
					int i = Integer.parseInt(newValue);
					if ((this.type == DX || this.type == DY || this.type == DZ || this.type == R || this.type == RM || this.type == C || this.type == LM || this.type == L)
							&& i < 0) throw new GenerationException(new Text("GUI", "error.number.min", false).addReplacement("<value>", newValue)
							.addReplacement("<min>", "0"));
					if (this.type == M && (i < 0 || i > 3)) throw new GenerationException(new Text("GUI", "error.number.bounds", false)
							.addReplacement("<value>", newValue).addReplacement("<min>", "0").addReplacement("<max>", "3"));
				} catch (NumberFormatException e)
				{
					throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>", newValue));
				}
				break;

			case NAME:
			case TEAM:
				if (newValue.contains(" ")) throw new GenerationException(new Text("GUI", "error.space").addReplacement("<value>", newValue));
				break;

			case RX:
			case RXM:
			case RY:
			case RYM:
				try
				{
					float f = Float.parseFloat(newValue);
					if ((this.type == RX || this.type == RXM) && (f < -90 || f > 90)) throw new GenerationException(new Text("GUI", "error.number.bounds",
							false).addReplacement("<value>", newValue).addReplacement("<min>", "-90").addReplacement("<max>", "90"));
					if ((this.type == RY || this.type == RYM) && (f < -180 || f > 180)) throw new GenerationException(new Text("GUI", "error.number.bounds",
							false).addReplacement("<value>", newValue).addReplacement("<min>", "-180").addReplacement("<max>", "180"));
				} catch (NumberFormatException e)
				{
					throw new GenerationException(new Text("GUI", "error.number", false).addReplacement("<value>", newValue));
				}
				break;

			case SCORE:
			case SCORE_MIN:
				if (Utils.countOccurrences(newValue, " ") > 1) throw new GenerationException(new Text("GUI", "error.space", false).addReplacement("<value>",
						newValue.substring(0, newValue.lastIndexOf(" "))));
				try
				{
					Integer.parseInt(newValue.substring(newValue.lastIndexOf(" ") + 1));
				} catch (Exception e)
				{
					throw new GenerationException(new Text("GUI", "error.integer", false).addReplacement("<value>",
							newValue.substring(newValue.lastIndexOf(" ") + 1)));
				}
				break;

			default:
				break;
		}
	}
}
