package generator.registry;

import java.util.HashMap;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

public class Register<T extends ObjectBase> implements ITranslate
{
	private HashMap<String, T> register;

	public Register()
	{
		this.register = new HashMap<String, T>();
	}

	public void complete(String name)
	{
		String display = "Registered " + name + "s : ";
		String current = display;

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
		display += current;
		CommandGenerator.log(display);
	}

	public T getObjectFromId(String id)
	{
		if (!this.register.containsKey(id))
		{
			CommandGenerator.log(id + " is not a valid ID.");
			return null;
		}
		return this.register.get(id);
	}

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
