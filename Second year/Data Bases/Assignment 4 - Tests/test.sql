use AnimalShelter;


-- procedure to create a new test
GO;
CREATE PROCEDURE AddTest @test_name VARCHAR(255)
AS
	INSERT INTO Tests(name)
	VALUES(@test_name);
GO;


-- procedure to add a table to the list (table) of tables which will be tested
GO;
CREATE PROCEDURE AddTableForTesting @table_name VARCHAR(255)
AS
	INSERT INTO Tables
	VALUES(@table_name);
GO;

-- procedure to add a new TestTable test instance: associate an existing test to an existing table
-- @no_rows = how many records the test will have to insert in the given table
-- @position = in which order the test should be run e.g., if position 2 is given then all tests with position < 2
--			   will be executed first, then this test, then all tests with position > 2
GO;
CREATE PROCEDURE AddTestTables @test_name VARCHAR(255), @table_name VARCHAR(255), @no_rows INT, @position INT
AS
	DECLARE @test_id INT, @table_id INT;
	SET @test_id = (SELECT Tests.test_id
					FROM Tests
					WHERE Tests.name = @test_name);
	SET @table_id = (SELECT Tables.table_id
					 FROM Tables
					 WHERE Tables.name = @table_name);

	INSERT INTO TestTables(test_id, table_id, no_of_rows, position)
	VALUES(@test_id, @table_id, @no_rows, @position);
GO;


-- procedure to add a view to the list (table) of views which will be tested
GO;
CREATE PROCEDURE AddViewForTesting @view_name VARCHAR(255)
AS
	INSERT INTO Views
	VALUES(@view_name);
GO;

-- procedure to add a new TestView test instance: associate an existing test to an existing view
GO;
CREATE PROCEDURE AddTestViews @test_name VARCHAR(255), @view_name VARCHAR(255)
AS
	DECLARE @test_id INT, @view_id INT;
	SET @test_id = (SELECT Tests.test_id
					FROM Tests
					WHERE Tests.name = @test_name);
	SET @view_id = (SELECT Views.view_id
					 FROM Views
					 WHERE Views.name= @view_name);

	INSERT INTO TestViews(test_id, view_id)
	VALUES(@test_id, @view_id);
GO;



-- procedure to run an existing test
GO;
CREATE PROCEDURE RunningTest @test_name VARCHAR(255)
AS
	DECLARE @test_id INT, @table_id INT, @no_rows INT, @test_run_id INT, @view_id INT;
	DECLARE @start_time DATETIME, @end_time DATETIME, @all_views_test_start_time DATETIME, @all_views_test_end_time DATETIME;
	DECLARE @table_name VARCHAR(255), @command VARCHAR(255), @view_name VARCHAR(255);

	-- get the ID of the test with the given name
	SET @test_id = (SELECT Tests.test_id
					FROM Tests
					WHERE Tests.name = @test_name);

	-- insert a new record in the table TestRuns, but only insert its description since we don't have start/end times as of now
	INSERT INTO TestRuns(description)
	VALUES(@test_name);

	-- get the ID of the new record inserted in TestRuns so that we can update the start and end times later
	SET @test_run_id = (SELECT TOP 1 TestRuns.test_run_id
						FROM TestRuns
						ORDER BY TestRuns.test_run_id DESC);

	-- Cursor for traversing the tables which have to be tested
	-- The cursor is ordered by the field <position> since we have to delete in the order given by position
	-- and insert in the opposite order
	DECLARE test_tables_cursor SCROLL CURSOR FOR
		SELECT Tables.table_id, Tables.name, TestTables.no_of_rows
		FROM Tables INNER JOIN TestTables ON Tables.table_id = TestTables.table_id
		WHERE TestTables.test_id = @test_id
		ORDER BY TestTables.position;

	-- delete test records in the tables
	OPEN test_tables_cursor;

	FETCH FIRST FROM test_tables_cursor
	INTO @table_id, @table_name, @no_rows;

	-- go over every table which needs to be tested and delete all the data from them
	WHILE @@FETCH_STATUS = 0
		BEGIN
			SET @command = 'DeleteTestRecordsInTable';
			EXEC @command @table_name;

			FETCH NEXT FROM test_tables_cursor
			INTO @table_id, @table_name, @no_rows;
		END


	-- insert test records in the tables
	FETCH LAST FROM test_tables_cursor
	INTO @table_id, @table_name, @no_rows;

	-- Go over every table which needs to be tested and add <@no_rows> test records in them
	-- Now the order is opposite of the order by which we deleted (we used FETCH LAST above and we use FETCH PRIOR below)
	WHILE @@FETCH_STATUS = 0
		BEGIN
			SET @start_time = GETDATE();
			SET @command = 'InsertTestRecordsIn' + @table_name + 'Table';
			EXEC @command @no_rows;
			SET @end_time = GETDATE();

			INSERT INTO TestRunTables(test_run_id, table_id, start_at, end_at)
			VALUES(@test_run_id, @table_id, @start_time, @end_time);

			FETCH PRIOR FROM test_tables_cursor
			INTO @table_id, @table_name, @no_rows;
		END

	-- close and deallocate the cursor
	CLOSE test_tables_cursor;
	DEALLOCATE test_tables_cursor;




	-- TESTING THE VIEWS

	-- cursor for the views which have to be tested
	DECLARE test_views_cursor CURSOR FOR
		SELECT Views.view_id, Views.name
		FROM Views INNER JOIN TestViews ON Views.view_id = TestViews.view_id
		WHERE TestViews.test_id = @test_id

	OPEN test_views_cursor;
	
	-- save the time at which we started testing the views
	SET @all_views_test_start_time = GETDATE();

	FETCH FROM test_views_cursor
	INTO @view_id, @view_name;

	-- go over all the views which need to be tested and select everything from them
	WHILE @@FETCH_STATUS = 0
		BEGIN
			SET @command = 'SELECT * FROM ' + @view_name;
			SET @start_time = GETDATE();
			EXEC(@command);
			SET @end_time = GETDATE();
			
			INSERT INTO TestRunViews(test_run_id, view_id, start_at, end_at)
			VALUES(@test_run_id, @view_id, @start_time, @end_time);

			FETCH NEXT FROM test_views_cursor
			INTO @view_id, @view_name;
		END

	-- save the time at which we finished testing the views
	SET @all_views_test_end_time = GETDATE();

	-- close and deallocate the cursor
	CLOSE test_views_cursor;
	DEALLOCATE test_views_cursor;

	-- Now that we have the start and end time of testing the views, we can update the previously inserted
	-- record in the table TestRuns
	UPDATE TestRuns
	SET start_at = @all_views_test_start_time, end_at = @all_views_test_end_time
	WHERE TestRuns.test_run_id = @test_run_id;

GO;


-- Creating and running a test

-- add a new test
EXEC AddTest 'MyTest1';

SELECT *
FROM Tests;

-- add tables which needed to be tested
EXEC AddTableForTesting 'Toys';
EXEC AddTableForTesting 'Food';
EXEC AddTableForTesting 'Donation';

-- add views which need to be tested
EXEC AddViewForTesting 'QuantityOver2View';
EXEC AddViewForTesting 'FoodWithAnimalView';
EXEC AddViewForTesting 'DonationCountryView';

SELECT * FROM Tables;
SELECT * FROM Views;

-- Associate the tables and views which need to be tested with the added test
EXEC AddTestTables 'MyTest1', 'Donation', 10, 1;
EXEC AddTestTables 'MyTest1', 'Food', 10, 2;
EXEC AddTestTables 'MyTest1', 'Toys', 10, 3;

EXEC AddTestViews 'MyTest1', 'QuantityOver2View';
EXEC AddTestViews 'MyTest1', 'FoodWithAnimalView';
EXEC AddTestViews 'MyTest1', 'DonationCountryView';


-- add some testing records in the table so the first test (which deletes records) can work
EXEC InsertTestRecordsInToysTable 10;
EXEC InsertTestRecordsInFoodTable 10;
EXEC InsertTestRecordsInDonationTable 10;

SELECT * FROM Toys;

SELECT * FROM Food;

SELECT * FROM Donation;


EXEC RunningTest 'MyTest1';


SELECT * FROM TestRunTables;

SELECT * FROM TestRunViews;

SELECT * FROM TestRuns;


