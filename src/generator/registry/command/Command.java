package generator.registry.command;

import generator.main.Utils;
import generator.registry.ObjectBase;

import java.util.ArrayList;

/** A Command. */
public class Command extends ObjectBase
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
		structure.setCommand(this);
	}

	/** Creates each Structure's GUI. */
	public void createGui()
	{
		for (Structure structure : this.structures)
		{
			structure.createGui();
		}
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

	@Override
	public void updateLang()
	{
		super.updateLang();
		for (Structure structure : this.structures)
		{
			structure.updateLang();
		}
	}

}
