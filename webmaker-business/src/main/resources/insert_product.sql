/*************************************************************************************************************** 
 *************************************************** PRODUIT *************************************************** 
 ***************************************************************************************************************/
-- Produit n°10000001 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity) VALUES (10000001, 'REF10000001', 2);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT, friendly_url, meta_description, meta_title)
VALUES (10000001, 'fr', 'NOM-10000001', 'DESCRIPTIF, PRODUIT 10000001', 'DESCRIPTIF COURT, PRODUIT 10000001', '/produit001', 'meta description fr produit 001', 'meta title fr produit 001'),
	(10000001, 'en', 'NAME-10000001', 'DESCRIPTION, PRODUCT 10000001', 'DESCRIPTION SHORT, PRODUCT 10000001', '/produit001', 'meta description an produit 001', 'meta title an produit 001');
	
-- Produit n°10000002 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity) VALUES (10000002, 'REF10000002', 2);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000002, 'fr', 'NOM-10000002', 'DESCRIPTIF, PRODUIT 10000002', 'DESCRIPTIF COURT, PRODUIT 10000002'),
	(10000002, 'en', 'NAME-10000002', 'DESCRIPTION, PRODUCT 10000002', 'DESCRIPTION SHORT, PRODUCT 10000002');

-- Produit n°10000003 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity, price_HT) VALUES (10000003, 'REF10000003', 2, 28);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000003, 'fr', 'NOM-10000003', 'DESCRIPTIF, PRODUIT 10000003', 'DESCRIPTIF COURT, PRODUIT 10000003'),
	(10000003, 'en', 'NAME-10000003', 'DESCRIPTION, PRODUCT 10000003', 'DESCRIPTION SHORT, PRODUCT 10000003');
	
-- Produit n°10000004 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity, price_HT) VALUES (10000004, 'REF10000004', 2, 28);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000004, 'fr', 'NOM-10000004', 'DESCRIPTIF, PRODUIT 10000004', 'DESCRIPTIF COURT, PRODUIT 10000004'),
	(10000004, 'en', 'NAME-10000004', 'DESCRIPTION, PRODUCT 10000004', 'DESCRIPTION SHORT, PRODUCT 10000004');
	
-- Produit n°10000005 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity, price_HT) VALUES (10000005, 'REF10000005', 2, 28);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000005, 'fr', 'NOM-10000005', 'DESCRIPTIF, PRODUIT 10000005', 'DESCRIPTIF COURT, PRODUIT 10000005'),
	(10000005, 'en', 'NAME-10000005', 'DESCRIPTION, PRODUCT 10000005', 'DESCRIPTION SHORT, PRODUCT 10000005');
	
-- Produit n°10000006 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity, price_HT) VALUES (10000006, 'REF10000006', 2, 28);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000006, 'fr', 'NOM-10000006', 'DESCRIPTIF, PRODUIT 10000006', 'DESCRIPTIF COURT, PRODUIT 10000006'),
	(10000006, 'en', 'NAME-10000006', 'DESCRIPTION, PRODUCT 10000006', 'DESCRIPTION SHORT, PRODUCT 10000006');
	
	-- Produit n°10000007 (traduction fr)
INSERT INTO PRODUCT (product_id, reference, quantity, price_HT) VALUES (10000007, 'REF10000007', 2, 28);
INSERT INTO PRODUCT_LANG (PRODUCT_ID, CODE_LANG, NAME, DESCRIPTION, DESCRIPTION_SHORT)
VALUES (10000007, 'fr', 'NOM-10000007', 'DESCRIPTIF, PRODUIT 10000007', 'DESCRIPTIF COURT, PRODUIT 10000007'),
	(10000007, 'en', 'NAME-10000007', 'DESCRIPTION, PRODUCT 10000007', 'DESCRIPTION SHORT, PRODUCT 10000007');
	
/*************************************************************************************************************** 
 ********************************************** VARIATION PRODUIT ********************************************** 
 ***************************************************************************************************************/
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000001, 'variation 01 pour 10000001', 10000001);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000002, 'variation 02 pour 10000001', 10000001);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000003, 'variation 03 pour 10000001', 10000002);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000004, 'variation 01 pour 10000001', 10000002);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000005, 'variation 02 pour 10000001', 10000003);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000006, 'variation 03 pour 10000001', 10000003);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000007, 'variation 01 pour 10000001', 10000004);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000008, 'variation 02 pour 10000001', 10000004);
INSERT INTO PRODUCT_VARIATION (id_product_variation, name, id_product) VALUES(11000009, 'variation 03 pour 10000001', 10000005);


/*************************************************************************************************************** 
 ************************************************* PRODUIT SEO ************************************************* 
 ***************************************************************************************************************/

/*************************************************************************************************************** 
 ************************************************* PRODUIT STOCK ************************************************* 
 ***************************************************************************************************************/


/*************************************************************************************************************** 
 *************************************************** CATEGORY ************************************************** 
 ***************************************************************************************************************/
-- Category n°20000001 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000001 , null, true, 1, '2021-08-02 12:05:06');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000001 , 'fr', 'DESCRIPTION 20000001', 'NOM 20000001'),
(20000001 , 'en', 'DESCRIPTIF 20000001', 'NAME 20000001');

-- Category n°20000002 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000002 , null, true, 1, '2021-05-12 04:05:34');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000002 , 'fr', 'DESCRIPTION 20000002', 'NOM 20000002'),
(20000002 , 'en', 'DESCRIPTIF 20000002', 'NAME 20000002');

-- Category n°20000003 (traduction fr)
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000003 , null, true, 1, '2021-02-23 15:23:54');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000003 , 'fr', 'DESCRIPTION 20000003', 'NOM 20000003'),
(20000003 , 'en', 'DESCRIPTIF 20000003', 'NAME 20000003');

-- Category n°20000004 (traduction fr) - Root Category n°20000003
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000004 , 20000003, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000004 , 'fr', 'DESCRIPTION 20000004', 'NOM 20000004'),
(20000004 , 'en', 'DESCRIPTIF 20000004', 'NAME 20000004');

-- Category n°20000005 (traduction fr) - Root Category n°20000003
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000005 , 20000003, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000005 , 'fr', 'DESCRIPTION 20000005', 'DESIGNATION 20000005'),
(20000005 , 'en', 'DESCRIPTIF 20000005', 'NAME 20000005');

-- Category n°20000006 (traduction fr) - Root Category n°20000004
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000006 , 20000004, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000006 , 'fr', 'DESCRIPTION 20000006', 'DESIGNATION 20000006'),
(20000006 , 'en', 'DESCRIPTIF 20000006', 'NAME 20000006');

-- Category n°20000007 (traduction fr) - Root Category n°20000006
INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000007 , 20000006, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000007 , 'fr', 'DESCRIPTION 20000007', 'NOM 20000007'),
(20000007 , 'en', 'DESCRIPTIF 20000007', 'NAME 20000007');

INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000008 , null, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000008 , 'fr', 'DESCRIPTION 20000008', 'NOM 20000008'),
(20000008 , 'en', 'DESCRIPTIF 20000008', 'NAME 20000008');

INSERT INTO CATEGORY (category_id, category_parent_id, displayed, position, created)
VALUES(20000009 , null, true, 1, '2021-04-04 11:07:16');
INSERT INTO CATEGORY_LANG (category_id, code_lang, description, name)
VALUES(20000009 , 'fr', 'DESCRIPTION 20000009', 'NOM 20000009'),
(20000009 , 'en', 'DESCRIPTIF 20000009', 'NAME 20000009');

/*************************************************************************************************************** 
 *********************************************** PRODUCT_CATEGORY ********************************************** 
 ***************************************************************************************************************/
-- Produit Category 
INSERT INTO PRODUCT_CATEGORY(product_id, category_id)
VALUES(10000001, 20000001), (10000002, 20000002), (10000005, 20000003);


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


