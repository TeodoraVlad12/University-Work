#include "repo.h"
#include <vector>
#include <fstream>
#include <algorithm>

using std::vector;

RepoException::RepoException(std::string& _message) : message(_message) {}

const char* RepoException::what() const noexcept {
	return message.c_str();
}





Repo::Repo(vector<Trench> repoArray, std::string& fileName) {
	this->trenchsList = repoArray;
	this->fileName = fileName;

}

void Repo::loadFromFile() {
	if (!this->fileName.empty()) {
		Trench trenchFromFile;
		std::ifstream fin(this->fileName);
		while (fin >> trenchFromFile) {
			if (std::find(this->trenchsList.begin(), this->trenchsList.end(), trenchFromFile) == this->trenchsList.end())
				this->trenchsList.push_back(trenchFromFile);
		}
		fin.close();
	}

}

void Repo::writeToFile() {
	if (!this->fileName.empty())
	{
		std::ofstream fout(this->fileName);
		for (const Trench& trench : this->trenchsList) {
			fout << trench << "\n";
		}
		fout.close();
	}
}

void Repo::initialiseRepo() {
	this->loadFromFile();
}

Repo::~Repo() {

}

Trench* Repo::getAllRepo() {
	if (this->trenchsList.empty()) {
		std::string error;
		error += std::string("The database is empty!");
		if (!error.empty())
			throw RepoException(error);
	}
	return this->trenchsList.data();

}

vector<Trench> Repo::getAllVector()
{
	return this->trenchsList;
}

int Repo::getNrElements() {
	if (this->trenchsList.empty()) {
		std::string error;
		error += std::string("The database is empty!");
		if (!error.empty())
			throw RepoException(error);
	}
	return this->trenchsList.size();
}


//int Repo::getCap() {
//	return this->trenchsList->capacityGetter();
//}



int Repo::findByColour(std::string colour) {
	for (int i = 0; i < this->getNrElements(); i++) {
		Trench someTrench = this->trenchsList.at(i);
		std::string colourSomeTrench = someTrench.getColour();
		if (colourSomeTrench == colour) {
			return i;
		}
	}
	return -1;

	//for (auto tr : this->trenchsList) {
	//	//Trench someTrench = this->trenchsList.at(i);
	//	std::string colourSomeTrench = tr.getColour();
	//	if (colourSomeTrench == colour) {
	//		return i;
	//	}
	//}
	//return -1;

}

void Repo::addRepo(const Trench& trench) {
	int existing = this->findByColour(trench.getColour());
	if (existing != -1) {
		std::string error;
		error += "The trench already exists!";
		if (!error.empty())
			throw RepoException(error);
	}
	this->trenchsList.push_back(trench);
	this->writeToFile();

}

void Repo::deleteRepo(int index) {
	if (index == -1) {
		std::string error;
		error += std::string("The trench does not exist!");
		if (!error.empty())
			throw RepoException(error);
	}
	this->trenchsList.erase(this->trenchsList.begin() + index );
	this->writeToFile();
}

void Repo::updateRepo(const Trench& newTrench, int index) {
	if (index == -1) {
		std::string error;
		error += std::string("The trench does not exist!");
		if (!error.empty())
			throw RepoException(error);
	}
	this->trenchsList.at(index) = newTrench;
	this->writeToFile();
}

void Repo::generateTrenchList() {
	Trench t1 = Trench(36, "blue", 450, 20, "https://www.moja.ro/storage/2022/05/40373/c/5-large.jpg");
	this->trenchsList.push_back(t1);
	Trench t2 = Trench(38, "yellow", 590, 10, "https://www.aboutyou.ro/p/only/geaca-de-primavara-toamna-elisa-8584475");
	this->trenchsList.push_back(t2);

	Trench t3= Trench(40, "red", 700, 3, "https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2F5e%2F65%2F5e651e2a0626e6816bb4408425572d0c1a50cd55.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BLOOKBOOK%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]");
	this->trenchsList.push_back(t3);

	Trench t4 = Trench(42, "green", 444, 4, "https://img01.ztat.net/article/spp-media-p1/05e646c9868d4507ab601e08e8a90e4e/89cbb4d9f0794bb9b1a6cedf32290848.jpg?imwidth=762");
	this->trenchsList.push_back(t4);

	Trench t5 = Trench(34, "brown", 488, 5, "https://m.media-amazon.com/images/I/71YVof7ZANL._AC_UL1500_.jpg");
	this->trenchsList.push_back(t5);

	Trench t6 = Trench(36, "purple", 455, 20, "https://m.media-amazon.com/images/I/71LEg3uuExL._AC_UL1500_.jpg");
	this->trenchsList.push_back(t6);

	Trench t7 = Trench(36, "greenn", 800, 20, "https://m.media-amazon.com/images/I/61nqV-ecpfL._AC_UL1430_.jpg");
	this->trenchsList.push_back(t7);

	Trench t8 = Trench(38, "pink", 700, 20, "https://m.media-amazon.com/images/I/61p6omKTuvL._AC_UL1500_.jpg");
	this->trenchsList.push_back(t8);

	Trench t9 = Trench(40, "magenta", 600, 20, "https://m.media-amazon.com/images/I/81O6P7t5aQL._AC_UL1500_.jpg");
	this->trenchsList.push_back(t9);

	Trench t10 = Trench(42, "orange", 555, 20, "https://m.media-amazon.com/images/I/611LfF2bR2L._AC_UL1500_.jpg");
	this->trenchsList.push_back(t10);

	this->writeToFile();


}

