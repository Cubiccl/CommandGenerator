package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class EffectType extends ObjectBase
{
	private static Map<Integer, EffectType> effects = new HashMap<Integer, EffectType>();

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
		super(idString, CGConstants.OBJECT_EFFECT);
		this.idNum = idNum;
		effects.put(idNum, this);
	}

	/** Returns this Effect's numerical ID. */
	public int getIdNum()
	{
		return idNum;
	}

	/** Returns this Effect's name. */
	public String getName()
	{
		return Lang.get("EFFECTS:" + getId());
	}

	/** Returns this Effect's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return new ImageIcon(Resources.folder + "textures/effects/" + getId() + ".png");
	}

	/** Returns an Effect from its numerical ID.
	 * 
	 * @param id
	 *            - <i>int</i> - The Effect's ID. */
	public static ObjectBase getEffectFromIdNum(int id)
	{
		return effects.get(id);
	}

}
