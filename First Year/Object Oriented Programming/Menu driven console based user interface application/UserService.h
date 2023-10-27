#pragma once
#include "repo.h"
#include "UserRepo.h"

class UserService {
private:
	Repo& repo;
	UserRepo* userRepo;

public:
	UserService(Repo& repo1, UserRepo* userrepo1);

	explicit UserService(Repo& repo1);

	std::vector<Trench> getAllUserService();

	unsigned int getNrElemsUserService();

	unsigned int getCapUserService();

	void addUserService(const Trench& trench);

	void repoType(const std::string& fileType);

	std::string& getFileService();

	~UserService();
};


