#pragma once
#include <vector>
#include "Trench.h"

using std::vector;

class Repo {
protected:
	vector<Trench> trenchsList;
	std::string fileName;

public:
	void loadFromFile();

	 void writeToFile();

	explicit Repo(vector<Trench> repoArray, std::string& file_name);

	void initialiseRepo();

	~Repo();
	 Trench* getAllRepo();
	 int getNrElements();
	//int getCap();

//Returns the position of the trench of a given colour
//Input: a string representing the colour
//Output: the position or -1 if such a trench does not exist
	int findByColour(std::string colour);

    void addRepo(const Trench& trench);
	void deleteRepo(int index);
	void updateRepo(const Trench& newTrench, int index);
	void generateTrenchList();

};

class RepoException : public std::exception {
private:
	std::string message;
public:
	explicit RepoException(std::string& _message);

	const char* what() const noexcept override;

};