/*************************************************************************************************************** 
 *************************************************** PRODUIT *************************************************** 
 ***************************************************************************************************************/
-- Produit n°10000001 (traduction fr)
INSERT INTO PRODUCT (product_id, reference) VALUES (10000001, 'REF10000001');
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000001, 'fr', 'NOM-10000001', 'DESCRIPTIF, PRODUIT 10000001', 'DESCRIPTIF COURT, PRODUIT 10000001'),
	(10000001, 'an', 'NAME-10000001', 'DESCRIPTION, PRODUCT 10000001', 'DESCRIPTION SHORT, PRODUCT 10000001');
	
-- Produit n°10000002 (traduction fr)
INSERT INTO PRODUCT (product_id, reference) VALUES (10000002, 'REF10000002');
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000002, 'fr', 'NOM-10000002', 'DESCRIPTIF, PRODUIT 10000001', 'DESCRIPTIF COURT, PRODUIT 10000002'),
	(10000002, 'an', 'NAME-10000002', 'DESCRIPTION, PRODUCT 10000001', 'DESCRIPTION SHORT, PRODUCT 10000002');

-- Produit n°10000002 (traduction fr)
INSERT INTO PRODUCT (product_id, reference) VALUES (10000003, 'REF10000003');
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000003, 'fr', 'NOM-10000003', 'DESCRIPTIF, PRODUIT 10000003', 'DESCRIPTIF COURT, PRODUIT 10000003'),
	(10000003, 'an', 'NAME-10000003', 'DESCRIPTION, PRODUCT 10000003', 'DESCRIPTION SHORT, PRODUCT 10000003');

/*************************************************************************************************************** 
 *************************************************** CATEGORY ************************************************** 
 ***************************************************************************************************************/
-- Category n°20000001 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000001 , null, true, 1, '2021-08-02 12:05:06');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000001 , 'fr', 'DESCRIPTION 20000001', 'NOM 20000001'),
(20000001 , 'an', 'DESCRIPTIF 20000001', 'NAME 20000001');

-- Category n°20000002 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000002 , null, true, 1, '2021-05-12 04:05:34');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000002 , 'fr', 'DESCRIPTION 20000002', 'NOM 20000002'),
(20000002 , 'an', 'DESCRIPTIF 20000002', 'NAME 20000002');

-- Category n°20000003 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000003 , null, true, 1, '2021-02-23 15:23:54');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000003 , 'fr', 'DESCRIPTION 20000003', 'NOM 20000003'),
(20000003 , 'an', 'DESCRIPTIF 20000003', 'NAME 20000003');

-- Category n°20000004 (traduction fr) - Root Category n°20000003
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000004 , 20000003, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000004 , 'fr', 'DESCRIPTION 20000004', 'NOM 20000004'),
(20000004 , 'an', 'DESCRIPTIF 20000004', 'NAME 20000004');

-- Category n°20000005 (traduction fr) - Root Category n°20000003
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000005 , 20000003, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000005 , 'fr', 'DESCRIPTION 20000005', 'DESIGNATION 20000005'),
(20000005 , 'an', 'DESCRIPTIF 20000005', 'NAME 20000005');

-- Category n°20000006 (traduction fr) - Root Category n°20000004
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000006 , 20000004, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000006 , 'fr', 'DESCRIPTION 20000006', 'DESIGNATION 20000006'),
(20000006 , 'an', 'DESCRIPTIF 20000006', 'NAME 20000006');

-- Category n°20000007 (traduction fr) - Root Category n°20000006
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000007 , 20000006, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000007 , 'fr', 'DESCRIPTION 20000007', 'NOM 20000007'),
(20000007 , 'an', 'DESCRIPTIF 20000007', 'NAME 20000007');

/*************************************************************************************************************** 
 *********************************************** PRODUCT_CATEGORY ********************************************** 
 ***************************************************************************************************************/
-- Produit Category 
INSERT INTO PRODUCT_CATEGORY(product_id, category_id)
VALUES(10000001, 20000001), (10000002, 20000002), (10000003, 20000003);


/*************************************************************************************************************** 
 ************************************** CONFIG_IMAGE (données de référence) ************************************ 
 ***************************************************************************************************************/
-- Exemple image product_thumbnail-10000001.jpg ou PRODUCTS-10000001-COVER-DEFAULT
INSERT INTO CONFIG_IMAGE(config_image_id, name, device_type, produtcs, categories, brands, suppliers, stores, width, height)
VALUES (11000111, 'header'		, 'DEFAULT'	, false, false, false, false, false, 1110	, 214),
(11000112, 'store'		  		, 'DEFAULT'	, false, false, false, false, false, 170	, 115),
(11000113, 'logo'		  		, 'DEFAULT'	, false, false, false, false, false, 200	, 40),
(11000114, 'slideshow'	  		, 'DEFAULT'	, false, false, false, false, false, 1110	, 340),
(11000115, 'favicon'	  		, 'DEFAULT'	, false, false, false, false, false, 32		, 32),
(11000116, 'favicon'			, 'MOBILE'	, false, false, false, false, false, 16		, 16),
-- Extra small
(11000118, 'thumbnail'			, 'DEFAULT'	, true, false, false, false, false, 45	, 45),
-- Small
(11000119, 'small'				, 'DEFAULT'	, true, false, false, false, false, 94	, 94),
-- Medium 
(11000120, 'medium'	    		, 'DEFAULT'	, true, false, false, false, false, 452	, 452),
-- Large 
(11000121, 'large'				, 'DEFAULT'	, true, false, false, false, false, 800	, 800),
-- Extra Large
(11000124, 'extra_large' 		, 'DEFAULT'	, true, false, false, false, false, 1100, 1100),
-- Autres
(11000123, 'category_product'	, 'DEFAULT'	, true, false, false, false, false, 141	, 180),
(11000125, 'cart_product'		, 'DEFAULT'	, true, false, false, false, false, 125	, 125),
(11000122, 'home_product'		, 'DEFAULT'	, true, false, false, false, false, 250	, 250);


