-- doordashDB.PhoneNumbers definition

CREATE TABLE IF NOT EXISTS PhoneNumbers(
  id int unsigned NOT NULL AUTO_INCREMENT,
  phone_number varchar(12) NOT NULL CHECK (phone_number <> ''),
  phone_type enum('home','cell') NOT NULL CHECK (phone_type <> ''),
  occurence bigint NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX id_pn_pt_index ON PhoneNumbers (id, phone_number, phone_type);
