package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class EnchantType extends ObjectBase
{

	private static Map<Integer, EnchantType> enchants = new HashMap<Integer, EnchantType>();

	/** Returns the Enchantment type for the specified ID.
	 * 
	 * @param id
	 *            - <i>int</i> - The Enchantment's ID. */
	public static ObjectBase getEnchantFromIdNum(int id)
	{
		return enchants.get(id);
	}
	/** This Enchantment's numercial ID. */
	private int idNum;

	/** This Enchantment's maximum level. */
	private int max;

	/** Creates a new Enchantment type.
	 * 
	 * @param idNum
	 *            - <i>int</i> - The Enchantment's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Enchantment's ID.
	 * @param max
	 *            - <i>int</i> - The Enchantment's max level. */
	public EnchantType(int idNum, String idString, int max)
	{
		super(idString, CGConstants.OBJECT_ENCHANT);
		this.idNum = idNum;
		this.max = max;
		enchants.put(idNum, this);
	}

	/** Returns this Enchantment's numerical ID. */
	public int getIdNum()
	{
		return idNum;
	}

	/** Returns this Enchantment's maximum level. */
	public int getMax()
	{
		return max;
	}

	/** Returns this Enchantment's name. */
	@Override
	public String getName()
	{
		return Lang.get("ENCHANTS:" + getId());
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
