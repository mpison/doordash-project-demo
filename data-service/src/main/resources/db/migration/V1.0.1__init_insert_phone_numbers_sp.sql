CREATE PROCEDURE IF NOT EXISTS insert_phone_numbers(in phone_number_input varchar(11), in phone_type_input varchar(4))
BEGIN
	DECLARE counter_occurence int;
	SELECT count(*) INTO counter_occurence FROM PhoneNumbers pn  WHERE pn.phone_number  = phone_number_input and pn.phone_type = phone_type_input ;

	set counter_occurence = counter_occurence + 1;

	Insert into PhoneNumbers  (phone_number, phone_type, occurence) values (phone_number_input, phone_type_input, counter_occurence);

	select LAST_INSERT_ID() as id, phone_number_input as phone_number, phone_type_input as phone_type, counter_occurence as occurence;

END;
