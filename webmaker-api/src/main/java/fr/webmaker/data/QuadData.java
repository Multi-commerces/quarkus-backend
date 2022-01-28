package fr.webmaker.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple Container Class
 * 
 * @author Julien ILARI
 *
 * @param <A> Data A
 * @param <B> Data B
 */
@Data
@AllArgsConstructor
public class QuadData<A, B, C, D> {

	protected A dataA;

	protected B dataB;
	
	protected C dataC;

	protected D dataD;

}
