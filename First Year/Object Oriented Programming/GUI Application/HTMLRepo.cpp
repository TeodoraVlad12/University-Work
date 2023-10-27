#include<fstream>
#include "HTMLRepo.h"

HTMLRepo::HTMLRepo(const std::vector<Trench>& basket, const std::string& fileName)
{
	this->basket = basket;
	this->fileName = fileName;
}

std::vector<Trench>& HTMLRepo::getAllUserRepo()
{
	return this->basket;
}

unsigned int HTMLRepo::getNrElems()
{
	return this->basket.size();
}

unsigned int HTMLRepo::getCap()
{
	return this->basket.capacity();
}

void HTMLRepo::addUserRepo(const Trench& trench)
{
	this->basket.push_back(trench);
	this->writeToFile();
}

void HTMLRepo::writeToFile()
{
	std::ofstream fout("file.html");
	fout << "<!DOCTYPE html>\n<html><head><title>Basket</title></head><body>\n";
	fout << "<table border=\"1\">\n";
	fout << "<tr><td>Size</td><td>Colour</td><td>Price</td><td>Quantity</td><td>Photo</td></tr>\n";
	for (const Trench& trench : this->basket) {
		fout << "<tr><td>" << std::to_string(trench.getSize() )<< "</td>"
			<< "<td>" << trench.getColour() << "</td>"
			<< "<td>" << std::to_string(trench.getPrice()) << "</td>"
			<< "<td>" << std::to_string(trench.getQuantity()) << "</td>"
			<< "<td><a href=\"" << trench.getPhotograph() << "\">" << trench.getPhotograph() << "</a></td>" << '\n';
	}
	fout << "</table></body></html>";
	fout.close();

	/*remove("file.html");
	std::ofstream fout("file.html", std::ios::app);
	fout << "<!DOCTYPE html>\n<html><head><title>Basket</title></head><body>\n";
	fout << "<table border=\"1\">\n";
	fout << "<tr><td>Size</td><td>Colour</td><td>Price</td><td>Quantity</td><td>Photo</td></tr>\n";
	for (const Trench& trench : this->basket) {
		fout << "<tr><td>" << trench.getColour() << "</td>"
			<< "<td>" << std::to_string(trench.getPrice()) << "</td>"
			<< "<td>" << std::to_string(trench.getQuantity()) << "</td>"
			<< "<td><a href=\"" << trench.getPhotograph() << "\">" << trench.getPhotograph() << "</a></td>" << '\n';
	}
	fout << "</table></body></html>";
	fout.close();*/
		
}

std::string& HTMLRepo::getFileName()
{
	return this->fileName;
}

HTMLRepo::~HTMLRepo() = default;
