DROP TABLE IF EXISTS "front_end"."department";
CREATE TABLE "front_end"."department" (
  "id" varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "disabled" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" varchar COLLATE "pg_catalog"."default",
  "sync" bool
)
;
ALTER TABLE "front_end"."department" OWNER TO "ctirobot";

ALTER TABLE "front_end"."department" ADD CONSTRAINT "department_pkey" PRIMARY KEY ("id");

DROP TABLE IF EXISTS "front_end"."item_category";
CREATE TABLE "front_end"."item_category" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "disabled" bool,
  "sync" bool
)
;
ALTER TABLE "front_end"."item_category" OWNER TO "ctirobot";

INSERT INTO "front_end"."item_category" VALUES ('1', '器械包', '1', NULL, 'f', 't');
COMMIT;

ALTER TABLE "front_end"."item_category" ADD CONSTRAINT "item_category_pkey" PRIMARY KEY ("id");


DROP TABLE IF EXISTS "front_end"."item";
CREATE TABLE "front_end"."item" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "disabled" bool,
  "category_id" varchar(255) COLLATE "pg_catalog"."default",
  "max_unit" varchar(255) COLLATE "pg_catalog"."default",
  "specification" varchar(255) COLLATE "pg_catalog"."default",
  "provider" varchar(255) COLLATE "pg_catalog"."default",
  "bar_code" varchar(255) COLLATE "pg_catalog"."default",
  "manufacturer" varchar(255) COLLATE "pg_catalog"."default",
  "note" varchar(255) COLLATE "pg_catalog"."default",
  "sync" bool
)
;
ALTER TABLE "front_end"."item" OWNER TO "ctirobot";

ALTER TABLE "front_end"."item" ADD CONSTRAINT "item_pkey" PRIMARY KEY ("id");
