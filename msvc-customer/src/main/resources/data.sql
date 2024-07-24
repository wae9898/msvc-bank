CREATE TABLE PERSONS (
                         PERSON_ID BIGSERIAL PRIMARY KEY,
                         NAME VARCHAR(255) NOT NULL,
                         GENDER VARCHAR(255) NOT NULL,
                         AGE INTEGER NOT NULL,
                         IDENTIFICATION VARCHAR(255) NOT NULL,
                         ADDRESS VARCHAR(255) NOT NULL,
                         PHONE VARCHAR(255) NOT NULL
);

CREATE TABLE CUSTOMERS (
                           CUSTOMER_ID BIGSERIAL CONSTRAINT uc_customer_id UNIQUE NOT NULL,
                           PASSWORD VARCHAR(255) NOT NULL,
                           STATUS VARCHAR(255) NOT NULL,
                           PERSON_ID BIGINT NOT NULL,
                           CONSTRAINT fk_person_id_persons FOREIGN KEY(PERSON_ID)
                               REFERENCES PERSONS(PERSON_ID)
);