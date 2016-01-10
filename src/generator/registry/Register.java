package generator.registry;

import generator.CommandGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/** Contains all Objects of the same type.
 * 
 * @param <T> - The type of the Objects. */
public class Register<T extends ObjectBase>
{
	class ObjectSorter implements Comparator<String>
	{
		private Register<T> register;

		public ObjectSorter(Register<T> register)
		{
			this.register = register;
		}

		@Override
		public int compare(String o1, String o2)
		{
			return this.register.getObjectFromId(o1).getId().compareTo(this.register.getObjectFromId(o2).getId());
		}

	}

	/** IDs, sorted. */
	private ArrayList<String> ids;

	/** All objects */
	private HashMap<String, T> register;

	public Register()
	{
		this.register = new HashMap<String, T>();
		this.ids = new ArrayList<String>();
	}

	/** Finalizes the Register. Creates language & textures.
	 * 
	 * @param name - The name of the Objects. (used for listing the register.) */
	public void complete(String name)
	{
		for (T object : this.getList())
			object.complete();
		this.sort();

		String display = "";
		String current = "Registered " + name + "s : ";

		for (String key : this.register.keySet())
		{
			current += this.register.get(key).getId() + ", ";
			if (current.length() >= 100)
			{
				display += current + "\n";
				current = "";
			}
			this.register.get(key).complete();
		}
		display += current + "\n ";
		CommandGenerator.log(display);
	}

	/** @return The list of all Objects. */
	public ArrayList<T> getList()
	{
		ArrayList<T> list = new ArrayList<T>();
		for (String id : this.ids)
		{
			list.add(this.getObjectFromId(id));
		}
		return list;
	}

	/** @param id - The ID of the target Object.
	 * @return The Object with the given ID. */
	public T getObjectFromId(String id)
	{
		if (!this.register.containsKey(id))
		{
			CommandGenerator.log(id + " is not a valid ID.");
			return null;
		}
		return this.register.get(id);
	}

	/** Adds a new Object.
	 * 
	 * @param object - The input Object. */
	public void register(T object)
	{
		this.register.put(object.getId(), object);
		this.ids.add(object.getId());
	}

	/** Sorts all Objects in this Register. */
	public void sort()
	{
		this.ids.sort(new ObjectSorter(this));
	}

}
