package generator.registry.command;

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
		String type = element;
		String[] details = new String[0];
		if (element.contains("["))
		{
			type = element.substring(0, element.indexOf('['));
			details = element.substring(element.indexOf('[') + 1, element.indexOf(']')).split(",");
		}

		switch (type)
		{

			default:
				String typeFinal = type;
				return new Argument(Argument.COMPOUND, true, 1) {

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
