
#include<utility>
#include<sstream>
#include <string>
#include<vector>
#include "Trench.h"

using namespace std;

Trench::Trench(int size, std::string colour, int price, int quantity, std::string photograph) {
	this->size = size;
	this->colour = colour;
	this->price = price;
	this->quantity = quantity;
	this->photograph = photograph;
}

Trench::~Trench() {

}

int Trench::getSize() const {
	return this->size;
}

string Trench::getColour() const {
	return this->colour;

}

int Trench::getPrice() const {
	return this->price;
}

int Trench::getQuantity() const {
	return this->quantity;
}

string Trench::getPhotograph() const {
	return this->photograph;
}

string Trench::toString() const {
	auto strSize = std::to_string(this->size);
	auto strPrice = std::to_string(this->price);
	auto strQuantity = std::to_string(this->quantity);
	//return "Size: " + strSize + " Colour: " + this->colour + " Price: " + strPrice + " Quantity: " + strQuantity + " Photo: " + this->photograph;
	return "Size: " + strSize + "\nColour: " + this->colour + "\nPrice: " + strPrice + "\nQuantity: " + strQuantity + "\nPhoto: " + this->photograph;
}


std::vector<std::string> tokenize(const std::string& str, char delimiter) {
	std::vector<std::string> result;
	std::stringstream ss(str);
	std::string token;
	while (getline(ss, token, delimiter))
		result.push_back(token);

	return result;
}



bool Trench::operator==(const Trench& trenchToCheck) const
{
	return this->colour == trenchToCheck.colour;
}


//size, colour, price, quantity, photo
std::istream& operator>>(std::istream& inputStream, Trench& trench)
{
	std::string line;
	std::getline(inputStream, line);
	std::vector<std::string> tokens;
	if (line.empty())
		return inputStream;
	tokens = tokenize(line, ',');
	trench.size = std::stoi(tokens[0]);
	trench.colour = tokens[1];
	trench.price = std::stoi(tokens[2]);
	trench.quantity = std::stoi(tokens[3]);
	trench.photograph = tokens[4];
	return inputStream;
}


std::ostream& operator<<(std::ostream& outputStream, const Trench& trenchOutput)
{
	outputStream << std::to_string(trenchOutput.size) << "," << trenchOutput.colour << "," << std::to_string(trenchOutput.price) << "," << std::to_string(trenchOutput.quantity) << "," << trenchOutput.photograph;
	return outputStream;
}
