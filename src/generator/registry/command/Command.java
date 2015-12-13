package generator.registry.command;

import java.util.ArrayList;

import generator.main.Utils;
import generator.registry.ObjectDescribed;

/** A Command. */
public class Command extends ObjectDescribed
{
	/** Contains all Structures that belong to this Command. */
	private ArrayList<Structure> structures;

	public Command(String id)
	{
		super(Utils.COMMAND, id);
		this.structures = new ArrayList<Structure>();
	}

	/** Adds a new Structure.
	 * 
	 * @param structure - The new Structure. */
	public void addStructure(Structure structure)
	{
		this.structures.add(structure);
	}

	@Override
	public String getDescription()
	{
		return super.getName();
	}

	@Override
	public String getName()
	{
		return this.getId();
	}

	/** @return All the Structures for this Command. */
	public Structure[] getStructures()
	{
		return this.structures.toArray(new Structure[0]);
	}

}
