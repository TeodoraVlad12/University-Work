#pragma once
#include <vector>
#include "Trench.h"

class UserRepo {
protected:
	std::vector<Trench> basket;
	std::string fileName;

public:
	explicit UserRepo(std::vector<Trench>& basket);

	UserRepo();

	virtual std::vector<Trench>& getAllUserRepo() = 0;

	virtual unsigned int getNrElems() = 0;

	virtual unsigned int getCap() = 0;

	virtual void addUserRepo(const Trench& trench) = 0;

	virtual void writeToFile() = 0;

	virtual std::string& getFileName()=0;

	~UserRepo();



};


class UserException : public std::exception {
private:
	std::string message;

public:
	explicit UserException(std::string& _message);

	const char* what() const noexcept override;
};

