package fr.commerces.commons.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public final class UtilityTest {
	public final static String LANG_CODE_BIDON = "xx";
	public final static String LANG_CODE_NOTUSE = "tt";
	public final static String LANG_CODE_FR = "fr";
	
	/*
	 * PRODUCT
	 */
	public final static long PRODUCT_ID_BIDON = -1L;
	public final static long PRODUCT_ID_10000001 = 10000001L;

	/*
	 * CATEGORY
	 */
	public final static long CATEGORY_ID_BIDON = -1L;
	
	// Catégorie n° 20000001 (2021-08-02 12:05:06 GMT)
	public final static Long CATEGORY_ID_20000001 = 20000001L;
	public final static String CATEGORY_IDENTIFIER_20000001FR = "20000001/fr";
	public final static LocalDateTime CATEGORY_CREATED_20000001 = LocalDate.of(2021, Month.AUGUST, 2).atTime(12, 05, 06);
	
	// Catégorie n° 20000002 (2021-05-12 04:05:34 GMT)
	public final static Long CATEGORY_ID_20000002 = 20000002L;
	public final static String CATEGORY_IDENTIFIER_20000002FR = "20000002/fr";
	public final static LocalDateTime CATEGORY_CREATED_20000002 = LocalDate.of(2021, Month.MAY, 12).atTime(4, 5, 34);
	
	// Catégorie n° 20000003 (2021-02-23 15:23:54 GMT)
	public final static Long CATEGORY_ID_20000003 = 20000003L;
	public final static String CATEGORY_IDENTIFIER_20000003FR = "20000003/fr";
	public final static LocalDateTime CATEGORY_CREATED_20000003 = LocalDate.of(2021, Month.FEBRUARY, 23).atTime(15, 23, 54);
	
	// Catégorie n° 20000004 (2021-04-04 11:07:16 GMT)
	public final static Long CATEGORY_ID_20000004 = 20000004L;
	public final static String CATEGORY_IDENTIFIER_20000004FR = "20000004/fr";
	public final static LocalDateTime CATEGORY_CREATED_20000004 = LocalDate.of(2021, Month.APRIL, 4).atTime(11, 7, 16);

	/*
	 * CUSTUMER
	 */
	public final static long CUSTUMER_BIDON = -1L;
	
	/*
	 * COMMAND
	 */
	public final static long COMMANDE_BIDON = -1L;

}