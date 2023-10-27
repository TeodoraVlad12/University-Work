#include "CVSRepo.h"
#include <fstream>


CVSRepo::CVSRepo(const std::vector<Trench>& basket, const std::string& fileName)
{
	this->basket = basket;
	this->fileName = fileName;


}

std::vector<Trench>& CVSRepo::getAllUserRepo()
{
	return this->basket;
}

unsigned int CVSRepo::getNrElems()
{
	return this->basket.size();
}

unsigned int CVSRepo::getCap() {
	return this->basket.capacity();
}

void CVSRepo::addUserRepo(const Trench &trench)
{
	this->basket.push_back(trench);
	this->writeToFile();
}

void CVSRepo::writeToFile()
{
	std::ofstream fout("file.cvs");
	if (!this->basket.empty()) {
		for (const Trench& trench : this->basket) {
			fout << trench << "\n";
		}
	}
	fout.close();
}

std::string& CVSRepo::getFileName()
{
	return this->fileName;
}

CVSRepo::~CVSRepo() = default;

