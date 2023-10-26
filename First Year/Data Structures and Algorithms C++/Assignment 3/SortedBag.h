#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

//DO NOT CHANGE THIS PART
#include <utility>

typedef int TComp;
typedef TComp TElem;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TCOMP -11111;

struct NodSLL {
	//int info;
	std::pair<TComp, int> info;
	NodSLL* next;
	//NodSLL(int info, NodSLL* next) : info{ info }, next{ next }{};
};


typedef NodSLL* Pnode;



class SortedBagIterator;

class SortedBag {
	friend class SortedBagIterator;

private:
	Pnode head;
	Pnode tail;
	TElem* elems;
	int capacity;
	Relation rel;
	
	int length;
	int unique;

	TElem maxi;
	TElem mini;

public:
	//constructor
	SortedBag(Relation r);

	//adds an element to the sorted bag
	void add(TComp e);

	//removes one occurence of an element from a sorted bag
	//returns true if an element was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an element appearch is the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an element in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the number of elements from the sorted bag
	int size() const;

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() const;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//int getFreq(TComp e) const;

	void show_elems();

	void show_list();

	TComp leastFrequent() const;

	//
	void testNewFunc();

	//destructor
	~SortedBag();
};