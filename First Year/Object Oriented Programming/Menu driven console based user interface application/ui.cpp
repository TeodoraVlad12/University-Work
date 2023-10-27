#include "ui.h"
#include "Trench.h"
#include <iostream>
#include <windows.h>

using namespace std;

UI::UI(Service& service, UserService& userService, TrenchValidator& validator1): service(service), userService(userService), validator(validator1) {
}

UI::~UI() = default;

void UI::listTrenchsSize(int size) {
	Trench* trenchs = this->service.getAllService();
	int nrElements = this->service.getNrElementsService();
	if (nrElements == 0)
		throw "No trenchs in the database!\n";
	else {
		bool ok = 0;
		cout << endl;
		for (int i = 0; i < nrElements; i++) {
			if (trenchs[i].getSize() == size) {
				ok = 1;
				cout << i + 1 << ". " << trenchs[i].toString() << endl;
				cout << endl;
			}
		}
		if (ok == 0) {
			cout << endl;
			cout << "There are no trench coats of this size...So these are all of them!" << endl<<endl;
			for (int i = 0; i < nrElements; i++) {
				cout << i + 1 << ". " << trenchs[i].toString() << endl;
				cout << endl;

			}
		}
	}
}

void UI::listTrenchs() {
	Trench* trenchs = this->service.getAllService();
	int nrElements = this->service.getNrElementsService();
	if (nrElements == 0)
		throw "No trenchs in the database!\n";
	else {
		cout << endl;
		for (int i = 0; i < nrElements; i++) {
			cout << i + 1 << ". " << trenchs[i].toString() << endl;
			cout << endl;
		}
	}
}

//void UI::generateTrenchList() {
//	this->service->addService(36, "blue", 450, 20, "https://www.moja.ro/storage/2022/05/40373/c/5-large.jpg");
//	this->service->addService(38, "yellow", 590, 10, "https://www.aboutyou.ro/p/only/geaca-de-primavara-toamna-elisa-8584475");
//	this->service->addService(40, "red", 700, 3, "https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2F5e%2F65%2F5e651e2a0626e6816bb4408425572d0c1a50cd55.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BLOOKBOOK%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]");
//	this->service->addService(42, "green", 444, 4, "https://img01.ztat.net/article/spp-media-p1/05e646c9868d4507ab601e08e8a90e4e/89cbb4d9f0794bb9b1a6cedf32290848.jpg?imwidth=762");
//	this->service->addService(34, "brown", 488, 5, "https://m.media-amazon.com/images/I/71YVof7ZANL._AC_UL1500_.jpg");
//	this->service->addService(36, "purple", 455, 20, "https://m.media-amazon.com/images/I/71LEg3uuExL._AC_UL1500_.jpg");
//	this->service->addService(36, "green", 800, 20, "https://m.media-amazon.com/images/I/61nqV-ecpfL._AC_UL1430_.jpg");
//	this->service->addService(38, "pink", 700, 20, "https://m.media-amazon.com/images/I/61p6omKTuvL._AC_UL1500_.jpg");
//	this->service->addService(40, "magenta", 600, 20, "https://m.media-amazon.com/images/I/81O6P7t5aQL._AC_UL1500_.jpg");
//	this->service->addService(42, "orange", 555, 20, "https://m.media-amazon.com/images/I/611LfF2bR2L._AC_UL1500_.jpg");
//}




void UI::addUI() {
	std::string size_s, colour, price_s, quantity_s, photograph;
	int size, price, quantity;

	while (true) {
		try {
			cout << "Enter size: ";
			getline(cin, size_s);
			this->validator.validateSizeString(size_s);
			 size = stoi(size_s);
			this->validator.validateSize(size);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}

	while (true) {
		try {
			cout << "Enter colour: ";
			getline(cin, colour);
			this->validator.validateColour(colour);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}

	while (true) {
		try {
			cout << "Enter price: ";
			getline(cin, price_s);
			this->validator.validatePriceString(price_s);
			price = stoi(price_s);
			this->validator.validateSize(price);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}

	while (true) {
		try {
			cout << "Enter quantity: ";
			getline(cin, quantity_s);
			this->validator.validateQuantityString(quantity_s);
		 quantity = stoi(quantity_s);
			this->validator.validateSize(quantity);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	while (true) {
		try {
			std::cout << "Enter the photograph: ";
			getline(std::cin, photograph);
			this->validator.validatePhotoLink(photograph);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	/*cout << "Enter size: ";
	getline(cin, size_s);
	int size = stoi(size_s);
	cout << "Enter colour: ";
	getline(cin, colour);
	cout << "Enter price: ";
	getline(cin, price_s);
	int price = stoi(price_s);
	cout << "Enter quantity: ";
	getline(cin, quantity_s);
	int quantity = stoi(quantity_s);
	cout << "Enter photo: ";
	getline(cin, photograph);*/


	int result = this->service.addService(size, colour, price, quantity, photograph);
	if (result == 1)
		cout << endl << "Trench added successfully!" << endl;
	else if (result == 0)
		cout << endl << "Trench of this colour already exists!" << endl;
}

void UI::deleteUI() {
	string colour;
	while (true) {
		try {
			cout << "Enter colour: ";
			getline(cin, colour);
			this->validator.validateColour(colour);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}

	int result = this->service.deleteService(colour);
	if (result == 1)
		cout << endl << "Trench deleted successfully!" << endl;
	else if (result == 0)
		cout << endl << "The trench does not exist!" << endl;


}

void UI::updateUI() {

	int size, price, quantity;
	std::string size_s, colour, price_s, quantity_s, photograph;

	while (true) {
		try {
			cout << "Enter the colour of the trench you want updated: ";
			getline(cin, colour);
			this->validator.validateColour(colour);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	while (true) {
		try {
			cout << "Enter new size: ";
			getline(cin, size_s);
			this->validator.validateSizeString(size_s);
			size = stoi(size_s);
			this->validator.validateSize(size);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	while (true) {
		try {
			cout << "Enter new price: ";
			getline(cin, price_s);
			this->validator.validatePriceString(price_s);
			price = stoi(price_s);
			this->validator.validateSize(price);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}

	while (true) {
		try {
			cout << "Enter new quantity: ";
			getline(cin, quantity_s);
			this->validator.validateQuantityString(quantity_s);
			quantity = stoi(quantity_s);
			this->validator.validateSize(quantity);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	while (true) {
		try {
			std::cout << "Enter the new photograph: ";
			getline(std::cin, photograph);
			this->validator.validatePhotoLink(photograph);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}





	/*string new_size_s, colour, new_price_s, new_quantity_s, new_photo;
	cout << "Enter the colour of the trench coat that you want updated: " << endl;
	getline(cin, colour);
	cout << "Enter the new size: ";
	getline(cin, new_size_s);
	int new_size = stoi(new_size_s);
	cout << "Enter the new price: ";
	getline(cin, new_price_s);
	int new_price = stoi(new_price_s);
	cout << "Enter the new quantity: ";
	getline(cin, new_quantity_s);
	int new_quantity = stoi(new_quantity_s);
	cout << "Enter the new photo: ";
	getline(cin, new_photo);*/


	int result = this->service.updateService(size, colour, price, quantity, photograph);
	if (result == 1)
		cout << endl << "Trench updated successfully!" << endl;
	else if (result == 0)
		cout << "The trench does not exist!" << endl;

}

void UI::listTrenchsOnebyOne() {
	std::string option;
	bool done = false;
	int index = 0;
	unsigned int length = this->service.getNrElementsService();
	int size;
	std::string size_s;

	while (true) {
		try {
			cout << "Enter size: ";
			getline(cin, size_s);
			this->validator.validateSizeString(size_s);
			size = stoi(size_s);
			this->validator.validateSize(size);
			break;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
	}


	while (!done) {
		if (length == 0) {
			std::string error;
			error += std::string("The database is empty!");
			if (!error.empty())
				throw RepoException(error);
		}

		if (index == length)
			index = 0;
		std::cout << this->service.getAllService()->toString() << std::endl;
		std::cout << "Add to cart? [yes/ no/ exit]: ";
		//std::string link = std::string("start").append(this->service.getAllService()[index].getPhotograph());
		//system(link.c_str());
		getline(std::cin, option);
		std::cout << endl;
		if (option == "yes") {
			Trench trench = this->service.getAllService()[index];
			this->userService.addUserService(trench);
			length = this->service.getNrElementsService();
			index++;
		}
		else if (option == "no") {
			index++;
		}
		else if (option == "exit")
			done = true;
		else
			std::cout << "Invalid input!" << std::endl;
		if (length == 0)
			done = true;
	}

}

void UI::showBasket() {
	std::vector<Trench> basket;
	basket = this->userService.getAllUserService();
	int index = 0;
	for (const Trench& trench : basket) {
		std::cout <<endl<< index + 1 << ". " << trench.toString() << std::endl;
		index++;
	}
}

void UI::openFile() {
	
	//"C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Excel 2016.lnk"
	//ShellExecuteA(NULL, NULL, "excel.exe", "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Excel 2016.lnk", NULL, SW_SHOWMAXIMIZED);


	//ShellExecuteA(NULL, NULL, "excel.exe", "file.cvs", NULL, SW_SHOWMAXIMIZED);



	//ShellExecuteA(NULL, NULL, "excel.exe", "C:\\Users\\Teo\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\fileA6.xlsx", NULL, SW_SHOWMAXIMIZED);


	ShellExecuteA(NULL, NULL, "excel.exe", "file.csv", NULL, SW_SHOWMAXIMIZED);
	//ShellExecuteA(NULL, NULL, "chrome.exe", this->userService.getFileService().c_str(), NULL, SW_SHOWMAXIMIZED);
	
}

void UI::printAdminSubmenu() {
	cout << endl << "THIS IS ADMINISTRATOR MODE" << endl;
	cout << "1. List all the trench coats" << endl;
	cout << "2. Add a new trench coat" << endl;
	cout << "3. Delete a trench coat"<< endl;
	cout << "4. Update a trench coat" << endl;
	cout << "0. Exit administrator mode" << endl << endl;

}

void UI::printUserSubmenu() {
	cout << endl << "THIS IS USER MODE" << endl;
	cout << "1. List all the trench coats of a given size" << endl;
	cout << "2. See shopping cart" << endl;
	cout << "3. See basket file" << endl;
	cout << "0. Exit user mode" << endl << endl;
	cout << "Enter option: ";
}

void UI::AdminMode() {
	cout << "You are the admin now!" << endl << endl;
	bool not_done = true;
	
	while (not_done) {
		try {
			printAdminSubmenu();
			cout << "Enter option: ";
			std::string option;
			getline(cin, option);
			if (option == "0") {
				not_done = false;
				cout << "Exiting admin mode..." << endl;
			}
			else if (option == "1") {
				this->listTrenchs();
			}
			else if (option == "2") {
				this->addUI();
			}
			else if (option == "3") {
				this->deleteUI();
			}
			else if (option == "4") {
				this->updateUI();
			}
			else {
				cout << "Invalid option. Please, try again!" << endl;
			}
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
		catch (RepoException& ex) {
			std::cout << ex.what() << std::endl;
		}
		
	}
}

void UI::UserMode() {
	cout << "You are the user now!" << endl << endl;
	bool done = false;
	while (!done) {
		try {
			printUserSubmenu();
			std::string option;
			getline(std::cin, option);
			if (option == "0") {
				done = true;
				std::cout << "Exiting user mode..." << std::endl;
			}
			else if (option == "1")
				listTrenchsOnebyOne();
			else if (option == "2")
				showBasket();
			else if (option == "3")
				this->openFile();
			else
				std::cout << "Invalid option!" << std::endl;
		}
		catch (ValidationException& ex) {
			std::cout << ex.what() << std::endl;
		}
		catch (RepoException& ex) {
			std::cout << ex.what() << std::endl;
		}
		catch (UserException& ex) {
			std::cout << ex.what() << std::endl;
		}
		
	}
	

}

void UI::printMenu() {
	std::cout << endl << "1. Administrator mode" << std::endl;
	std::cout << "2. User mode" << std::endl;
	std::cout << "0. Exit" << endl;
	std::cout << "Enter option: ";
}

void UI::start() {
	cout << "Find the perfect trench coat!" << endl;
	int usermode = 0;
	bool done = false;
	while (!done) {
		printMenu();
		std::string option;
		getline(cin, option);
		if (option == "0")
		{
			done = true;
			cout << "Byeeee!" << std::endl;
		}
		else if (option == "1")
			AdminMode();
		else if (option == "2")
		{
			if (usermode == 0) {
				std::cout << "Which type of file do you want?(cvs/ html): " ;
				std::string fileType;
				getline(cin, fileType);
				this->userService.repoType(fileType);


				usermode = 1;
			}
			UserMode();
		}
		else
			cout << "Invalid option!" << endl;
	}

}

