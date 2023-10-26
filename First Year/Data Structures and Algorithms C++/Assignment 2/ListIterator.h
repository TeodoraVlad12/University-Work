#pragma once
#include "IndexedList.h"


//VECHI

class ListIterator{
    //DO NOT CHANGE THIS PART
	friend class IndexedList;
private:

    ListIterator(const IndexedList& lista);

	const IndexedList& list;

    Pnode currentElement;
    

public:
    void first();
    void next();
    bool valid() const;
    TElem getCurrent() const;

};

