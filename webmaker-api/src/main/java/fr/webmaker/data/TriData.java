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
 * @param <C> Data C
 */
@Data
@AllArgsConstructor
public class TriData<A, B, C> {

	protected A dataA;

	protected B dataB;
	
	protected C dataC;

}
