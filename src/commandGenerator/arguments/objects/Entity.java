package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Entity extends ObjectBase
{

	public static Entity player = new Entity("Player"), entity = new Entity("");

	/** Creates a new Entity.
	 * 
	 * @param id
	 *            - <i>String</i> - The Entity's ID. */
	public Entity(String id)
	{
		super(id, CGConstants.OBJECT_ENTITY);
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

	/** Returns an array containing all registered Entities, except Player. */
	public static Entity[] getListNoPlayer()
	{
		List<Entity> entityList = new ArrayList<Entity>();
		ObjectBase[] list = Registerer.getObjectList(CGConstants.OBJECT_ENTITY);
		for (int i = 0; i < list.length; i++)
			if (!list[i].getId().equals("Player")) entityList.add((Entity) list[i]);

		return entityList.toArray(new Entity[0]);
	}

}
