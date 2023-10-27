#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
#include <limits.h>
using namespace std;

SortedMultiMap::SortedMultiMap(Relation r)
{
	this->r = r;
	this->capacity = 10;
	this->dynamicArraySize = 10;
	this->numberOfPairs = 0;
	this->pairs = new BSTNode[this->capacity];
	lengths = new int[this->capacity];
	for (int i = 0; i < this->capacity; i++)
	{
		this->pairs[i].information.second = new TValue[this->dynamicArraySize];
		this->pairs[i].right = -1;
		this->pairs[i].left = -1;
		lengths[i] = 0;
	}
	this->root = -1;
}
/// Theta(capacity)

void SortedMultiMap::add(TKey c, TValue v)
{
	int foundKey = -1;
	for (int i = 0; i < this->capacity; i++)
	{
		if (lengths[i] != 0 && this->pairs[i].information.first == c)
		{
			foundKey = i;
			break;
		}

	}
	if (foundKey != -1)       //there already exists this key
	{
		if (this->lengths[foundKey] == this->dynamicArraySize)     //we need resizing
		{
			this->dynamicArraySize *= 2;
			for (int i = 0; i < this->capacity; i++)
			{

				TValue* newdata = new TValue[this->dynamicArraySize];
				for (int j = 0; j < this->lengths[i]; j++)
					newdata[j] = this->pairs[i].information.second[j];
				delete[] this->pairs[i].information.second;
				this->pairs[i].information.second = newdata;

			}
		}
		this->pairs[foundKey].information.second[this->lengths[foundKey]] = v;
		this->lengths[foundKey]++;
		return;
	}
	else if (foundKey == -1)          //the key doesn't exist yet
	{
		int empty = -1;
		for (int i = 0; i < this->capacity; i++)    //search for an empty spot
		{
			if (lengths[i] == 0)
			{
				empty = i;
				break;
			}
		}
		if (empty == -1)               //we need resizing
		{
			this->capacity *= 2;
			BSTNode* newdata = new BSTNode[this->capacity];
			int* newlengths = new int[this->capacity];

			for (int i = 0; i < this->capacity / 2; i++)
			{
				newdata[i] = this->pairs[i];
				newlengths[i] = this->lengths[i];
			}


			for (int i = this->capacity / 2; i < this->capacity; i++)
			{
				newdata[i].information.second = new TValue[this->dynamicArraySize];
				newdata[i].right = -1;
				newdata[i].left = -1;
				newlengths[i] = 0;
			}
			delete[] this->pairs;
			delete[] this->lengths;
			this->lengths = newlengths;
			this->pairs = newdata;
			empty = this->capacity / 2;
		}
		                                    //the key exists
		if (this->root == -1)
		{
			this->root = 0;
			this->numberOfPairs++;
			this->pairs[this->root].information.first = c;
			this->pairs[this->root].information.second[this->lengths[this->root]] = v;
			this->lengths[this->root]++;
			this->pairs[this->root].left = -1;
			this->pairs[this->root].right = -1;
		}
		else
		{
			int current = this->root;

			while (1)
			{
				if (this->r(c, this->pairs[current].information.first))
				{
					if (this->pairs[current].left == -1)
					{
						this->pairs[current].left = empty;

						this->numberOfPairs++;
						this->pairs[empty].information.second[this->lengths[empty]] = v;
						this->lengths[empty]++;
						this->pairs[empty].information.first = c;

						this->pairs[empty].left = -1;
						this->pairs[empty].right = -1;

						break;
					}
					else
					{
						current = this->pairs[current].left;
					}
				}
				else
				{
					if (this->pairs[current].right == -1)
					{
						this->pairs[current].right = empty;

						this->numberOfPairs++;
						this->pairs[empty].information.second[this->lengths[empty]] = v;
						this->lengths[empty]++;
						this->pairs[empty].information.first = c;

						this->pairs[empty].left = -1;
						this->pairs[empty].right = -1;
						break;
					}
					else
					{
						current = this->pairs[current].right;
					}
				}
			}

		}
	}
}

/// BestCase: Theta(1)  (when the key exists and it's on the first position)
/// WorstCase: O(2*capacity)  =>total complexity: O(2*capacity)

vector<TValue> SortedMultiMap::search(TKey c) const
{
	vector<TValue> result;
	for (int i = 0; i < this->capacity; i++)
	{
		if (lengths[i] != 0 && this->pairs[i].information.first == c)
		{
			for (int j = 0; j < lengths[i]; j++)
				result.push_back(this->pairs[i].information.second[j]);

		}
	}
	return result;
}

/// BestCase: Theta(capacity)
/// WorstCase: O(n^2)   => total complexity: O(n^2)

bool SortedMultiMap::remove(TKey c, TValue v)
{
	int foundKey = -1;
	for (int i = 0; i < this->capacity; i++)
	{
		if (lengths[i] != 0 && this->pairs[i].information.first == c)
		{
			foundKey = i;
			break;
		}

	}
	if (foundKey == -1)         //the key doesn't exist
		return false;

	int foundTElem = -1;
	for (int i = 0; i < this->lengths[foundKey]; i++)
	{
		if (this->pairs[foundKey].information.second[i] == v)
		{
			foundTElem = i;
			break;
		}
	}
	if (foundTElem == -1)    //the value associated with the key doesn't exist
		return false;

	for (int i = foundTElem; i < this->lengths[foundKey] - 1; i++)     //we remove the value from the array
	{
		this->pairs[foundKey].information.second[i] = this->pairs[foundKey].information.second[i + 1];
	}
	this->lengths[foundKey]--;


	if (lengths[foundKey] == 0)       //we need to remove the key because there are no values associated with it left
	{
		int current = this->root;
		int parent = -1;
		while (1)
		{
			if (this->pairs[current].information.first == c)
				break;
			if (this->r(c, this->pairs[current].information.first))
			{
				parent = current;
				current = this->pairs[current].left;
			}
			else
			{
				parent = current;
				current = this->pairs[current].right;
			}
		}

		if (this->pairs[current].left == -1 && this->pairs[current].right == -1)
		{
			if (parent == -1)
			{
				this->root = -1;
				this->numberOfPairs--;
				return true;
			}
			if (this->pairs[parent].left == current)
				this->pairs[parent].left = -1;
			else
				this->pairs[parent].right = -1;

			this->numberOfPairs--;
			return true;
		}
		else if (this->pairs[current].left == -1)
		{
			if (parent == -1)
				this->root = this->pairs[current].right;

			else
			{
				if (this->pairs[parent].left == current)
					this->pairs[parent].left = this->pairs[current].right;
				else
					this->pairs[parent].right = this->pairs[current].right;
			}

			this->numberOfPairs--;

			return true;
		}
		else if (this->pairs[current].right == -1)
		{
			if (parent == -1)
				this->root = this->pairs[current].left;
			{
				if (this->pairs[parent].left == current)
					this->pairs[parent].left = this->pairs[current].left;
				else
					this->pairs[parent].right = this->pairs[current].left;
			}

			this->numberOfPairs--;
			return true;
		}

		int min = this->pairs[current].right;
		int parentMin = current;
		while (this->pairs[min].left != -1)
		{
			parentMin = min;
			min = this->pairs[min].left;
		}


		this->pairs[current].information.first = this->pairs[min].information.first;
		this->pairs[current].information.second = this->pairs[min].information.second;
		this->pairs[current].left = this->pairs[min].left;
		this->pairs[current].right = this->pairs[min].right;

		if (this->pairs[parentMin].left == min)
			this->pairs[parentMin].left = this->pairs[min].right;
		else
			this->pairs[parentMin].right = this->pairs[min].right;

		this->lengths[current] = this->lengths[min];
		this->lengths[min] = 0;
		this->numberOfPairs--;
	}

	return true;

}


/// BestCase: Theta(1)    (when the key exists and is on the first positions and the array associated with it has only 2 elems
/// WorstCase: O(capacity)   => total complexity: O(capacity)

int SortedMultiMap::size() const
{

	int size = 0;
	for (int i = 0; i < this->capacity; i++)
		size += lengths[i];

	return size;
}

/// Theta(capacity)

bool SortedMultiMap::isEmpty() const
{
	return this->numberOfPairs == 0;
}

///Theta(1)

SMMIterator SortedMultiMap::iterator() const
{
	return SMMIterator(*this);
}
/// Theta(1)



void SortedMultiMap::replace(TKey k, TValue oldValue, TValue newValue)
{
	for (int i = 0; i < this->capacity; i++)
	{
		if (lengths[i] != 0 && this->pairs[i].information.first == k)
		{
			for (int j = 0; j < lengths[i]; j++)
				if (this->pairs[i].information.second[j] == oldValue)
					this->pairs[i].information.second[j] = newValue;

		}
	}
}
// BestCase: Theta(capacity)
/// WorstCase: O(n*nrValues)   => total complexity: O(n^2)




SortedMultiMap::~SortedMultiMap()
{
	delete[] this->pairs;
	delete[] this->lengths;
}
/// Theta(1)

