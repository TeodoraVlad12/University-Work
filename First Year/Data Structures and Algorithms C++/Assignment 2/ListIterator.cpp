#include "ListIterator.h"
#include "IndexedList.h"
#include <exception>

using namespace std;




ListIterator::ListIterator(const IndexedList& lista) : list(lista){
    currentElement = lista.head;
}
//theta(1)


void ListIterator::first(){
    currentElement = list.head;
}
//theta(1)


void ListIterator::next() {
    if (currentElement == nullptr) throw exception();
    currentElement = currentElement->next;
}
//theta(1)


bool ListIterator::valid() const{
  
    return currentElement != nullptr;
}
//theta(1)


TElem ListIterator::getCurrent() const{
    if (currentElement == nullptr)
        throw exception();

    return currentElement->info;
}
//theta(1)
