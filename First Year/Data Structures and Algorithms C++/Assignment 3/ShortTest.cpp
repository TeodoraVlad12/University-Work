#include "ShortTest.h"
#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <assert.h>
#include<iostream>
using namespace std;

bool relation1(TComp e1, TComp e2) {
	return e1 <= e2;
}

bool relationn3(TComp r1, TComp r2) {
	return r1 >= r2;
}

void testAll() {
	SortedBag sb(relationn3);
	sb.add(5);

	sb.show_elems();
	sb.show_list();
	
	sb.add(6);

	sb.show_elems();
	sb.show_list();
	
	sb.add(0);
	sb.show_elems();
	sb.show_list();
	sb.add(5);
	sb.show_elems();
	sb.show_list();
	sb.add(10);
	sb.show_elems();
	sb.show_list();
	sb.add(8);


	sb.show_elems();
	sb.show_list();



	assert(sb.size() == 6);

	assert(sb.nrOccurrences(5) == 2);

	assert(sb.remove(5) == true);
	assert(sb.size() == 5);



	assert(sb.search(6) == true);
	assert(sb.isEmpty() == false);

	SortedBagIterator it = sb.iterator();
	assert(it.valid() == true);
	while (it.valid()) {
		it.getCurrent();
		it.next();
	}
	assert(it.valid() == false);
	it.first();
	assert(it.valid() == true);

}

