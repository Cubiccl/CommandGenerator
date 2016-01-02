package generator.gui.spinner;

import generator.gui.button.CArrow;

import java.awt.Component;

import javax.swing.plaf.basic.BasicSpinnerUI;

public class CSpinnerUI extends BasicSpinnerUI
{

	@Override
	protected Component createNextButton()
	{
		CArrow c = new CArrow(CArrow.NORTH);
		c.setHasDecoration(true);
		installNextButtonListeners(c);
		return c;
	}

	@Override
	protected Component createPreviousButton()
	{
		CArrow c = new CArrow(CArrow.SOUTH);
		c.setHasDecoration(true);
		installPreviousButtonListeners(c);
		return c;
	}

}
