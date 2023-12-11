--Toys ->  (has a single-column primary key and no foreign keys)
--Food	->  (has a single-column primary key an one foreign key)
--Donation	-> (has a multicolumn primary key)

use AnimalShelter;

-- insert @no_rows test records in the Animal table
-- (ToyID, ToyType)

GO;
CREATE PROCEDURE InsertTestRecordsInToysTable @no_rows INT
AS
BEGIN
	DECLARE @added_records INT;
	SET @added_records = 0;

	DECLARE @current_id INT;
	SET @current_id = 1111;

	WHILE @added_records < @no_rows
		BEGIN

			INSERT INTO Toys
			(ToyID, ToyType)
			VALUES
			(@current_id, 'TypeTest');

			SET @current_id = @current_id +1;
			SET @added_records = @added_records +1;
		END
END
GO;



-- insert @no_rows test records in the Food table
-- (FoodID, FoodQuantity, FoodAnimalID )

CREATE PROCEDURE InsertTestRecordsInFoodTable @no_rows INT
AS
	DECLARE @added_records INT;
	SET @added_records = 0;

	DECLARE @current_id INT;
	SET @current_id = 1111;

	DECLARE @random_quantity INT;
	DECLARE @random_animal_id INT;


	WHILE @added_records < @no_rows
		BEGIN
			SET @random_quantity = FLOOR(RAND() * 100 + 1);

			SET @random_animal_id = (SELECT TOP 1 Animal.AnimalID
										FROM Animal
										ORDER BY NEWID());


			INSERT INTO Food
			(FoodID, FoodQuantity, FoodAnimalID)
			VALUES
			(@current_id, @random_quantity, @random_animal_id);


			SET @current_id = @current_id +1;
			SET @added_records = @added_records +1;
		END
GO;




-- insert @no_rows test records in the Donation table
-- (PersonID, DonationType, DonationAmount, DonationDate) PK(PersonID, DonationType, DonationDate)
CREATE PROCEDURE InsertTestRecordsInDonationTable @no_rows INT
AS
	DECLARE @added_records INT;
	SET @added_records = 0;

	DECLARE @random_day INT, @random_month INT, @random_year INT;
	DECLARE @random_date VARCHAR(255);
	DECLARE @random_person_id INT;
	DECLARE @random_amount INT;
	

	WHILE @added_records < @no_rows
		BEGIN
			SET @random_day = FLOOR(RAND() * 29 + 1);
			SET @random_month = FLOOR(RAND() * 12 + 1);
			SET @random_year = FLOOR(RAND() * 10 + 2010);

			SET @random_date = CONVERT(VARCHAR(4), @random_year) + '/' + CONVERT(VARCHAR(2), @random_month) + '/' + CONVERT(VARCHAR(2), @random_day);
			SET @random_amount = FLOOR(RAND() * 100 + 1);

			SET @random_person_id = (SELECT TOP 1 Person.PersonID
										FROM Person
										ORDER BY NEWID());
			

			INSERT INTO Donation
			(PersonID, DonationType, DonationAmount, DonationDate)
			VALUES
			(@random_person_id, 'TypeTest', @random_amount, @random_date);

			SET @added_records = @added_records +1;
		END

GO;



-- remove all test records from a table  
CREATE PROCEDURE DeleteTestRecordsInTable @table_name VARCHAR(255)
AS
	EXEC('DELETE FROM ' + @table_name);
GO;


EXEC DeleteTestRecordsInTable 'Toys';
EXEC DeleteTestRecordsInTable 'Food';
EXEC DeleteTestRecordsInTable 'Donation';



EXEC InsertTestRecordsInToysTable 10;
EXEC InsertTestRecordsInFoodTable 10;
EXEC InsertTestRecordsInDonationTable 10;


SELECT *  FROM Toys;
SELECT *  FROM Food;
SELECT *  FROM Donation;




-- CREATING THE VIEWS

--a view with a SELECT statement operating on one table;
GO;
CREATE VIEW QuantityOver2View AS
SELECT Food.FoodQuantity, Food.FoodID
FROM Food
WHERE Food.FoodQuantity > 2;
GO;

SELECT * FROM QuantityOver2View;



-- a view with a SELECT statement operating on at least 2 tables:


GO;
CREATE VIEW FoodWithAnimalView AS
SELECT Animal.AnimalName, Food.FoodQuantity
FROM Food INNER JOIN Animal ON Food.FoodAnimalID = Animal.AnimalID;
GO;

SELECT * FROM FoodWithAnimalView ;



--- a view with a SELECT statement that has a GROUP BY clause and operates on at least 2 tables

GO;
CREATE VIEW DonationCountryView AS
SELECT Person.PersonCountry, COUNT(Donation.DonationDate) as Donations
FROM Donation INNER JOIN Person ON Donation.PersonID = Person.PersonID
GROUP BY Person.PersonCountry;
GO;

SELECT * FROM DonationCountryView;





