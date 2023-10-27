#include "A9designer.h"
#include <QtWidgets/QApplication>
#include<vector>
#include "Trench.h"
#include "repo.h"
#include "service.h"
#include "UserService.h"
#include "validator.h"
#include "ui.h"
#include "A9designer.h"
using std::vector;

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    //A9designer w;


	vector<Trench> repoArray;  //admin repo
	repoArray.reserve(10);
	std::string file_name = "Text.txt";  //"C:\\Users\\Teo\\source\\repos\\A6\\Text.txt";
	Repo repo(repoArray, file_name);
	repo.initialiseRepo();
	Service service(repo);
	UserService userService{ repo };
	TrenchValidator validator{};
	//UI ui{ service, userService, validator };

	A9designer gui{service,validator};


	


    gui.show();
    return a.exec();
}
