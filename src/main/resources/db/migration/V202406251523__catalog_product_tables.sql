CREATE TABLE IF NOT EXISTS CATALOG
(
    CATALOG_ID   SERIAL PRIMARY KEY,
    NAME         VARCHAR(100) NOT NULL,
    DESCRIPTION  VARCHAR(256),
    START_DATE   DATE,
    END_DATE     DATE,
    CREATED_DATE TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS CATEGORY
(
    CATEGORY_ID  SERIAL PRIMARY KEY,
    NAME         VARCHAR(100) NOT NULL,
    DISPLAY_TEXT VARCHAR(256),
    CREATED_DATE TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCT
(
    PRODUCT_ID   SERIAL PRIMARY KEY,
    NAME         VARCHAR(100),
    DESCRIPTION  VARCHAR(256),
    SKU          VARCHAR(100) not null,
    CREATED_DATE TIMESTAMP,
    CATEGORY_ID  INT REFERENCES CATEGORY (CATEGORY_ID),
    CONSTRAINT UNIQUE_SKU UNIQUE (SKU)
);

CREATE TABLE IF NOT EXISTS INVENTORY_STATUS
(
    PRODUCT_ID SERIAL PRIMARY KEY,
    STATUS     VARCHAR(100) NOT NULL,
    QUANTITY   INTEGER,
    CONSTRAINT PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID) ON DELETE CASCADE,
    CONSTRAINT quantity_non_negative CHECK (QUANTITY >= 0)
);

CREATE TABLE IF NOT EXISTS CATALOG_PRODUCT
(
    CATALOG_ID SERIAL,
    PRODUCT_ID SERIAL,
    CONSTRAINT CATALOG_PRODUCT_PK PRIMARY KEY (CATALOG_ID, PRODUCT_ID),
    CONSTRAINT PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID) ON DELETE CASCADE,
    CONSTRAINT CATALOG_ID_FK FOREIGN KEY (CATALOG_ID) REFERENCES CATALOG (CATALOG_ID) ON DELETE CASCADE
);