package commandGenerator.arguments.objects.command;

import java.util.Map;

import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.main.CGConstants;

public class ObjectIdArgument extends Argument
{

	private byte objectType;

	public ObjectIdArgument(String id, byte objectType)
	{
		super(1, id);
	}

	public ObjectIdArgument(String id, boolean compulsery, byte objectType)
	{
		super(1, id, compulsery);
		this.objectType = objectType;
	}

	@Override
	public boolean matches(String[] elements)
	{
		ObjectBase[] objects = Registerer.getObjectList(objectType);
		for (int i = 0; i < objects.length; i++)
			if (objects[i].getId().equals(elements[0])) return true;

		try
		{
			int id = Integer.parseInt(elements[0]);
			for (int i = 0; i < objects.length; i++)
			{
				if (objectType == CGConstants.OBJECT_ENCHANT) if (((EnchantType) objects[i]).getIdNum() == id) return true;
				if (objectType == CGConstants.OBJECT_EFFECT) if (((EffectType) objects[i]).getIdNum() == id) return true;
			}
		} catch (Exception e)
		{}

		return false;
	}

	@Override
	public void generateData(String[] elements, Map<String, Object> data)
	{
		data.put(getId(), Registerer.getObjectFromId(elements[0]));
		try
		{
			if (data.get(getId()) == null) data.put(getId(), Registerer.getObjectFromIdNum(objectType, Integer.parseInt(elements[0])));
		} catch (Exception e)
		{}
	}

}
