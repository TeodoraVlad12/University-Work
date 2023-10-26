#include "Map.h"
#include "MapIterator.h"

Map::Map() {
	this->hashTable.m = 10;
	this->hashTable.table = new Node * [this->hashTable.m];
	for (int i = 0; i < this->hashTable.m; i++) {
		this->hashTable.table[i] = nullptr;
	}
	this->sizeOfMap = 0;
}
//theta(m)


TValue Map::mostFrequent() const
{
	int lengthArray = 0;
	TValue val = NULL_TVALUE;
	int freq = 0;
	std::pair<TValue, int>* elem;
	elem = new std::pair<TValue, int>[this->sizeOfMap];

	for (int i = 0; i < this->sizeOfMap; i++)
	{
		elem[i].first = 0;
		elem[i].second = 0;
	}
	

	if (this->isEmpty())
		return NULL_TVALUE;
	
	else {
		
		int index = 0;
		int indexArray = 0;

		

		while (index < this->hashTable.m)
		{
			Node* currentNode = this->hashTable.table[index];

			while (currentNode != nullptr) {
				int ok = 0;
				for (int i = 0; i < indexArray; i++) {
					if (elem[i].first == currentNode->element.second) {
						elem[i].second++;
						ok = 1;
					}
				}
				if (ok == 0) {
					elem[indexArray++].first = currentNode->element.second;
					elem[indexArray++].second = 1;
				}
				currentNode = currentNode->next;
			}
			index++;
		}
		lengthArray = indexArray;
	}
	for (int i = 0; i < lengthArray; i++) {
		if (val == NULL_TVALUE) {
			val = elem[i].first;
			freq = elem[i].second;

		}
		else
			if (elem[i].second > freq) {
				val = elem[i].first;
				freq = elem[i].second;
			}

	}
	return val;
}
//O(m+alpha*n)

Map::~Map() {
	for (int i = 0; i < this->hashTable.m; ++i) {
		auto currentLinkedList = this->hashTable.table[i];
		while (currentLinkedList != nullptr) {
			auto prev = currentLinkedList;
			currentLinkedList = currentLinkedList->next;
			delete prev;
		}
	}
	delete[]this->hashTable.table;
}
//O(m+n)


TValue Map::add(TKey c, TValue v) {
	// resize
	if ((float)this->size() / (float)this->hashTable.m >= 0.75) {
		resize(2 * this->hashTable.m);
	}

	TValue valueToReturn = NULL_TVALUE;

	//we have to search for the key and replace it if we find it

	int index = this->hashTable.hashFunction(c);
	Node* currentNode = this->hashTable.table[index];

	// we stop either at the end or at the key
	while (currentNode != nullptr)  {
		if (currentNode->element.first == c) {
			valueToReturn = currentNode->element.second;
			currentNode->element.second = v;
		}
		currentNode = currentNode->next;
	}

	if (valueToReturn == NULL_TVALUE) { //if we didn't find the key and we need to add a new pair

		int newIndex = this->hashTable.hashFunction(c);
		Node* newNode = new Node;
		newNode->element.first = c;
		newNode->element.second = v;

		// CASE: if list is empty just add it
		if (this->hashTable.table[newIndex] == nullptr) {
			newNode->next = nullptr;
			this->hashTable.table[newIndex] = newNode;
			this->sizeOfMap++;
			return NULL_TVALUE;
		}


		//we can add at the beginning
		newNode->next = this->hashTable.table[newIndex];
		this->hashTable.table[newIndex] = newNode;
		this->sizeOfMap++;




		//auto currentNode = this->hashTable.table[newIndex]; // first node from new table
		//Node* prevNode = currentNode;  // save the previous node from while

		//// we want to reach the end of the sll 
		//while (currentNode != nullptr) {
		//	prevNode = currentNode;
		//	currentNode = currentNode->next;
		//}

		//// add at the end of list
		//newNode->next = nullptr;
		//prevNode->next = newNode;  // new tail
		//this->sizeOfMap++;
	}
	return valueToReturn;
}
//BC: theta(1) the key already exists and is the first in the sll and we just need to replace its value or if the list is empty
//WC: O( m + alpha*n ) if we need resizing



TValue Map::search(TKey c) const {
	int index = this->hashTable.hashFunction(c);
	Node* currentNode = this->hashTable.table[index];

	// we stop either at the end or at the key
	while (currentNode != nullptr && currentNode->element.first != c) {
		currentNode = currentNode->next;
	}

	if (currentNode == nullptr)  //the key wasn't found
		return NULL_TVALUE;

	else
		return currentNode->element.second;
}
//BC: theta(1) if the key is the first element in the sll
//WC: O(nrOfElementsInTheSLL) if the key isn't found for example => total complexity= O(nrOfElementsInTheSLL)


TValue Map::remove(TKey c) {
	int newIndex = this->hashTable.hashFunction(c);

	// case 1: list is empty
	if (hashTable.table[newIndex] == nullptr)
		return NULL_TVALUE;

	// case 2: remove from beginning
	auto headOfList = hashTable.table[newIndex];
	if (headOfList->element.first == c ) {
		TValue valueToReturn = headOfList->element.second;
		hashTable.table[newIndex] = headOfList->next;
		this->sizeOfMap--;
		return valueToReturn;
	}

	auto currentNode = headOfList->next;
	auto prevNode = headOfList;

	if (currentNode != nullptr) {
		while (currentNode->next != nullptr) {
			if (currentNode->element.first == c) { // case 3: remove from middle
				TValue valueToReturn = currentNode->element.second;
				prevNode->next = currentNode->next;
				this->sizeOfMap--;
				return valueToReturn;
			}
			currentNode = currentNode->next;
		}
		// case 4: remove from end
		if (currentNode->element.first == c ) {
			TValue valueToReturn = currentNode->element.second;
			prevNode->next = nullptr;
			this->sizeOfMap--;
			return valueToReturn;
		}
	}
	else { // special case for second element as the last element
		if (prevNode->element.first == c ) {
			TValue valueToReturn = prevNode->element.second;
			prevNode->next = nullptr;
			this->sizeOfMap--;
			return valueToReturn;

		}
	}

	return NULL_TVALUE;

}
//BC: theta(1) - is empty
//WC: O(nrOfElementsInTheSLL) if we remove from the end of the SLL => total complexity= O(nrOfElementsInTheSLL)



int Map::size() const {
	return this->sizeOfMap;
}
//theta(1)


bool Map::isEmpty() const {

	return (this->sizeOfMap == 0);
}
//theta(1)


MapIterator Map::iterator() const {
	return MapIterator(*this);
}
//theta(1)


int Map::HashTableMap::hashFunction(TKey c) const {
	return abs(c % (this->m));
}
//theta(1)


void Map::resize(int newCapacity) {
	Node** newTable = new Node * [newCapacity];
	for (int i = 0; i < newCapacity; i++)
		newTable[i] = nullptr;

	// modify m
	int oldM = this->hashTable.m;
	this->hashTable.m = newCapacity;

	// copy the old table into the new table
	for (int i = 0; i < oldM; i++) {
		auto currentLinkedList = this->hashTable.table[i];
		while (currentLinkedList != nullptr) {
			auto currentNode = currentLinkedList;

			// find index of newNode
			int newIndex = this->hashTable.hashFunction(currentNode->element.first);
			Node* newNode = new Node;
			newNode->element = currentNode->element;

			// CASE1: if list is empty just add it
			if (newTable[newIndex] == nullptr) {
				newNode->next = nullptr;
				newTable[newIndex] = newNode;
				currentLinkedList = currentLinkedList->next;
				delete currentNode;
				continue;
			}


			// CASE: we add at the beginning of the list
			
				newNode->next = newTable[newIndex];
				newTable[newIndex] = newNode;
				currentLinkedList = currentLinkedList->next;
				delete currentNode;
				continue;
			


		}
	}
	delete[]this->hashTable.table;
	this->hashTable.table = newTable;
}
//O(m + alpha*n)

