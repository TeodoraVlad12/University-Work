#pragma once

#include "SortedMultiMap.h"

class SMMIterator {
    friend class SortedMultiMap;
private:
    // DO NOT CHANGE THIS PART
    const SortedMultiMap& map;
    SMMIterator(const SortedMultiMap& map);

    // TODO - Representation

    BSTNode* currentNode;
    int* stack;
    int stackTop;
    int currentElement;

public:
    void first();
    void next();
    bool valid() const;
    TElem getCurrent() const;
};






//#pragma once
//
//#include "SortedMultiMap.h"
//#include <vector>
//#include <algorithm>
//#include <stack>
//
//class SMMIterator {
//	friend class SortedMultiMap;
//private:
//	//DO NOT CHANGE THIS PART
//	const SortedMultiMap& map;
//	SMMIterator(const SortedMultiMap& map);
//
//	//TODO - Representation
//
//	BSTNode* currentNode;
//	std::stack<BSTNode> stack;
//	int currentElement;
//
//public:
//	void first();
//	void next();
//	bool valid() const;
//	TElem getCurrent() const;
//};
//
