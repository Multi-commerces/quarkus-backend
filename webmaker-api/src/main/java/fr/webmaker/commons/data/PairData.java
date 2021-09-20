package fr.webmaker.commons.data;

import java.io.Serializable;

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
public class PairData<A, B> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected A dataA;

	protected B dataB;

}
