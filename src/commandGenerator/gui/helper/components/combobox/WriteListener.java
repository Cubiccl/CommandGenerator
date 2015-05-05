package commandGenerator.gui.helper.components.combobox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WriteListener implements KeyListener
{
	private SearchComboBox box;

	public WriteListener(SearchComboBox box)
	{
		this.box = box;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{}

	@Override
	public void keyReleased(KeyEvent e)
	{
		this.box.write(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e)
	{}

}
