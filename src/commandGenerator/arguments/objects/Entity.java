package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class Entity extends ObjectBase
{

	public static final Entity player = new Entity("Player"), entity = new Entity("");

	/** Creates a new Entity.
	 * 
	 * @param id
	 *            - <i>String</i> - The Entity's ID. */
	public Entity(String id)
	{
		super(id, ObjectBase.ENTITY);
	}

	/** Returns this Entity's name. */
	@Override
	public String getName()
	{
		return Lang.get("ENTITIES:" + this.getId());
	}

	/** Returns this Entity's texture. */
	@Override
	public ImageIcon getTexture()
	{

		String path = Resources.folder + "textures/entities/" + this.getId() + ".png";
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
