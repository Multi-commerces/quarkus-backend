create sequence public.hibernate_sequence;

-- Table des produits
create table public.product
(
    product_id int8 primary key,
    reference varchar(255),
    code_client varchar(255),
    code_barre  varchar(255),
    price_ht int8,
    quantity int8,
    package_width  int8,
    package_height int8,
    package_depth int8,
    package_weight int8
);





