create sequence public.hibernate_sequence;

create table public.product
(
    product_id int8 primary key,
    reference varchar(255),
    code_client varchar(255),
    code_barre  varchar(255),
    price_ht int8,
    quantity int8,
    package_Width  int8,
    package_Height int8,
    package_Depth int8,
    package_Weight int8
);





