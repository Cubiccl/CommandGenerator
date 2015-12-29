package generator.registry.command;

import generator.CommandGenerator;
import generator.main.GenerationException;

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
				return new LabelArgument(details[0]);

			case "static":
				return new StaticArgument(details[0]);

			case "boolean":
				BooleanArgument argumentBoolean = new BooleanArgument(compulsory, details[0]);
				for (int i = 1; i < details.length; i++)
				{
					String detail = details[i];
					if (detail.startsWith("true=")) argumentBoolean.setTrueValue(detail.substring("true=".length()));
					else if (detail.startsWith("false=")) argumentBoolean.setFalseValue(detail.substring("false=".length()));
					else CommandGenerator.log("Unknown detail : " + detail);
				}
				return argumentBoolean;

			case "string":
				StringArgument argumentString = new StringArgument(compulsory, details[0]);
				for (int i = 1; i < details.length; i++)
				{
					String detail = details[i];
					if (detail.equals("space")) argumentString.setHasSpaces();
					else CommandGenerator.log("Unknown detail : " + detail);
				}
				return argumentString;

			case "choice":
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

			default:
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
					public Component getComponent()
					{
						return this.label;
					}

					@Override
					public void updateLang()
					{}
				};
		}
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

}
