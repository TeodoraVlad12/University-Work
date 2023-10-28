   PRAGMA foreign_keys = ON;
                                                    --Insert--

--TO DO VIOLATING REFERENTIAL CONSTRAINTS

INSERT INTO Animal
(AnimalID ,AnimalName ,AnimalAge ,AnimalType ,AnimalWeight ,AnimalCondition ,DateOfArrival)
VALUES
(1, 'Micu', 10, 'dog', 15, 'healthy', '2023-9-1'),
(2, 'Bob', 2, 'dog', 5, 'healthy', '2022-8-2'),
(3, 'Cassandra', 5, 'cat', 4, 'sick', '2023-10-10');


INSERT INTO Animal
VALUES
(4, 'Puffy', 6, 'dog', 8, 'sick', '2023-4-4'),
(5, 'Coco', 2, 'bird', 2, 'sick', '2022-2-2');

SELECT * FROM Animal;

INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(1,'Andrei','Pop',20, 'Romania', '0732312311','andrei@gmail.com'),
(2,'Bianca','Nedelcu',20,'Romania','12345678999','bianca@gmail.com'),
(3, 'Luca', 'Medea', 25, 'Spain','1223456789','luca@yahoo.com'),
(4, 'Aurel', 'Vlaicut', 50, 'Romania','1234567898', 'aurel@hotmail.com');

INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(5,'Mihai','Lungu',21,'Romania','1234567891','mihai@gmail.com'),
(6, 'Ion','Nedelea',34,'Romania','123456783','ion@gmail.com'),
(7, 'Malina', 'Ureche', 25,'Italy','2325621231','malina@gmail.com'),
(8, 'Paul','Popa',45,'Sweden','1234567894','paul@gmail.com');


INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(9,'Ana','Pop',34,'Romania','1234567892','ana@gmail.com'),
(10,'Giga','Gard',44,'Spain','1234567897','giga@yahoo.com');


INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(11,'Ana-Maria','Popescu',34,'Romania','1234557892','anamaria@gmail.com'),
(12,'Angela','Minulescu',44,'Spain','1234567697','angela@yahoo.com');

SELECT * FROM Person;

INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(13,'Angel','Pop',44,'Germany','1234567697','angela@yahoo.com');


INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(14,'Marcel','Pop',40,'Portugal','1234562697','marcel@yahoo.com');


INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(15,'Costel','Bratu',24,'France','1232567697','costel@yahoo.com');

INSERT INTO Employee
(EmployeeID, EmployeeSpecialization, EmployeeSalary)
VALUES
(1, 'doctor', 7000),
(4, 'feeder', 3000),
(2, 'secretary', 6000),
(3,'feeder',3000);

INSERT INTO Employee
(EmployeeID, EmployeeSpecialization, EmployeeSalary)
VALUES
(9, 'doctor', 10000);

INSERT INTO Employee
(EmployeeID, EmployeeSpecialization, EmployeeSalary)
VALUES
(13, 'doctor', 9000);

INSERT INTO Employee
(EmployeeID, EmployeeSpecialization, EmployeeSalary)
VALUES
(14, 'doctor', 5000);

INSERT INTO Adoption
(AnimalID, PersonID, DateOfAdoption)
VALUES
(1, 11, '2023-10-1'),
(3, 12, '2023-10-11');

SELECT * FROM Adoption;


INSERT INTO ShelterClinic
(AnimalID, DoctorID, TreatmentType, TreatmentDate, TreatmentCost, TreatmentDescription)
VALUES
(2, 1, 'vaccinations', '2022-8-3', 200, 'ok'),
(3, 9, 'procedure', '2023-10-12', 1000, 'ok');

SELECT * FROM Adoption;

--Violating integrity constraints--
INSERT INTO Employee
(EmployeeID, EmployeeSpecialization, EmployeeSalary)
VALUES
(999,'doctor',8000);

SELECT * FROM Employee;

INSERT INTO Volunteer
(VolunteerID, VolunteerHours )
VALUES
(5, 200),
(6, 10),
(7, 134),
(8, 234);

SELECT * FROM Volunteer;



                                                    --Update--Delete---
UPDATE Volunteer
SET VolunteerHours=987
WHERE VolunteerID=8 ;

/*
DELETE FROM Employee
WHERE EmployeeID=1;

INSERT INTO Employee
VALUES
(1,'doctor',700);
*/
UPDATE Employee
SET EmployeeSalary= EmployeeSalary+100
WHERE EmployeeSpecialization = 'doctor' AND EmployeeSalary<10000;

/*
DELETE FROM Person
WHERE PersonID BETWEEN 11 AND 12;

INSERT INTO Person
(PersonID,  PersonFirstName, PersonLastName ,PersonAge ,PersonCountry ,PersonPhoneNr ,PersonEmail )
VALUES
(11,'Ana-Maria','Popescu',34,'Romania','1234557892','anamaria@gmail.com'),
(12,'Angela','Minulescu',44,'Spain','1234567697','angela@yahoo.com');
*/

SELECT * FROM Person;

UPDATE Person
SET PersonAge = PersonAge+1
WHERE PersonFirstName LIKE 'A%a';


                                   --Queries--

----a----
--Select person id for the persons who are either older than 25 or are employees
SELECT P.PersonID
FROM Person P
WHERE P.PersonAge > 25
UNION
SELECT E.EmployeeID
FROM Employee E;


--Animals who are either less than 10 years or are sick
SELECT A1.AnimalID, A1.AnimalName, A1.AnimalAge, A1.AnimalCondition
FROM Animal A1
WHERE A1.AnimalAge < 10 OR A1.AnimalCondition = 'sick';


----b----
--Selecting persons who are older than 25 and are feeders
SELECT P.PersonID
FROM Person P
WHERE P.PersonAge > 25
INTERSECT
SELECT E.EmployeeID
FROM Employee E
WHERE E.EmployeeSpecialization = 'feeder'

--Persons employed from Romania
SELECT P.PersonFirstName, P.PersonID, P.PersonCountry, E.EmployeeSpecialization
FROM Person P, Employee E
WHERE P.PersonID = E.EmployeeID AND P.PersonID IN (
    SELECT P2.PersonID
    FROM PERSON P2
    WHERE P2.PersonCountry = 'Romania'
    )


----c----
--Persons who are not employees
SELECT P.PersonFirstName, P.PersonID
FROM Person P
WHERE P.PersonID NOT IN (
    SELECT E.EmployeeID
    FROM Employee E
    );

SELECT * FROM Volunteer


--Dogs older than 5
SELECT A.AnimalName, A.AnimalType
FROM Animal A
WHERE A.AnimalType = 'dog'
EXCEPT
SELECT A2.AnimalName, A2.AnimalType
FROM Animal A2
WHERE A2.AnimalAge < 5


--D--
--Persons who are employees
SELECT E.EmployeeID, E.EmployeeSpecialization
FROM Employee E INNER JOIN  Person P ON E.EmployeeID = P.PersonID;


--Persons who adopted    (3 tables)
SELECT P.PersonFirstName, An.AnimalName, An.AnimalType
FROM Person P LEFT OUTER JOIN Adoption A ON P.PersonID = A.PersonID
    LEFT OUTER JOIN Animal An ON A.AnimalID = An.AnimalID;


SELECT S.TreatmentType, A.AnimalName
FROM ShelterClinic S RIGHT OUTER JOIN Animal A ON S.AnimalID = A.AnimalID;


--FULL JOIN does not work in SQL Lite so this is a simulation   (2 many to many tables)
SELECT A.AnimalID, S.TreatmentType
FROM Adoption A LEFT JOIN ShelterClinic S ON A.AnimalID = S.AnimalID
UNION
SELECT A.AnimalID, S.TreatmentType
FROM Adoption A RIGHT JOIN ShelterClinic S ON A.AnimalID = S.AnimalID;


---e---
--Animals who've been at the clinic
SELECT A.AnimalID, A.AnimalName
FROM Animal A
WHERE   A.AnimalID  IN (SELECT An.AnimalID
                        FROM Animal An, ShelterClinic S
                        WHERE An.AnimalID = S.AnimalID);

--Persons who are doctors(employees) and who have worked at the clinic before
SELECT P.PersonID, P.PersonFirstName
FROM Person P
WHERE P.PersonID IN (SELECT E.EmployeeID
                     FROM Employee E
                     WHERE E.EmployeeID IN (
                         SELECT Per.PersonId
                         FROM Person Per, ShelterClinic S
                         WHERE S.DoctorID = E.EmployeeID
                         ));


----f----
--Selecting the persons who are employees
SELECT *
FROM Person P
WHERE EXISTS (SELECT *
              FROM Employee E
              WHERE P.PersonID = E.EmployeeID);


--Animals who have been adopted
SELECT A.AnimalType, A.AnimalName
FROM Animal A
WHERE EXISTS (SELECT *
              FROM Adoption Ad
              WHERE Ad.AnimalID = A.AnimalID);



----g----
--The average salary per specialization
SELECT E.EmployeeSpecialization, AVG(E.EmployeeSalary) AS AvgSalary
FROM (
    SELECT EmployeeSpecialization, EmployeeSalary
    FROM Employee
) AS E
GROUP BY E.EmployeeSpecialization;

/*
SELECT *
FROM Employee E
WHERE E.EmployeeSpecialization = 'doctor';


UPDATE Employee
SET EmployeeSalary=5999
WHERE EmployeeID = 14

 */

--Average age per animal type
SELECT A.AnimalAge, A.AnimalType, AVG(A.AnimalAge) AS AverageAge
FROM (SELECT AnimalType, AnimalAge
      FROM Animal)
    AS A
GROUP BY A.AnimalType;



----h----
--Animals of the same type and which are more than 2
SELECT AnimalType, COUNT(*) AS AnimalCount
FROM Animal
GROUP BY AnimalType
HAVING COUNT(*) > 2;


--Maximum salary for a specialization
SELECT
FROM Employee E

