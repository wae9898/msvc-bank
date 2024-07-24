CREATE TABLE ACCOUNTS (
                          ACCOUNT_ID BIGSERIAL PRIMARY KEY,
                          NUMBER NUMERIC(10,2) CONSTRAINT uc_account_number UNIQUE NOT NULL,
                          CUSTOMER_ID BIGINT NOT NULL,
                          ACCOUNT_TYPE_ID BIGINT NOT NULL,
                          INITIAL_BALANCE NUMERIC(10,2),
                          STATUS BOOLEAN
);

CREATE TABLE ACCOUNT_TYPES (
                               ACCOUNT_TYPE_ID BIGSERIAL PRIMARY KEY,
                               NAME VARCHAR(255),
                               DESCRIPTION TEXT,
                               STATUS BOOLEAN
);

CREATE TABLE MOVEMENTS (
                           MOVEMENT_ID BIGSERIAL PRIMARY KEY,
                           DATE DATE,
                           MOVEMENT_TYPE_ID BIGINT NOT NULL,
                           VALUE NUMERIC(10,2),
                           BALANCE NUMERIC(10,2),
                           ACCOUNT_ID BIGINT NOT NULL
);

CREATE TABLE MOVEMENT_TYPES (
                                MOVEMENT_TYPE_ID BIGSERIAL PRIMARY KEY,
                                NAME VARCHAR(255)
);

ALTER TABLE ACCOUNTS ADD CONSTRAINT FK_ACCOUNTS_ACCOUNT_TYPES
    FOREIGN KEY (ACCOUNT_TYPE_ID) REFERENCES ACCOUNT_TYPES(ACCOUNT_TYPE_ID);

ALTER TABLE MOVEMENTS ADD CONSTRAINT FK_MOVEMENTS_ACCOUNTS
    FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS(ACCOUNT_ID);

ALTER TABLE MOVEMENTS ADD CONSTRAINT FK_MOVEMENTS_MOVEMENT_TYPES
    FOREIGN KEY (MOVEMENT_TYPE_ID) REFERENCES MOVEMENT_TYPES(MOVEMENT_TYPE_ID);


INSERT INTO public.account_types (account_type_id, name, description, status) VALUES (DEFAULT, 'Ahorro', 'cuenta para ahorros', true);
INSERT INTO public.account_types (account_type_id, name, description, status) VALUES (DEFAULT, 'Corriente', 'cuenta corriente para cuenta habientes', true);


INSERT INTO public.movement_types (movement_type_id, name) VALUES (DEFAULT, 'RETIRO');
INSERT INTO public.movement_types (movement_type_id, name) VALUES (DEFAULT, 'DEPOSITO');