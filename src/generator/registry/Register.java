package generator.registry;

import java.util.Collection;
import java.util.HashMap;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

/** Contains all Objects of the same type.
 * 
 * @param <T> - The type of the Objects. */
public class Register<T extends ObjectBase> implements ITranslate
{
	/** All objects, sorted by ID. */
	private HashMap<String, T> register;

	public Register()
	{
		this.register = new HashMap<String, T>();
	}

	/** Finalizes the Register. Creates language & textures.
	 * 
	 * @param name - The name of the Objects. (used for listing the register.) */
	public void complete(String name)
	{
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

	/** @return The lsit of all Objects. */
	public Collection<T> getList()
	{
		return this.register.values();
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
	}

	@Override
	public void updateLang()
	{
		for (T object : this.register.values())
		{
			object.updateLang();
		}
	}

}
