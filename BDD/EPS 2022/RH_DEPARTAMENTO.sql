ALTER TABLE RRHH.RH_DEPARTAMENTO
 DROP PRIMARY KEY CASCADE;

DROP TABLE RRHH.RH_DEPARTAMENTO CASCADE CONSTRAINTS;

CREATE TABLE RRHH.RH_DEPARTAMENTO
(
  PAIS                      NUMBER(3)           NOT NULL,
  DEPARTAMENTO              NUMBER(3)           NOT NULL,
  NOMBRE_DEPARTAMENTO       VARCHAR2(30 BYTE)   NOT NULL,
  ABREVIATURA_DEPARTAMENTO  VARCHAR2(5 BYTE)    NOT NULL,
  AREA                      VARCHAR2(1 BYTE)    DEFAULT 'P'                   NOT NULL,
  CODIGO_PRESUPUESTARIO     NUMBER(4)           NOT NULL,
  CUOTA_LABORAL_IGSS        NUMBER(8,4)         DEFAULT 0                     NOT NULL,
  CUOTA_PATRONAL_IGSS       NUMBER(8,4)         DEFAULT 0                     NOT NULL,
  CODIGO_BANCARIO           NUMBER(3),
  AREA_GEOGRAFICA           CHAR(1 BYTE)        DEFAULT 'C'                   NOT NULL,
  ID_CEDULA                 VARCHAR2(3 BYTE),
  UCIORDEN                  NUMBER(3)           DEFAULT 999
)
TABLESPACE TS_RRHH2021
RESULT_CACHE (MODE DEFAULT)
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          40K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING
ENABLE ROW MOVEMENT;

COMMENT ON TABLE RRHH.RH_DEPARTAMENTO IS 'Descripcion general de los Departamentos y/o Provincias del Pais';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.PAIS IS 'Codigo del pais';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.DEPARTAMENTO IS 'Codigo del departamento o provincia';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.NOMBRE_DEPARTAMENTO IS 'Nombre del departamento o provincia';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.ABREVIATURA_DEPARTAMENTO IS 'Abreviatura del departamento';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.AREA IS 'Area geografica donde se encuentra el departamento, CENTRAL, OCCIDENTE, ORIENTE, NORTE, SUR.';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.CODIGO_PRESUPUESTARIO IS 'Codigo presupestario definido para este departamento';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.CUOTA_LABORAL_IGSS IS 'Porcentaje de la cuota laboral del IGSS para el OJ, segun el departamento.';

COMMENT ON COLUMN RRHH.RH_DEPARTAMENTO.CUOTA_PATRONAL_IGSS IS 'Cuotra patronal de IGSS';



CREATE INDEX RRHH.XIF2DEPARTAMENTO ON RRHH.RH_DEPARTAMENTO
(PAIS)
LOGGING
TABLESPACE TS_RRHH_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          40K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


CREATE UNIQUE INDEX RRHH.XPKDEPARTAMENTO ON RRHH.RH_DEPARTAMENTO
(PAIS, DEPARTAMENTO)
LOGGING
TABLESPACE TS_RRHH_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          40K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


GRANT SELECT ON RRHH.RH_DEPARTAMENTO TO SIDEP;

