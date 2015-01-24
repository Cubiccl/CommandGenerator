package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Entity extends ObjectBase
{

	private static Map<String, Entity> list = new HashMap<String, Entity>();
	public static List<String> ids = new ArrayList<String>();
	public static List<Entity> entities = new ArrayList<Entity>();
	public static Entity player = new Entity("Player"), entity = new Entity("");

	public Entity(String id)
	{
		super(id, CGConstants.OBJECT_ENTITY);
		list.put(id, this);
		ids.add(id);
		if (!id.equals("Player") && !id.equals("")) entities.add(this);
	}

	public String getName()
	{
		return Lang.get("ENTITIES:" + getId());
	}

	public ImageIcon getTexture()
	{

		String path = Resources.folder + "textures/entities/" + getId() + ".png";
		try
		{
			return new ImageIcon(path);
		} catch (Exception ex)
		{
			DisplayHelper.missingTexture(path);
			return null;
		}
	}

	public static Entity getEntityFromId(String id)
	{
		return list.get(id);
	}

	public static Entity[] getList()
	{
		Entity[] entityList = new Entity[list.size()];
		for (int i = 0; i < entityList.length; i++)
			entityList[i] = list.get(ids.get(i));
		return entityList;
	}

	public static Entity[] getListNoPlayer()
	{
		List<Entity> entityList = new ArrayList<Entity>();
		for (int i = 0; i < ids.size(); i++)
			if (ids.get(i) != "Player") entityList.add(list.get(ids.get(i)));
		return entityList.toArray(new Entity[0]);
	}

}
