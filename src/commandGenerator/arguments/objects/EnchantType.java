package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class EnchantType extends ObjectBase {

	private static Map<String, EnchantType> list = new HashMap<String, EnchantType>();
	private static List<String> ids = new ArrayList<String>();

	private int idNum, max;

	public EnchantType(int idNum, String idString, int max) {
		super(idString, CGConstants.OBJECT_ENCHANT);
		this.idNum = idNum;
		this.max = max;
		list.put(idString, this);
		ids.add(idString);
	}

	public int getMax() {
		return max;
	}

	public int getIdNum() {
		return idNum;
	}

	public String getName() {
		return Lang.get("ENCHANTS:" + getId());
	}

	public static EnchantType getEnchantFromId(String id) {
		return list.get(id);
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

	public static EnchantType[] getList()
	{
		EnchantType[] enchants = new EnchantType[list.size()];
		for (int i = 0; i < list.size(); i++) enchants[i] = list.get(ids.get(i));
		return enchants;
	}

	public static ObjectBase getEnchantFromIdNum(int id)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			if (list.get(ids.get(i)).getIdNum() == id) return getEnchantFromId(ids.get(i));
		}
		return null;
	}

}
