package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

public class ObjectLists {

	private static Map<String, ObjectBase[]> list = new HashMap<String, ObjectBase[]>();
	
	public static void add(String id, String[] listNew) {
		ObjectBase[] objects = new ObjectBase[listNew.length];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectBase.getObjectFromId(listNew[i]);
		}
		list.put(id, objects);
	}

	public static ObjectBase[] get(String listName) {
		if (list.get(listName) == null) return new ObjectBase[0];
		return list.get(listName);
	}
	
}
