CREATE DATABASE IF NOT EXISTS lab_5abc;
USE lab_5abc;

CREATE TABLE IF NOT EXISTS train (
    id_train VARCHAR(4) PRIMARY KEY,
    CONSTRAINT train_id_uindex UNIQUE (id_train)
);


CREATE TABLE IF NOT EXISTS coach (
    id_coach INT AUTO_INCREMENT PRIMARY KEY,
    number_place INT NOT NULL,
    type_coach VARCHAR(15) NOT NULL,
    number_coach VARCHAR(4) NOT NULL,
    train_id VARCHAR(4) NOT NULL default 'n/a',
    CONSTRAINT coach_id_uindex UNIQUE (id_coach),
    CONSTRAINT coach_street_id_fk FOREIGN KEY (train_id)
        REFERENCES train (id_train)
);


CREATE TABLE ticket (
    id_ticket INT AUTO_INCREMENT PRIMARY KEY,
    date_departuere DATE NOT NULL,
    place_departuere VARCHAR(25) NOT NULL,
    price_ticket INT NOT NULL,
    CONSTRAINT ticket_id_uindex UNIQUE (id_ticket)
);


CREATE TABLE IF NOT EXISTS ticket_coach (
    ticket_id INT NOT NULL,
    coach_id INT NULL,
    CONSTRAINT ticket_coach_pharmacy_id_fk FOREIGN KEY (coach_id)
        REFERENCES coach (id_coach),
    CONSTRAINT coach_ticket_medicines_id_fk FOREIGN KEY (ticket_id)
        REFERENCES ticket (id_ticket)
);
CREATE INDEX ticket_coach_coach_id_fk
  ON ticket_coach (coach_id);

insert into coach (number_place, type_coach, number_coach, train_id) values
				  (32,'kupe', 13, '120П'),
			      (54,'plackart', 21, '220Ж'),
				  (12,'lux', 2, '99К'),
                  (15,'kupe', 12, '220Ж'),
				  (3,'plackart', 23, '311Т'),
                  (4,'plackart', 24, '311Т'),
				  (22,'lux', 5, '120П');

insert into ticket (date_departuere, place_departuere, price_ticket) values
				   ('2017-10-09', 'Lviv', 128),
				   ('2018-06-29', 'Dnipro', 87),
				   ('2017-02-05', 'Kropyvnitskyi', 147),
				   ('2017-04-22', 'Dnipro', 160),
                   ('2017-04-22', 'Rivne', 122),
                   ('2017-04-22', 'Rivne', 122),
				   ('2017-04-22', 'Chonhar', 90);

insert train (id_train) values ('120П'), ('220Ж'), ('99К'), ('311Т');

DELIMITER //
DROP PROCEDURE IF EXISTS InsertCoach;//

CREATE PROCEDURE InsertCoach
(
IN number_place_in   int,
IN type_coach_in        varchar(15),
IN number_coach_in   varchar(4),
in train_in          VARCHAR(15)
)
BEGIN
	DECLARE msg varchar(40);

  IF NOT EXISTS( SELECT * FROM train WHERE number_train = train_in)
    THEN SET msg = 'This coach is absent';
  ELSE
		INSERT coach (number_place, type_coach, number_coach, train_id)
        Value (number_place_in, type_coach_in, number_coach_in,
			     (SELECT id_train FROM train WHERE train.number_train = train_in) );
		SET msg = 'OK';

	END IF;

	SELECT msg AS msg;

END //
DELIMITER ;