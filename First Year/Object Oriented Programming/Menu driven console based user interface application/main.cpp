
#include "repo.h"
#include "service.h"
#include "ui.h"
#include "tests.h"
#include "trench.h"


int main() {


	vector<Trench> repoArray;  //admin repo
	repoArray.reserve(10);
	std::string file_name = "Text.txt";  //"C:\\Users\\Teo\\source\\repos\\A6\\Text.txt";
	Repo repo(repoArray, file_name);
	repo.initialiseRepo();
	//repo.generateTrenchList();
	Service service(repo);
	UserService userService{repo};
	TrenchValidator validator{};
	UI ui{ service, userService, validator };
	ui.start();
	
	testAll();

	return 0;
}

