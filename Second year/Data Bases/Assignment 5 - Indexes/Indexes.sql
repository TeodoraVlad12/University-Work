use AnimalShelter;

-- Work on 3 tables of the form Ta(aid, a2, …), Tb(bid, b2, …), Tc(cid, aid, bid, …), where:
-- - aid, bid, cid, a2, b2 are integers;
-- - the primary keys are underlined;
-- - a2 is UNIQUE in Ta;
-- - aid and bid are foreign keys in Tc, referencing the primary keys in Ta and Tb, respectively.

GO;

------Ta
CREATE TABLE People2 (
	PersonID INT PRIMARY KEY IDENTITY(1,1),  --aid
	PersonName VARCHAR(50),
	PhoneNumber INT UNIQUE,   --a2
	City VARCHAR(MAX)
	);

------Tb
CREATE TABLE Animal2(
	AnimalID INT PRIMARY KEY IDENTITY(1,1),  --bid
	AnimalName VARCHAR(50),
	Age INT    --b2
	);


-------Tc
CREATE TABLE Adoption2(
	Person INT REFERENCES People2 (PersonID), ---aid
	Animal INT REFERENCES Animal2 (AnimalID), ---bid
	AdoptionID INT PRIMARY KEY IDENTITY(1,1)   --cid
	);


GO;


CREATE OR 
ALTER PROCEDURE GenerateNrOfPeople @nrOfPeople INT
AS
BEGIN
	DECLARE @i INT;
	DECLARE @phoneNr INT;
	SET @i = 0;
	SET @phoneNr = 5000000;
	while (@i < @nrOfPeople)
		BEGIN 
		INSERT INTO People2 VALUES ('Person Number ' + convert(varchar(10), @i), @phoneNr, 'City Number ' + convert(varchar(10), @i))
		set @i = @i+1;
		set @phoneNr = @phoneNr+1;
		END
END
GO




GO;

CREATE OR ALTER PROCEDURE GenerateNumberOfAnimals @nrOfAnimals INT
AS
BEGIN
	DECLARE @i INT;
	SET @i = 0;
	
	while (@i < @nrOfAnimals)
		BEGIN

		INSERT INTO Animal2 VALUES ('Animal number ' + convert(varchar(10), @i), @i%10 );
		SET @i = @i +1;

		END
END
GO




CREATE OR ALTER PROCEDURE GenerateNrOfAdoptions @nrOfAdoptions INT
AS
BEGIN
	DECLARE @personID INT, @animalID INT, @i INT
	SET @i = 0;

	WHILE (@i < @nrOfAdoptions)
		BEGIN
			SET @personID = (SELECT TOP 1 PersonID FROM People2 ORDER BY NEWID())
			SET @animalID = (SELECT TOP 1 AnimalID FROM Animal2 ORDER BY NEWID())

			 IF EXISTS(SELECT * FROM ( SELECT * FROM Adoption2 WHERE Person = @personID) as PeopleFromAdoptions    --If the combination of Person and Animal exists we skip the current iteration
                      WHERE Animal = @animalID)
                BEGIN
                    CONTINUE
                END

			INSERT INTO Adoption2
            VALUES (@personID, @animalID)

            SET @i = @i + 1
		END
END
GO



EXEC GenerateNrOfPeople 7000
EXEC GenerateNumberOfAnimals 10000
EXEC GenerateNrOfAdoptions 4500

SELECT * FROM People2
SELECT * FROM Animal2
SELECT * FROM Adoption2




-------------------------------------------A------------------------------------
-- a. Write queries on Ta such that their execution plans contain the following operators:
-- - clustered index scan;
-- - clustered index seek;
-- - nonclustered index scan;
-- - nonclustered index seek;
-- - key lookup.



---Clustered index scan

--This operator is used when the query requires a full scan of the clustered index (usually the primary key) to retrieve the requested data.
--It is likely to be used when your query doesn't have a WHERE clause that filters on the clustered index column.

SELECT *
FROM People2
ORDER BY PersonID


----Non clustered index scan

--This operator is used when the query can use a nonclustered index to scan and retrieve the required data.
--It might be used when your query doesn't filter on the leading column(s) of the nonclustered index, or when a large portion of the table needs to be scanned.

SELECT PhoneNumber
FROM People2

DROP INDEX IF EXISTS nonClusteredIndexPerson on People2
CREATE NONCLUSTERED INDEX nonClusteredIndexPerson ON People2 (PhoneNumber)


SELECT PhoneNumber
FROM People2



----Clustered index seek

--This operator is used when the query can seek directly to the required rows in the clustered index.
--It is likely to be used when your query has a WHERE clause that filters on the clustered index column.

SELECT *
FROM People2
WHERE PersonID > 3500



---Non clustered index seek

--This operator is used when the query can use a nonclustered index to directly seek the required rows.
--It is likely to be used when your query has a WHERE clause that filters on the leading column(s) of the nonclustered index.

DROP INDEX IF EXISTS nonClusteredIndexPerson on People2
CREATE NONCLUSTERED INDEX nonClusteredIndexPerson ON People2 (PhoneNumber)

SELECT PhoneNumber
FROM People2
WHERE PhoneNumber > 5000000
  AND PhoneNumber < 6000000


---Key lookup

 --This operator is used when a nonclustered index doesn't cover all the columns required by the query, and the query needs to perform additional lookups into the clustered index or base table to retrieve the remaining columns.
--It is likely to be used when your nonclustered index is not a covering index for your query.

SELECT PersonName
FROM People2
WHERE PhoneNumber = 5003078





---------------------------------------------------- B ------------------------
-- Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan. 
-- Create a nonclustered index that can speed up the query. 
-- Examine the execution plan again.

SELECT Age
FROM Animal2
WHERE Age = 7

DROP INDEX IF EXISTS nonClusteredIndexAnimal ON Animal2
CREATE NONCLUSTERED INDEX nonClusteredIndexAnimal ON Animal2 (Age) INCLUDE (AnimalName, AnimalID)



------------------------------------------------------ C ------------------------------
-- Create a view that joins at least 2 tables. 
-- Check whether existing indexes are helpful; if not, reassess existing indexes / examine the cardinality of the tables.

GO
CREATE OR ALTER VIEW ViewC AS
SELECT TOP 1000 P.PersonName , Anim.AnimalName
FROM People2 P
		 INNER JOIN Adoption2 A on P.PersonID = A.Person
		 INNER JOIN Animal2 Anim on Anim.AnimalID = A.Animal
WHERE P.PhoneNumber > 5000000 AND Anim.Age > 5
ORDER BY Anim.Age
GO

SELECT * FROM ViewC


DROP INDEX IF EXISTS nonClusteredIndexPerson ON People2
CREATE NONCLUSTERED INDEX nonClusteredIndexPerson ON People2 (PhoneNumber) INCLUDE (PersonName)
DROP INDEX IF EXISTS nonClusteredIndexAnimal ON Animal2
CREATE NONCLUSTERED INDEX nonClusteredIndexAnimal ON Animal2 (Age) INCLUDE (AnimalName)
