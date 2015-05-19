package commandGenerator.arguments.objects;

import java.io.File;

import javax.swing.ImageIcon;

import commandGenerator.Generator;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

public class Entity extends ObjectBase
{

	public static final Entity PLAYER = new Entity("Player"), ENTITY = new Entity("");

	private ImageIcon texture;
	private String name;

	/** Creates a new Entity.
	 * 
	 * @param id
	 *            - <i>String</i> - The Entity's ID. */
	public Entity(String id)
	{
		super(id, ObjectBase.ENTITY);
		this.name = id;
	}

	/** Returns this Entity's name. */
	@Override
	public String getName()
	{
		return this.name;
	}

	/** Returns this Entity's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return this.texture;
	}

	public void registerTexture()
	{
		String path = Resources.folder + "textures/entities/" + this.getId() + ".png";
		this.texture = new ImageIcon(path);
		if (!new File(path).exists()) DisplayHelper.missingTexture(path);
	}

	@Override
	public void updateLang()
	{
		this.name = Generator.translate("ENTITIES:" + this.getId());
	}
}
