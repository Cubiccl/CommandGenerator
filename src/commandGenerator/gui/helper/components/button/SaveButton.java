package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;

import javax.swing.JLabel;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IEvent;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class SaveButton extends CButton implements IEvent
{

	private ISave parent;
	private byte type;

	public SaveButton(byte type, ISave parent)
	{
		super("GENERAL:save");
		this.type = type;
		this.parent = parent;
		this.addActionListener(new ClickListener(this));
	}

	@Override
	public void handleEvent(AWTEvent e, int eventID)
	{
		if (eventID != IEvent.CLICK_EVENT) return;
		Object object = this.parent.getObjectToSave();
		String name = DisplayHelper.askObjectName(this.type);
		if (name == null) return;
		SavedObjects.add(name, this.type, object);
		DisplayHelper.showMessage(new JLabel(Generator.translate("GENERAL:save_success").replaceAll("<item>", name)), Generator.translate("GENERAL:save"));
	}

}
