package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

public class Achievement extends ObjectBase
{
	private static Map<String, Achievement> list = new HashMap<String, Achievement>();
	private static List<String> ids = new ArrayList<String>();

	private Item itemTexture;

	/** Creates a new Achievement
	 * 
	 * @param id
	 *            - The achievement ID
	 * @param itemTexture
	 *            - The item which will give its texture to the achievement */
	public Achievement(String id, Item itemTexture)
	{
		super(id, CGConstants.OBJECT_ACHIEVEMENT);
		this.itemTexture = itemTexture;
		list.put(id, this);
		ids.add(id);
	}

	public String commandStructure()
	{
		return "achievement." + getId();
	}

	public String getName()
	{
		return Lang.get("ACHIEVEMENTS:" + getId());
	}

	public ImageIcon getTexture()
	{
		return itemTexture.getTexture(0);
	}

	public static Achievement getAchievementFromId(String id)
	{
		return list.get(id);
	}

	public static Achievement[] getList()
	{
		Achievement[] achs = new Achievement[list.size()];
		for (int i = 0; i < achs.length; i++)
		{
			achs[i] = list.get(ids.get(i));
		}
		return achs;
	}

	public static Achievement generateFrom(String data)
	{
		if (data.contains("achievement.")) data = data.substring("achievement.".length());

		Achievement ach = (Achievement) ObjectBase.getObjectFromId(data);
		if (ach == null) DisplayHelper.log(data + " is not a correct achievement.");
		else DisplayHelper.log("Created achievement " + ach.getId());
		return ach;
	}

}
