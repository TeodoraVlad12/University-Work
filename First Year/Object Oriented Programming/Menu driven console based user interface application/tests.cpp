#include "tests.h"
#include <cassert>
#include<iostream>

using namespace std;

void testDomain() {
	Trench newTr(36, "green_green", 100, 100, "link");
	assert(newTr.getColour() == "green_green");
	assert(newTr.getSize() == 36);
	assert(newTr.getPrice() == 100);
	assert(newTr.getQuantity() == 100);
	assert(newTr.getPhotograph() == "link");
	assert(newTr.toString() == "Size: 36\nColour: green_green\nPrice: 100\nQuantity: 100\nPhoto: link");
	
}


//void testRepo() {
//	vector<Trench> repoArray;
//	auto* repo = new Repo(repoArray);
//	assert(repo->getNrElements() == 0);
//	Trench newTr(36, "green_green", 100, 100, "link");
//	repo->addRepo(newTr);
//	Trench newTr2(36, "green_two", 100, 100, "link");
//	repo->addRepo(newTr2);
//	assert(repo->getNrElements() == 2);
//	repo->deleteRepo(0);
//	assert(repo->getNrElements() == 1);
//	Trench newTr3(36, "green_three", 100, 100, "link");
//	repo->addRepo(newTr3);
//	Trench newTr4(36, "green_four", 100, 100, "link");
//	repo->addRepo(newTr4);
//	Trench newTr5(36, "green_five", 100, 100, "link");
//	repo->addRepo(newTr5);
//	Trench newTr6(36, "green_six", 100, 100, "link");
//	repo->addRepo(newTr6);
//	repo->~Repo();
//
//	vector<Trench> repoArray2;
//	auto* repo2 = new Repo(repoArray2);
//	Trench newTr7(36, "green_green", 100, 100, "link");
//	repo2->addRepo(newTr7);
//	Trench newTr8(36, "green_two", 100, 100, "link");
//	repo2->addRepo(newTr8);
//	assert(repo2->getAllRepo()[0].getPrice() == 100);
//	assert(repo2->findByColour("green_green") == 0);
//	assert(repo2->findByColour("black") == -1);
//	Trench newTr9(40, "green_four", 200, 200, "link");
//	repo2->updateRepo(newTr9, 0);
//	assert(repo2->getAllRepo()[0].getPrice() == 200);
//	repo2->~Repo();
//
//	vector<Trench> repoArray3;
//	auto* repo3 = new Repo(repoArray2);
//	repo3->generateTrenchList();
//	assert(repo3->getNrElements() == 10);
//	repo3->~Repo();
//}
//
//void testService() {
//		vector<Trench> repoArray;
//		auto* repo = new Repo(repoArray);
//		auto* service = new Service(repo);
//		service->addService(36, "blue", 450, 20, "link");
//		assert(service->getAllService()[0].getColour() == "blue");
//		service->addService(38, "red", 444, 22, "link");
//		service->addService(32, "green", 555, 11, "link");
//		assert(service->getNrElementsService() == 3);
//		assert(service->addService(38, "red", 400, 10, "link") == 0);
//		assert(service->addService(44, "orange", 44, 33, "link") == 1);
//		assert(service->deleteService("pink") == 0);
//		assert(service->deleteService("blue") == 1);
//		assert(service->getNrElementsService() == 3);
//		assert(service->updateService(111, "orange", 111, 111, "new_link") == 1);
//		assert(service->getAllService()[2].getPhotograph() == "new_link");
//		assert(service->updateService(111, "colour", 111, 111, "new_link") == 0);
//		service->~Service();
//
//		}
//
//
//
//
//
//
//
void testAll() {
		testDomain();
//		testRepo();
//		testService();
		cout << "The tests are ok!:)";
}