package generator.gui.spinner;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

@SuppressWarnings("serial")
public class CSpinner extends JSpinner
{

	/** @param min - The minimum value
	 * @param max - The maximum value
	 * @return An array containing all values between min and max included. */
	private static int[] createValues(int min, int max)
	{
		int[] array = new int[max - min + 1];
		for (int i = 0; i < array.length; i++)
		{
			array[i] = min + i;
		}
		return array;
	}

	/** Creates a new Spinner.
	 * 
	 * @param min - The minimum value.
	 * @param max - The maximum value. */
	public CSpinner(int min, int max)
	{
		this(createValues(min, max));
	}

	/** Creates a new Spinner.
	 * 
	 * @param values - An array containing the values. */
	public CSpinner(int[] values)
	{
		super(new SpinnerListModel());
		this.setValues(values);
		this.setUI(new CSpinnerUI());
		this.setMinimumSize(new Dimension(50, 20));
		this.setPreferredSize(new Dimension(50, 20));
	}

	/** Changes the bounds of this Spinner.
	 * 
	 * @param min - The minimum value.
	 * @param max - The maximum value. */
	public void setBounds(int min, int max)
	{
		this.setValues(createValues(min, max));
	}

	/** Changes the values of this Spinner.
	 * 
	 * @param values - The input values. */
	public void setValues(int[] values)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i : values)
		{
			list.add(i);
		}
		this.setModel(new SpinnerListModel(list));
	}

}
