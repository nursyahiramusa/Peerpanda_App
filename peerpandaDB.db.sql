BEGIN TRANSACTION;
DROP TABLE IF EXISTS "BookingDetail";
CREATE TABLE IF NOT EXISTS "BookingDetail" (
	"stuID"	TEXT NOT NULL,
	"datetime"	TEXT,
	"location"	TEXT,
	"total_pay"	TEXT,
	"coursecode"	TEXT,
	"tutorID"	TEXT NOT NULL,
	PRIMARY KEY("stuID")
);
COMMIT;
