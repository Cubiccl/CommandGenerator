package commandGenerator.arguments.tags;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;

public abstract class Tag extends ObjectBase
{

	public static final byte BOOLEAN = 0, STRING = 1, INT = 2, FLOAT = 3, COMPOUND = 4, LIST = 5;
	public static final int NONE = 0, MIN = 1, MAX = 2, BOTH = 3;

	private ObjectBase[] applicable;
	protected GridBagConstraints gbc;
	private boolean isEmpty;
	protected JPanel panel;
	private byte type;

	/** The Base Tag.
	 * 
	 * @param id
	 *            - <i>String</i> - The Tag's ID.
	 * @param type
	 *            - <i>byte</i> - The type of this Tag.
	 * @param ids
	 *            - <i>String[]</i> - The list of Object IDs this Tag can be applied to. */
	public Tag(String id, byte type, String... ids)
	{
		super(id, ObjectBase.TAG);
		List<ObjectBase> objects = new ArrayList<ObjectBase>();
		if (ids.length == 1)
		{
			for (String objectId : ids[0].split(":"))
			{
				if (!objectId.startsWith("LIST=")) objects.add(Generator.registry.getObjectFromId(objectId));
				else
				{
					objectId = objectId.substring("LIST=".length());
					for (ObjectBase object : Generator.registry.getList(objectId))
						objects.add(object);

				}
			}
		}
		applicable = objects.toArray(new ObjectBase[0]);
		panel = new JPanel(new GridBagLayout());
		isEmpty = true;
		this.type = type;

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
	}

	/** Called when the user adds this tag. Asks the user for all parameters needed. Returns true if the user canceled */
	public abstract void askValue();

	/** Generates the NBT structure for the command. */
	public abstract String commandStructure();

	/** Displays the Tag to the user.
	 * 
	 * @param details */
	public abstract String display(int details, int lvls);

	public ObjectBase[] getApplicable()
	{
		return applicable;
	}

	public String getDescription()
	{
		if (getId().equals("")) return "";
		return Generator.translate("TAGS:" + getId() + ".description");
	}

	@Override
	public String getId()
	{
		String id = super.getId();
		if (id.startsWith("block.")) return id.substring("block.".length());
		if (id.startsWith("entity.")) return id.substring("entity.".length());
		if (id.startsWith("item.")) return id.substring("item.".length());
		return id;
	}

	@Override
	public String getName()
	{
		if (getId().equals("")) return "";
		return Generator.translate("TAGS:" + getId());
	}

	public byte getTagType()
	{
		return type;
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

	/** Is this tag empty? */
	public boolean isEmpty()
	{
		return isEmpty;
	}

	/** Displays the Tag panel. Returns true if the user canceled. */
	protected boolean showPanel()
	{
		isEmpty = true;

		panel.addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e)
			{
				Window window = SwingUtilities.getWindowAncestor(panel);
				if (window instanceof Dialog)
				{
					Dialog dialog = (Dialog) window;
					if (!dialog.isResizable())
					{
						dialog.setResizable(true);
					}
				}
			}
		});
		boolean cancel = JOptionPane.showConfirmDialog(null, panel, Generator.translate("GUI:tag.add") + " : " + getName(), JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION;
		if (!cancel) isEmpty = false;
		return cancel;
	}

	@Override
	public String toString()
	{
		return display(true);
	}

	@Override
	public void updateLang()
	{}
}
