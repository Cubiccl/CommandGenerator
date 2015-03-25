package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

public class Sound extends ObjectBase
{

	/** Creates a new Sound.
	 * 
	 * @param id
	 *            - <i>String</i> - The Sound's ID. */
	public Sound(String id)
	{
		super(id, ObjectBase.SOUND);
	}

	@Override
	public String getName()
	{
		return this.getId();
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
