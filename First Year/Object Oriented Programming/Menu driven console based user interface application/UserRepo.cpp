#include <fstream>
#include "UserRepo.h"


UserException::UserException(std::string& _message) : message(_message) {}

const char* UserException::what() const noexcept {
	return message.c_str();
}

UserRepo::UserRepo() = default;

UserRepo::UserRepo(std::vector<Trench>& basket) {
	this->basket = basket;
}

UserRepo::~UserRepo() = default;

