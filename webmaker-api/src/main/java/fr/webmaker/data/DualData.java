package fr.webmaker.data;

import lombok.AllArgsConstructor;

/**
 * Simple Container Class
 * 
 * @author Julien ILARI
 *
 * @param <A> Data A
 * @param <B> Data B
 */
@AllArgsConstructor
public class DualData<A, B>
{

	protected A dataA;

	protected B dataB;

	public A getDataA()
	{
		return dataA;
	}

	public void setDataA(A dataA)
	{
		this.dataA = dataA;
	}

	public B getDataB()
	{
		return dataB;
	}

	public void setDataB(B dataB)
	{
		this.dataB = dataB;
	}

}
