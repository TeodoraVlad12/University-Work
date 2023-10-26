#pragma once
//DO NOT INCLUDE LISTITERATOR

//DO NOT CHANGE THIS PART
typedef int TElem;

struct NodDLL {
    TElem info;
    NodDLL* next;
    NodDLL* prev;
    int index;
    NodDLL(TElem info, NodDLL* prev, NodDLL* next, int index) : info{ info }, next{ next }, prev{ prev }, index{ index } {};
};

typedef NodDLL* Pnode;
#define NULL_TELEM -11111
class ListIterator;

//VECHI


class IndexedList {
private:
  

    Pnode head;
    Pnode tail;
    //int index;
    int numberOfElements;
	
	//DO NOT CHANGE THIS PART
    friend class ListIterator;    
public:
    // constructor
    IndexedList ();

    void printList();

    // returns the number of elements from the list
    int size() const;

    // checks if the list is empty
    bool isEmpty() const;

    // returns an element from a position
    //throws exception if the position is not valid
    TElem getElement(int pos) const;

    // modifies an element from a given position
	//returns the old value from the position
    //throws an exception if the position is not valid
    TElem setElement(int pos, TElem e);

    // adds an element to the end of the list
    void addToEnd(TElem e);

    // adds an element to a given position
    //throws an exception if the position is not valid
    void addToPosition(int pos, TElem e);

    // removes an element from a given position
	//returns the removed element
    //throws an exception if the position is not valid
    TElem remove(int pos);

    void removeBetween(int start, int end);

    // searches for an element and returns the first position where the element appears or -1 if the element is not in the list
    int search(TElem e)  const;

    // returns an iterator set to the first element of the list or invalid if the list is empty
    ListIterator iterator() const;


    //destructor
    ~IndexedList();

};
