package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Entity extends ObjectBase
{

	public static Entity player = new Entity("Player"), entity = new Entity("");

	/** Returns an array containing all registered Entities, except Player. */
	public static Entity[] getListNoPlayer()
	{
		List<Entity> entityList = new ArrayList<Entity>();
		ObjectBase[] list = Registerer.getObjectList(Constants.OBJECT_ENTITY);
		for (int i = 0; i < list.length; i++)
			if (!list[i].getId().equals("Player")) entityList.add((Entity) list[i]);

		return entityList.toArray(new Entity[0]);
	}

	/** Creates a new Entity.
	 * 
	 * @param id
	 *            - <i>String</i> - The Entity's ID. */
	public Entity(String id)
	{
		super(id, Constants.OBJECT_ENTITY);
	}

	/** Returns this Entity's name. */
	public String getName()
	{
		return Lang.get("ENTITIES:" + getId());
	}

	/** Returns this Entity's texture. */
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

}
