#include <exception>
#include<iostream>
#include "IndexedList.h"
#include "ListIterator.h"
using namespace std;




IndexedList::IndexedList() {
    head = nullptr;
    tail = nullptr;
    numberOfElements = -1;
   
}
//theta(1)


void IndexedList::printList()
{
    Pnode currentNode = this->head;
    while (currentNode != nullptr) {
        cout << "(" << currentNode->index << ";" << currentNode->info << ") ";
        currentNode = currentNode->next;
    }
    cout << endl;
}

int IndexedList::size() const {
    if (this->numberOfElements == -1)
        return 0;
    else
        //return index + 1;
        return this->tail->index + 1;

}
//theta(1)


bool IndexedList::isEmpty() const {
    if (this->size() == 0)
        return true;
    else
	    return false;
}
//theta(1)


TElem IndexedList::getElement(int pos) const {
    if (pos > this->numberOfElements || pos < 0)
        throw exception();
    else {
        Pnode currentNode = this->head;
        while (currentNode != nullptr && currentNode->index < pos)
            currentNode = currentNode->next;

        return currentNode->info; 
    }
    
}
//BC: theta(1) when pos==0
//WC: theta(numberOfElements+1) when the position is the last position in the list => total complexity: O(numberOfElements+1)

TElem IndexedList::setElement(int pos, TElem e) {
    if (pos > this->numberOfElements || pos < 0)
        throw exception();
    else {
        Pnode curr = this->head;
        while (curr != nullptr && curr->index < pos)
            curr = curr->next;
        TElem old_value = curr->info;
        curr->info = e;


        return old_value;
        //return NULL_TELEM;
    }
}
//BC: theta(1) when pos==0
//WC: theta(numberOfElements+1) when the position is the last position in the list => total complexity: O(numberOfElements+1)


void IndexedList::addToEnd(TElem e) {
    Pnode newNode = new NodDLL(e, nullptr, nullptr, NULL_TELEM);
    if (isEmpty())    //if the list is empty the new node becomes the head
    {
        cout << "Case: No elements in linked list." << endl;
        head = newNode;
        tail = newNode;
 /*       head->next = tail;
        tail->prev = head;*/
        this->numberOfElements++;
        head->index = 0;
        tail->index = this->numberOfElements;
    }
    else {
        //since we need to add to end, we link to tail
        Pnode previousTail = this->tail;
        this->tail = newNode;
        previousTail->next = newNode;
        newNode->prev = previousTail;
        this->numberOfElements++;
        newNode->index = this->numberOfElements;

        //Pnode curr = this->head;
        //while (curr != nullptr && curr->index <= index)
        //    curr = curr->next;
        //curr->next = pn;
        //index++;
        //pn->index = index;
    }
    
}
//theta(1) because we just link to tal or just set the head if the list is empty


void IndexedList::addToPosition(int pos, TElem e) {
    if ( pos<0 || pos>this->numberOfElements)
        throw exception();
    Pnode newNode = new NodDLL(e, nullptr, nullptr, pos);
    if (pos == 0 && this->numberOfElements == -1)  //we are adding the first element
    {   
        head = newNode;
        tail = newNode;
 /*       head->next = tail;
        tail->prev = head;*/
        this->numberOfElements++;
        head->index = 0;
        tail->index = this->numberOfElements;
    }
    else 

    if (pos == 0 && this->numberOfElements != -1)
    {
        Pnode previousHead = this->head;
        this->head = newNode;
        newNode->next = previousHead;
        previousHead->prev = newNode;
        newNode->index = -1;

        Pnode currentNode = this->head;

        while (currentNode != nullptr) {
            currentNode->index++;
            //cout << currentNode->index << endl;
            currentNode = currentNode->next;
        }
        this->numberOfElements++;


    }
    
    else
    {
        Pnode currentNode = this->head;
        while (currentNode->index < pos)
            currentNode = currentNode->next;
        
        Pnode nodeBefore = currentNode->prev;
        Pnode nodeAfter = currentNode;
        nodeBefore->next = newNode;
        newNode->prev = nodeBefore;
        newNode->next = nodeAfter;
        nodeAfter->prev = newNode;
        newNode->index = pos;

        Pnode crtNode = nodeAfter;
        while (crtNode != nullptr) {
            crtNode->index++;
            crtNode = crtNode->next;
        }
        this->numberOfElements++;
        

    }
}
//BC: theta(1) if we are adding the first element
//WC: theta(numberOfElements+1) when the position is the last position in the list => total complexity: O(numberOfElements+1)


TElem IndexedList::remove(int pos) {
    if (pos<0 || pos>this->numberOfElements)
        throw exception();
    if (pos == 0 and this->numberOfElements == 0) {  //we have to empty the list
        TElem value_to_return = this->head->info;
        Pnode newNode = new NodDLL(NULL_TELEM, nullptr, nullptr, pos);
        //this->head = newNode;
        this->head = nullptr;
        this->numberOfElements = -1;
        return value_to_return;
    }
    else
    if (pos == 0)  //we have to change the head
    {
        TElem value_to_return = this->head->info;
        
        this->head->next->prev = nullptr;
        this->head = this->head->next;
        this->head->index = 0;
        this->numberOfElements--;
        
        Pnode curr = head->next;
        while (curr != nullptr)
            curr->index = curr->index - 1;
        return value_to_return;

    }
    else
        if (pos == this->numberOfElements)  //we have to change the tail
        {  
            TElem value_to_return = tail->info;
            this->tail = this->tail->prev;
            this->tail->next = nullptr;
            this->numberOfElements--;
            return value_to_return;
        }
        else {
            Pnode curr = this->head;
            while (curr->index < pos - 1)
                curr = curr->next;
            TElem value_to_return = curr->next->info;
            curr->next = curr->next->next;    //we set the deatils of the element before the one we want eliminated and the one after
            curr->next->prev = curr;
            curr->next->index = curr->next->index - 1;
            this->numberOfElements--;
            
            curr = curr->next->next;
            while (curr != nullptr)
            {
                curr->index = curr->index - 1;
                curr = curr->next;

            }
            return value_to_return;
        }
    

        
    //return NULL_TELEM;
}
//BC: theta(1) if the list has only one element and it needs to be eliminated or we just need to change the tail
//WC: theta(numberOfElements+1) when the position is not the last one because we need to go through all the elements up to that position and after reaching the position we need to go to the end of the list for changing all the indexes
//=>total complexity: O(numberOfElements+1)


void IndexedList::removeBetween(int start, int end)
{
    if (start >= end+1)
        throw exception();
    if (start < 0) {
        throw exception();
     if (end > head->index)
            throw exception();
    }
    int poz = 0;
    Pnode startNode = this->head;
    while (poz < start) {
        startNode = startNode->next;
        poz++;
    }

    Pnode endNode = startNode->next;
    poz++;
    while (poz < end) {                           //0 1 2 3 4 5 6 7
                                                 //start=1
                                                 //end=4     stergem end-start-1=2
        endNode = endNode->next;
        poz++;
     }

    //if (end == this->numberOfElements) {  //we have to change the tail
    //    startNode->next = nullptr;
    //    this->tail = startNode;
    //    this->numberOfElements = this->tail->index;
    //}
    //else {
        startNode->next = endNode;
        endNode->prev = startNode;
        this->numberOfElements = this->numberOfElements - (end - start - 1);

        int newIndex = startNode->index + 1;                  //we have to change all indexes from pos end to the end of the list
        endNode->index = newIndex++;
        endNode = endNode->next;
        while (endNode != nullptr) {
            endNode->index = newIndex++;
            endNode = endNode->next;



        //}
    }

}
//BC: theta(1) if we just need to remove the element previous to the tail
//WC: theta(numberOfElements+1) when the position is not the last one because we need to go through all the elements up to the start position and after reaching the positions we need to go to the end of the list for changing all the indexes
//=>total complexity: O(numberOfElements+1)

int IndexedList::search(TElem e) const{
    
    Pnode curr = this->head;
    while ( curr != nullptr)
    {
        if (curr->info == e)
            return curr->index;
        curr = curr->next;
    }

	return -1;
}
//BC: theta(1) when the element is the head


ListIterator IndexedList::iterator() const {
    return ListIterator(*this);        
}
//theta(1)
//WC: theta(numberOfElements+1) when the element is on the last position in the list => total complexity: O(numberOfElements+1)



IndexedList::~IndexedList() {
    while (head != nullptr) {
        Pnode p = head;
        head = head->next;
        delete p;
    }
}
//BC: theta(1) when the list is empty or has just the head
//WC: theta(numberOfElements+1) when the listt has more nodes => total complexity: O(numberOfElements+1)

