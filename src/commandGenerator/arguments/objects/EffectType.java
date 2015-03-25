package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class EffectType extends ObjectBase
{

	/** This Effect's numerical ID. */
	private int idNum;

	/** New Effect type.
	 * 
	 * @param idNum
	 *            - <i>int</i> - The Effect's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Effect's text ID. */
	public EffectType(int idNum, String idString)
	{
		super(idString, ObjectBase.EFFECT);
		this.idNum = idNum;
		Registry.registerEffect(this);
	}

	/** Returns this Effect's numerical ID. */
	public int getIdNum()
	{
		return this.idNum;
	}

	/** Returns this Effect's name. */
	@Override
	public String getName()
	{
		return Lang.get("EFFECTS:" + this.getId());
	}

	/** Returns this Effect's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return new ImageIcon(Resources.folder + "textures/effects/" + this.getId() + ".png");
	}

}
