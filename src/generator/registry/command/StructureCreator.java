package generator.registry.command;

import generator.CommandGenerator;
import generator.main.GenerationException;
import generator.main.Text;

import java.awt.Component;

import javax.swing.JLabel;

public final class StructureCreator
{

	/** Creates a new Argument.
	 * 
	 * @param data - The input data.<br/>
	 *            <strong>Type[detail,detail,...]</strong>
	 * @return The created Argument. */
	private static Argument createArgument(String element)
	{
		String type = element; // The type of the Argument.
		String[] details = new String[0]; // The details.
		if (element.contains("["))
		{
			type = element.substring(0, element.indexOf('['));
			details = element.substring(element.indexOf('[') + 1, element.indexOf(']')).split(",");
		}
		boolean compulsory = Character.isUpperCase(type.charAt(0)); // True if compulsory
		type = type.toLowerCase();

		switch (type)
		{
			case "label":
				return new LabelArgument(new Text("GUI", details[0]));

			case "static":
				return new StaticArgument(details[0]);

			case "boolean":
				return createBoolean(compulsory, details);

			case "choice":
				return createChoice(compulsory, details);

			case "string":
				return createString(compulsory, details);

			case "float":
				return createFloat(compulsory, details);

			case "integer":
				NumberArgument argumentFloat = createFloat(compulsory, details);
				argumentFloat.setInteger();
				return argumentFloat;

			case "xp":
				return new XPArgument();

			case "coord":
				return new SingleCoordinateArgument(compulsory, new Text("GUI", details[0]));

			case "coordinates":
				return createCoordinates(compulsory, details);

			case "achievement":
				return new AchievementArgument();

			case "enchantment":
				return new EnchantmentArgument();

			case "effect":
				return new EffectArgument();
				
			case "sound":
				return new SoundArgument();

			case "target":
				return createTarget(compulsory, details);

			default:
				CommandGenerator.log("Unknown Argument type : " + type);
				String typeFinal = type;
				return new Argument(true, 1) {

					private JLabel label;

					@Override
					public void createGui()
					{
						this.label = new JLabel(element);
					}

					@Override
					public String generate() throws GenerationException
					{
						return typeFinal;
					}

					@Override
					protected String generateValue() throws GenerationException
					{
						return null;
					}

					@Override
					public Component getComponent()
					{
						return this.label;
					}

					@Override
					public void updateLang()
					{}

					@Override
					protected void verifyValue(String value) throws GenerationException
					{}
				};
		}
	}

	private static BooleanArgument createBoolean(boolean compulsory, String[] details)
	{
		BooleanArgument argument = new BooleanArgument(compulsory, new Text("GUI", details[0]));
		for (int i = 1; i < details.length; i++)
		{
			String detail = details[i];
			if (detail.startsWith("true=")) argument.setTrueValue(detail.substring("true=".length()));
			else if (detail.startsWith("false=")) argument.setFalseValue(detail.substring("false=".length()));
			else CommandGenerator.log("Unknown detail : " + detail);
		}
		return argument;
	}

	private static ChoiceArgument createChoice(boolean compulsory, String[] details)
	{
		boolean hasHelp = details.length > 1 && details[1].equals("help");
		String[] values;
		if (hasHelp) values = new String[details.length - 2];
		else values = new String[details.length - 1];
		for (int i = 0; i < values.length; i++)
		{
			if (hasHelp) values[i] = details[i + 2];
			else values[i] = details[i + 1];
		}
		return new ChoiceArgument(compulsory, hasHelp, details[0], values);
	}

	private static Argument createCoordinates(boolean compulsory, String[] details)
	{
		boolean relative = false;
		for (int i = 1; i < details.length; i++)
		{
			String detail = details[i];
			if (detail.equals("relative")) relative = true;
			else CommandGenerator.log("Unknown detail : " + detail);
		}
		return new CoordinatesArgument(compulsory, new Text("GUI", details[0]), relative);
	}

	private static NumberArgument createFloat(boolean compulsory, String[] details)
	{
		NumberArgument argument = new NumberArgument(compulsory, new Text("GUI", details[0]));
		for (int i = 1; i < details.length; i++)
		{
			String detail = details[i];
			if (detail.startsWith("min=")) argument.setMin(Float.parseFloat(detail.substring("min=".length())));
			else if (detail.startsWith("max=")) argument.setMax(Float.parseFloat(detail.substring("max=".length())));
			else if (detail.equals("info")) argument.addInfo();
			else CommandGenerator.log("Unknown detail : " + detail);
		}
		return argument;
	}

	private static StringArgument createString(boolean compulsory, String[] details)
	{
		StringArgument argument = new StringArgument(compulsory, new Text("GUI", details[0]));
		for (int i = 1; i < details.length; i++)
		{
			String detail = details[i];
			if (detail.equals("space")) argument.setHasSpaces();
			else if (detail.equals("info")) argument.addInfo();
			else CommandGenerator.log("Unknown detail : " + detail);
		}
		return argument;
	}

	/** Creates a new Structure.
	 * 
	 * @param data - The input data.<br/>
	 *            <strong>ID;Argument[details...];Argument;...</strong>
	 * @return The created Structure. */
	public static Structure createStructure(String[] data)
	{
		Structure structure = new Structure(data[0]);
		for (int i = 1; i < data.length; i++)
		{
			structure.addArgument(createArgument(data[i]));
		}
		return structure;
	}

	private static ArgumentTarget createTarget(boolean compulsory, String[] details)
	{
		int type = ArgumentTarget.ALL;
		if (details[1].equals("all")) type = ArgumentTarget.ALL;
		else if (details[1].equals("players")) type = ArgumentTarget.PLAYERS;
		else if (details[1].equals("entity")) type = ArgumentTarget.ENTITIES;

		return new ArgumentTarget(compulsory, new Text("GUI", details[0]), type);
	}

}
