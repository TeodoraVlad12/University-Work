#pragma once
#include "repo.h"


class Service{
private:
	Repo& repo;
public:
	Service(Repo& repo);
	~Service();
	Trench* getAllService();
	int getNrElementsService();
	//int getCapacityService();
	int addService(int size, std::string colour, int price, int quantity, std::string photograph);
	int deleteService(std::string colour);

//Updates the information about a trench of a given colour
//Input: the updated information about the trench
//Output: return 1 if the update was successful or 0 if the trench does not exist
	int updateService(int newSize, std::string colour, int newPrice, int newQuantity, std::string newPhotograph);
};

