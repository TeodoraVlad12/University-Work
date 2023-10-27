#pragma once
#include <string>


//Each Trench Coat has a size, 
//						a colour, 
//						a price, 
//						a quantity and 
//						a photograph


class Trench {
private:
	int size;
	std::string colour;
	int price;
	int quantity;
	std::string photograph;

public:
	explicit Trench(int size = 0, std::string colour = "empty", int price = 0, int quantity = 0, std::string photograph = "empty");
	~Trench();

	int getSize() const;
	std::string getColour() const;
	int getPrice() const;
	int getQuantity() const;
	std::string getPhotograph() const;

	std::string toString() const;

	bool operator==(const Trench& trenchToCheck) const;

	friend std::istream& operator>>(std::istream& inputStream, Trench& trench);

	friend std::ostream& operator<<(std::ostream& outputStream, const Trench& trenchOutput);


};