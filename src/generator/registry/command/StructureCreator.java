package generator.registry.command;


public final class StructureCreator
{

	/** Creates a new Structure.
	 * 
	 * @param data - The input data.<br/>
	 *            <strong>ID;Argument[details...];Argument;...</strong> */
	public static Structure createStructure(String[] data)
	{
		Structure structure = new Structure(data[0]);
		for (String element : data)
		{
			structure.addArgument(createArgument(element));
		}
		return structure;
	}

	private static Argument createArgument(String element)
	{
		// TODO createArgument(element)
		return null;
	}

}
