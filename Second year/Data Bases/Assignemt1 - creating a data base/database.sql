drop table Animal;
drop table Person;
drop table Cage;
drop table Employee;
drop table Volunteer;
drop table Activity;
drop table ShelterClinic;
drop table Adoption;
drop table Donation;
drop table Food;


CREATE TABLE Animal (
    AnimalID int NOT NULL,
    AnimalName varchar(255),
    AnimalAge tinyint,
    AnimalType varchar(255),
    AnimalWeight int,
    AnimalCondition varchar(255), --sick/ healthy
    DateOfArrival date,
    CONSTRAINT PK_Animal PRIMARY KEY(AnimalID)
);

CREATE TABLE Person (
    PersonID int NOT NULL,
    PersonFirstName varchar(255),
    PersonLastName varchar(255),
    PersonAge int,
    PersonCountry varchar(255),
    PersonPhoneNr varchar(255),
    PersonEmail varchar(255),
    CONSTRAINT PK_Person PRIMARY KEY(PersonID)

);

CREATE TABLE Employee (
    EmployeeID int NOT NULL,
    EmployeeSpecialization varchar(510),
    EmployeeSalary int,
    CONSTRAINT PK_Employee PRIMARY KEY(EmployeeID),
    CONSTRAINT FK_Employee FOREIGN KEY(EmployeeID) REFERENCES Person(PersonID)   -- 1 to 1 relationship

)

CREATE TABLE Volunteer (
    VolunteerID int NOT NULL,
    VolunteerHours int,
    CONSTRAINT PK_Volunteer PRIMARY KEY(VolunteerID),
    CONSTRAINT FK_Volunteer FOREIGN KEY(VolunteerID) REFERENCES Person(PersonID)   -- 1 to 1 relationship

);

CREATE TABLE Activity (
    ActivityID int NOT NULL,
    ActivityDuration int,
    ActivityVolunteerID int,
    ActivityEmployeeID int,
    ActivityFrequency int,
    CONSTRAINT FK_VolunteerActivity FOREIGN KEY(ActivityVolunteerID) REFERENCES Volunteer(VolunteerID),
    CONSTRAINT FK_EmployeeActivity FOREIGN KEY(ActivityEmployeeID) REFERENCES Employee(EmployeeID)

);


CREATE TABLE Cage
(
	CageID int NOT NULL,
	AnimalID int NOT NULL,
	CONSTRAINT PK_Cage PRIMARY KEY(CageID),
	CONSTRAINT FK_AnimalCage FOREIGN KEY(AnimalID) REFERENCES Animal(AnimalID)   --one to many
);

CREATE TABLE ShelterClinic
(
	AnimalID int NOT NULL,
	DoctorID int NOT NULL,
	TreatmentType varchar(255) NOT NULL, -- (vaccinations / prescriptions / procedures / other)
	TreatmentDate date,
	TreatmentCost int,
	TreatmentDescription text,
	CONSTRAINT PK_Treatment PRIMARY KEY(AnimalID, DoctorID, TreatmentDate),  --many to many, an animal can receive treatments from multiple doctors, and a doctor can treat multiple animals
	CONSTRAINT FK_AnimalClinic FOREIGN KEY(AnimalID) REFERENCES Animal(AnimalID),
	CONSTRAINT FK_Doctor FOREIGN KEY(DoctorID) REFERENCES Employee(EmployeeID)
);

CREATE TABLE Adoption
(
	AnimalID int NOT NULL,
	PersonID int NOT NULL,
	DateOfAdoption date NOT NULL,
	CONSTRAINT PK_Adoption PRIMARY KEY(AnimalID, PersonID),   --many to many
	CONSTRAINT FK_AdoptedAnimal FOREIGN KEY(AnimalID) REFERENCES Animal(AnimalID),
	CONSTRAINT FK_Adopter FOREIGN KEY(PersonID) REFERENCES Person(PersonID)
);

CREATE TABLE Donation
(
	PersonID int NOT NULL,
	DonationType varchar(255), -- cash/supplies
	DonationAmount int,
	DonationDate date NOT NULL,
	CONSTRAINT PK_Donation PRIMARY KEY(PersonID, DonationType, DonationDate),
	CONSTRAINT FK_Donor FOREIGN KEY(PersonID) REFERENCES Person(PersonID)    --one to many
);

CREATE TABLE Food
(
     FoodID int,
    FoodQuantity int,
    FoodAnimalID int,
    CONSTRAINT PK_FOOD PRIMARY KEY(FoodID),
    CONSTRAINT FK_Eater FOREIGN KEY(FoodAnimalID) REFERENCES Animal(AnimalID)  --one to many, one animal can have multiple food records
);



