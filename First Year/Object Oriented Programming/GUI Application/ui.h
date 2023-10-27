#pragma once
#include "service.h"
#include "UserService.h"
#include "validator.h"

class UI {
private:
	Service& service;
	UserService& userService;
	TrenchValidator& validator;

public:
	

	UI(Service& service, UserService& userService, TrenchValidator& validator1 );
	~UI();
	void listTrenchsSize(int size);
	void listTrenchs();
	void addUI();
//	void generateTrenchList();
	void deleteUI();
	void updateUI();

	void openFile();

//Prints the trench coats of a given size one by one or all of them if there is none of that colour
	void listTrenchsOnebyOne();
	
	static void printAdminSubmenu();
	static void printUserSubmenu();
	void AdminMode();
	void UserMode();
	static void printMenu();
	void start();

//Displays all the trench coats in the basket
	void showBasket();
};