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

	public void complete()
	{
		for (String key : this.register.keySet())
		{
			this.register.get(key).complete();
		}
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
