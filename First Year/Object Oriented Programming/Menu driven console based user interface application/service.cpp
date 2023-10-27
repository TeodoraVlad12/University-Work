#include "service.h"

Service::Service(Repo& repo): repo(repo) {
	
}

Service::~Service() {

}

Trench* Service::getAllService() {
	return this->repo.getAllRepo();
}

int Service::getNrElementsService() {
	return this->repo.getNrElements();
}

//int Service::getCapacityService() {
//	return this->repo->getCap();
//}

int Service::addService(int size, std::string colour, int price, int quantity, std::string photograph) {
	int result = this->repo.findByColour(colour);
	if (result == -1) {
		this->repo.addRepo(Trench(size, colour, price, quantity, photograph));
		return 1;}
	else
		return 0;}

int Service::deleteService(std::string colour) {
	int result = this->repo.findByColour(colour);
	if (result == -1)
		return 0;
	else {
		this->repo.deleteRepo(result);
		return 1;}} 

int Service::updateService(int newSize, std::string colour, int newPrice, int newQuantity, std::string newPhotograph) {
	int result = this->repo.findByColour(colour);
	if (result == -1) {
		return 0;}
	else {
		Trench newTrench = Trench(newSize, colour, newPrice, newQuantity, newPhotograph);
		this->repo.updateRepo(newTrench, result);
		return 1;}}
