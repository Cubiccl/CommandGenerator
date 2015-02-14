package commandGenerator.arguments.objects.command;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.main.CGConstants;

public enum Structure
{

	achievementOne(new StringArgument("give", "take"), new ObjectIdArgument(CGConstants.PANELID_ACHIEVEMENT, CGConstants.OBJECT_ACHIEVEMENT),
			new TargetArgument(CGConstants.PANELID_TARGET, false)),
	achievementAll(new StringArgument("give", "take"), new StringArgument("*"), new TargetArgument(CGConstants.PANELID_TARGET, false)),
	blockdata(new CoordinatesArgument(CGConstants.PANELID_COORDS), new DataTagArgument(CGConstants.PANELID_NBT)),
	clear(new TargetArgument(CGConstants.PANELID_TARGET), new ObjectIdArgument(CGConstants.PANELID_ITEM, false, CGConstants.OBJECT_ITEM));

	private Argument[] arguments;

	private Structure(Argument... arguments)
	{
		this.arguments = arguments;
	}

	public boolean matches(String[] command)
	{
		int index = 0;
		
		for (int i = 0; i < arguments.length; i++)
		{
			if (!arguments[i].isCompulsery()) break;

			try
			{
				String[] elements = new String[arguments[i].getLength()];
				for (int j = 0; j < elements.length; j++)
					elements[j] = command[index + j];
				if (!arguments[i].matches(elements)) return false;
			} catch (Exception e)
			{
				return false;
			}

		}

		return true;
	}

	public Map<String, Object> generateData(String[] command)
	{
		Map<String, Object> data = new HashMap<String, Object>();

		int index = 0;

		for (int i = 0; i < arguments.length && index < command.length; i++)
		{
			String[] elements = new String[arguments[i].getLength()];
			for (int j = 0; j < elements.length; j++)
				elements[j] = command[index + j];
			arguments[i].generateData(elements, data);
		}

		return data;
	}

}
