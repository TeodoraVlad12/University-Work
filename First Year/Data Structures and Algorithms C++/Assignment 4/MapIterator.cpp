#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;


MapIterator::MapIterator(const Map& d) : map(d), head(nullptr), currentNode(nullptr)
{
    // Find the first non-empty slot in the hash table
    int index = 0;
    while (index < map.hashTable.m && map.hashTable.table[index] == nullptr)
    {
        index++;
    }

    if (index < map.hashTable.m)
    {
        head = map.hashTable.table[index];
        currentNode = head;
    }
}
//BC: theta(1) if the first non-empty slot is at the position 0
//WC: O(m) if the first non-empty slot is at the last position => total complexity: O(m)


void MapIterator::first() {
    int index = 0;
    while (index < map.hashTable.m && map.hashTable.table[index] == nullptr)
    {
        index++;
    }

    if (index < map.hashTable.m)
    {
        head = map.hashTable.table[index];
        currentNode = head;
    }
    else
    {
        head = nullptr;
        currentNode = nullptr;
    }
}
//BC: theta(1) if the first non-empty slot is at the position 0
//WC: O(m) if the first non-empty slot is at the last position => total complexity: O(m)


void MapIterator::next() {
	if (!valid())
		throw std::exception();

    // Check if there is a next node in the current index
    if (currentNode->next != nullptr) {
        currentNode = currentNode->next;
    }
    else {
        // Find the next non-empty index in the hash table
        int currentIndex = map.hashTable.hashFunction(currentNode->element.first);
        int nextIndex = currentIndex + 1;

        while (nextIndex < map.hashTable.m && map.hashTable.table[nextIndex] == nullptr) {
            nextIndex++;
        }

        // Move to the next non-empty index if found, otherwise set currentNode to nullptr
        if (nextIndex < map.hashTable.m) {
            currentNode = map.hashTable.table[nextIndex];
        }
        else {
            currentNode = nullptr;
        }
    }
}
//BC: theta(1) - if there is still a node in the current index
//WC: O(m) if the next non-empty index is at the end => total complexity: O(m)


TElem MapIterator::getCurrent() {
	if (!valid())
		throw std::exception();

	return currentNode->element;
}
//theta(1)


bool MapIterator::valid() const {
	return currentNode != nullptr;
}
//theta(1)








