#include "SMMIterator.h"
#include "SortedMultiMap.h"

SMMIterator::SMMIterator(const SortedMultiMap& map) : map(map) {
    this->stack = new int[this->map.capacity];
    this->stackTop = -1;

    int node = this->map.root;
    while (node != -1) {
        this->stack[++this->stackTop] = node;
        node = this->map.pairs[node].left;    //the indices of the nodes are stored in the stack array
    }

    if (this->stackTop != -1)
        this->currentNode = &(this->map.pairs[this->stack[this->stackTop]]);
    else
        this->currentNode = nullptr;

    currentElement = 0;
}
/// O(h + log n)  (h=height)  h from traversing the left part and log n from pushing elements onto the stack
/// WC: O(n)  the height can be the number of elements in the tree =>total complexity: O(n)


void SMMIterator::first() {
    this->stackTop = -1;

    int node = this->map.root;
    while (node != -1) {       //we rebuild the stack by traversing the left branches of the BST nodes starting from the root node
        this->stack[++this->stackTop] = node;
        node = this->map.pairs[node].left;
    }

    if (this->stackTop != -1)
        this->currentNode = &(this->map.pairs[this->stack[this->stackTop]]);
    else
        this->currentNode = nullptr;

    currentElement = 0;
}
/// O(h + log n)  (h=height)  h from traversing the left part and log n from pushing elements onto the stack
/// WC: O(n)  the height can be the number of elements in the tree =>total complexity: O(n)




void SMMIterator::next() {
    if (!valid())
        throw std::exception();

    int index;
    for (int i = 0; i < map.capacity; i++) {
        //find the index of the current node in the pairs array of the sorted multi - map
        if (currentNode->information.first == map.pairs[i].information.first) {
            index = i;
            break;
        }
    }

    if (currentElement < map.lengths[index] - 1) {
        currentElement++;     //increment currentElement to move to the next element
    }
    else {            //we have traversed all elements in the current node, and we need to move to the next node
        currentElement = 0;
        this->stackTop--;

        if (this->currentNode->right == -1) {
            if (this->stackTop != -1)
                this->currentNode = &(this->map.pairs[this->stack[this->stackTop]]);
            else
                this->currentNode = nullptr;
        }
        else {
            int node = this->currentNode->right;
            while (node != -1) {
                this->stack[++this->stackTop] = node;
                node = this->map.pairs[node].left;
            }
            if (this->stackTop != -1)
                this->currentNode = &(this->map.pairs[this->stack[this->stackTop]]);
            else
                this->currentNode = nullptr;
        }
    }
}

/// O(n)

bool SMMIterator::valid() const {
    return this->currentNode != nullptr;
}
/// Theta(1)



TElem SMMIterator::getCurrent() const {
    if (valid()) {
        TElem elem;
        elem.first = this->currentNode->information.first;
        elem.second = this->currentNode->information.second[currentElement];
        return elem;
    }
    else {
        throw std::exception();
    }
}
/// Theta(1)










//#include "SMMIterator.h"
//#include "SortedMultiMap.h"
//#include <iostream>
//SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d) {
//
//	int node = this->map.root;
//	while (node != -1)
//	{
//		this->stack.push(this->map.pairs[node]);
//		node = this->map.pairs[node].left;
//	}
//
//	if (!this->stack.empty())
//		this->currentNode = &(this->stack.top());
//	else
//		this->currentNode = nullptr;
//
//	currentElement = 0;
//
//}
//
//void SMMIterator::first() {
//
//	while (!this->stack.empty())
//		this->stack.pop();
//	int node = this->map.root;
//	while (node != -1)
//	{
//		this->stack.push(this->map.pairs[node]);
//		node = this->map.pairs[node].left;
//	}
//
//	if (!this->stack.empty())
//		this->currentNode = &(this->stack.top());
//	else
//		this->currentNode = nullptr;
//	currentElement = 0;
//}
//
//void SMMIterator::next() {
//	if (!valid())
//		throw std::exception();
//
//	int index;
//	for (int i = 0; i < map.capacity; i++)
//	{
//		if (currentNode->information.first == map.pairs[i].information.first)
//		{
//			index = i;
//			break;
//		}
//	}
//
//	if (currentElement < map.lengths[index] - 1)
//	{
//		currentElement++;
//		return;
//	}
//	else
//	{
//		currentElement = 0;
//		this->stack.pop();
//
//		if (currentNode->right == -1)
//		{
//			if (!this->stack.empty())
//				this->currentNode = &(this->stack.top());
//			else
//				this->currentNode = nullptr;
//			return;
//		}
//		else
//		{
//			int node = currentNode->right;
//			while (node != -1)
//			{
//				this->stack.push(this->map.pairs[node]);
//				node = this->map.pairs[node].left;
//			}
//			if (!this->stack.empty())
//				this->currentNode = &(this->stack.top());
//			else
//				this->currentNode = nullptr;
//			return;
//		}
//	}
//
//
//
//}
//
//bool SMMIterator::valid() const {
//
//	return this->currentNode != nullptr;
//
//}
//
//TElem SMMIterator::getCurrent() const {
//	if (valid())
//	{
//		TElem elem;
//		elem.first = this->currentNode->information.first;
//		elem.second = this->currentNode->information.second[currentElement];
//		return elem;
//	}
//	else
//		throw std::exception();
//}
//
//
