
#pragma once
#include "UserRepo.h"

class HTMLRepo : public UserRepo {
public:
	HTMLRepo(const std::vector<Trench>& basket, const std::string& fileName);

	std::vector<Trench>& getAllUserRepo() override;
	
	unsigned int getNrElems() override;

	unsigned int getCap() override;

	void addUserRepo(const Trench& trench) override;

	void writeToFile() override;

	std::string& getFileName() override;

	~HTMLRepo();
};


