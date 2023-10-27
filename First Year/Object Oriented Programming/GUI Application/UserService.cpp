#include "UserService.h"
#include "CVSRepo.h"
#include "HTMLRepo.h"

UserService::UserService(Repo& repo1, UserRepo* userrepo1) : repo(repo1) {
	this->userRepo = userrepo1;
}


UserService::UserService(Repo& repo1) : repo(repo1) {}


std::vector<Trench> UserService::getAllUserService()
{
	if (this->userRepo->getAllUserRepo().empty()) {
		std::string error;
		error += std::string("The basket is empty!");
		if (!error.empty())
			throw UserException(error);
	}

	return this->userRepo->getAllUserRepo();
}

unsigned int UserService::getNrElemsUserService()
{
	if (this->userRepo->getAllUserRepo().empty()) {
		std::string error;
		error += std::string("The basket is empty!");
		if (!error.empty())
			throw UserException(error);
	}
	return this->userRepo->getNrElems();
}

unsigned int UserService::getCapUserService()
{
	return this->userRepo->getCap();
}

void UserService::addUserService(const Trench& trench)
{
	this->userRepo->addUserRepo(trench);
	
}

void UserService::repoType(const std::string& fileType)
{
	if (fileType == "cvs") {
		std::vector<Trench> userVector;
		std::string userFile = R"("C:\Users\Teo\source\repos\A7\file.cvs")";
		auto* repo = new CVSRepo{ userVector, userFile };
		this->userRepo = repo;
	}
	else if (fileType == "html") {
		std::vector<Trench> userVector;
		std::string userFile = R"("C:\Users\Teo\source\repos\A7\file.html")";
		auto* repo = new HTMLRepo(userVector, userFile);
		this->userRepo = repo;
	}
	else {
		std::string error;
		error += std::string("The filename is invalid!");
		if (!error.empty())
			throw UserException(error);
	}

}

std::string& UserService::getFileService()
{
	return this->userRepo->getFileName();
}

UserService::~UserService() = default;
