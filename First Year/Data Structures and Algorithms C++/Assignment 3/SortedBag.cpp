#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <assert.h>
#include<iostream>
using namespace std;


//pos = e - mini
//value = mini + pos





SortedBag::SortedBag(Relation r) {
	this->head = nullptr;
	this->tail = nullptr;
	this->capacity = 2;
	this->elems = new TElem[this->capacity];
	this->elems[0] = 0;
	this->elems[1] = 0;
	this->rel = r;
	this->maxi = NULL_TCOMP;
	this->mini = NULL_TCOMP;
	this->length = 0;
	this->unique = 0;

}
//theta(1)


void SortedBag::add(TComp e) {
	//CASE: the list is empty
	if (this->length ==0) {
		this->maxi = e;             //minimum element in the array
		this->mini = e;
		Pnode newNode = new NodSLL();
		newNode->info.first = e;
		newNode->info.second = 0;    //the position in the array
		newNode->next = nullptr;
		this->head = newNode;
		this->elems[0] = 1;
		this->unique++;
		//this->tail = newNode;
	}
	else {
		Pnode currentNode = this->head;
		bool found = false;
		while (currentNode != nullptr && !found && this->rel(currentNode->info.first, e)) {
			if (currentNode->info.first == e) {
				this->elems[currentNode->info.second]++;				
				found = true;
			}
			else
				currentNode = currentNode->next;
		}

		

		if (!found) {
			//wee need to change the head
			if (currentNode == this->head && currentNode != nullptr) {


				if (this->tail == nullptr) {    //the head becomes the tail
					
					int old_min = this->mini;
					int old_maxi = this->maxi;
					bool maxi_changed = false;
					bool mini_changed = false;
					if (e > this->maxi) {
						this->maxi = e;
						maxi_changed = true;

					}
					else
						if (e < this->mini) {
							this->mini = e;
							mini_changed = true;
						}




					Pnode newNode = new NodSLL();
					newNode->info.first = e;
					int pos_new_el = e - this->mini;

					if (maxi_changed) {

						newNode->info.second = pos_new_el;
						newNode->next = this->head;
						int new_pos_maxi = this->maxi - this->mini;

						if (new_pos_maxi < this->capacity) {
							//we don't need resizing

							this->elems[pos_new_el] = 1;
							
							
							this->tail = this->head;
							this->tail->next = nullptr;
							this->head = newNode;
							this->head->next = this->tail;
							
						
						}
						else {
							//we need resizing
							auto* newElems = new TElem[new_pos_maxi + 1];
							this->capacity = new_pos_maxi + 1;
							newElems[new_pos_maxi] = 1;
							newElems[0] = this->elems[0];

							for (int i = 1; i < new_pos_maxi; i++)
								newElems[i] = 0;
							for (int i = new_pos_maxi + 1; i < this->capacity; i++)
								newElems[i] = 0;

							this->elems = newElems;

							this->tail = this->head;
							this->tail->next = nullptr;
							this->head = newNode;
							this->head->next = this->tail;



						}

					}
					else {    //mini changed 
						newNode->info.second = 0;
						newNode->info.first = e;

						int new_pos_maxi = this->maxi - this->mini;

						if (new_pos_maxi < this->capacity) {//we don't need resizing


							this->elems[new_pos_maxi] = this->elems[0];
							this->elems[0] = 1;
							
							
							this->tail = this->head;
							this->tail->next = nullptr;
							this->head = newNode;
							this->head->next = this->tail;
							
						}
						else {//we need resizing
							
							auto* newElems = new TElem[new_pos_maxi + 1];
							this->capacity = new_pos_maxi + 1;
							newElems[new_pos_maxi] = this->elems[0];
							newElems[0] = 1;

							for (int i = 1; i < new_pos_maxi; i++)
								newElems[i] = 0;
							for (int i = new_pos_maxi + 1; i < this->capacity; i++)
								newElems[i] = 0;

							this->tail = this->head;
							this->tail->next = nullptr;
							this->head = newNode;
							this->head->next = this->tail;

							this->elems = newElems;


						}
					}







			}
				else


				{
				Pnode newNode = new NodSLL();
				newNode->info.first = e;
				//newNode->info.second = 0;


				int old_min = this->mini;
				int old_maxi = this->maxi;
				bool maxi_changed = false;
				bool mini_changed = false;
				if (e > this->maxi) {
					this->maxi = e;
					maxi_changed = true;

				}
				else
					if (e < this->mini) {
						this->mini = e;
						mini_changed = true;
					}

				newNode->info.second = e - this->mini;      //position in the array
				newNode->next = currentNode;



				int how_many_to_add = this->maxi - this->mini + 1;
				int pos_maxi = this->maxi - this->mini;

				this->head = newNode;

				if (how_many_to_add < this->capacity && pos_maxi < this->capacity) {  //we don't have to resize
					auto* newElems = new TElem[this->capacity];

					if (mini_changed) {

						int to_add = old_min - this->mini;

						int new_pos_maxi = this->maxi - this->mini;
						int new_pos_mini = old_min - this->mini;
						//int new_pos_maxi = this->maxi - e;
						newElems[0] = 1;
						for (int i = 1; i < new_pos_mini; i++)
							newElems[i] = 0;
						int index = 0;
						for (int i = new_pos_mini; i <= new_pos_maxi; i++)
						{
							newElems[i] = this->elems[index];
							index++;
						}
						for (int i = new_pos_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;
						this->elems = newElems;


						Pnode current = this->head;          //change .second (the position in the elems[])
						current->info.second = 0;
						current = current->next;
						while (current != nullptr) {
							current->info.second += to_add;
							current = current->next;
						}

					}
					else {//if maxi changed
						int pos_new_maxi = this->maxi - this->mini;
						auto* newElems = new TElem[this->capacity];
						newElems[pos_new_maxi] = 1;
						int pos_old_maxi = old_maxi - this->mini;
						for (int i = 0; i <= pos_old_maxi; i++) {
							newElems[i] = this->elems[i];
						}
						for (int i = pos_old_maxi + 1; i < pos_new_maxi; i++)                             //am adauagt +1
							newElems[i] = 0;
						for (int i = pos_new_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;

						this->elems = newElems;

					}

				}

				else {  //wee need to resize
					//cout << endl << "We are resizing!" << endl;
					auto* newElems = new TElem[this->capacity * 4];
					this->capacity *= 4;

					if (mini_changed) {
						int to_add = old_min - this->mini;

						int new_pos_mini = old_min - e;
						int new_pos_maxi = this->maxi - this->mini;
						newElems[0] = 1;
						for (int i = 1; i < new_pos_mini; i++)
							newElems[i] = 0;
						int index = 0;
						for (int i = new_pos_mini; i <= new_pos_maxi; i++)
						{
							newElems[i] = this->elems[index];
							index++;
						}
						for (int i = new_pos_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;

						this->elems = newElems;


						Pnode current = this->head;
						current->info.second = 0;
						current = current->next;     //change .second (the position in the elems[])
						while (current != nullptr) {
							current->info.second += to_add;
							current = current->next;
						}

					}
					else {//maxi changed
						int pos_new_maxi = this->maxi - this->mini;
						int pos_old_maxi = old_maxi - this->mini;
						for (int i = 0; i <= pos_old_maxi; i++) {
							newElems[i] = this->elems[i];
						}
						for (int i = pos_old_maxi + 1; i < pos_new_maxi; i++)
							newElems[i] = 0;
						newElems[pos_new_maxi] = 1;
						for (int i = pos_new_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;

						this->elems = newElems;

					}

				}

			}
		}

			else if (currentNode == nullptr) {
				//wee need to add it last

				int old_min = this->mini;
				int old_maxi = this->maxi;
				bool maxi_changed = false;
				bool mini_changed = false;
				if (e > this->maxi) {
					this->maxi = e;
					maxi_changed = true;

				}
				else
					if (e < this->mini) {
						this->mini = e;
						mini_changed = true;
					}


				if (this->tail == nullptr) {     //if it's the second in the list

					Pnode newNode = new NodSLL();
					newNode->info.first = e;
					int pos_new_el = e - this->mini;

					if (maxi_changed){

					newNode->info.second = pos_new_el;
					newNode->next = nullptr;

					if (pos_new_el < this->capacity) {
						//we don't need resizing

						this->elems[pos_new_el] = 1;
						this->head->next = newNode;
						this->tail = newNode;
					}
					else {
						//we need resizing
						auto* newElems = new TElem[pos_new_el+1];
						for (int i = 1; i < pos_new_el; i++)
							newElems[i] = 0;
						newElems[pos_new_el] = 1;
						this->elems = newElems;
					}
					
				}
					else {    //mini changed 
						newNode->info.second = pos_new_el;
						newNode->next = nullptr;

						int new_pos_maxi = this->maxi - this->mini;
						
						if (new_pos_maxi < this->capacity) {//we don't need resizing


							this->elems[new_pos_maxi] = this->elems[0];
							this->elems[0] = 1;
							Pnode newNode = new NodSLL();
							newNode->next = nullptr;
							newNode->info.first = e;
							newNode->info.second = pos_new_el;
							this->head->next = newNode;
							this->tail = newNode;
						}
						else {//we need resizing
							int new_pos_maxi = this->maxi - this->mini;
							auto* newElems = new TElem[new_pos_maxi + 1];
							this->elems[new_pos_maxi] = this->elems[0];
							this->elems[0] = 1;

							Pnode newNode = new NodSLL();
							newNode->next = nullptr;
							newNode->info.first = e;
							newNode->info.second = new_pos_maxi;
							this->head->next = newNode;
							this->tail = newNode;


						}
					}

				}

				else {//still the case: add it last

					if (maxi_changed) {

					Pnode newNode = new NodSLL();
					newNode->info.first = e;
					newNode->info.second = e - this->mini;
					newNode->next = nullptr;


					this->tail->next = newNode;
					this->tail = newNode;

					int pos_new_maxi = this->maxi - this->mini;
					int how_many_add = this->maxi - this->mini + 1;
					

					if (how_many_add < this->capacity && pos_new_maxi < this->capacity) { //we don't need to resize
						auto* newElems = new TElem[this->capacity];
						newElems[pos_new_maxi] = 1;
						int pos_old_maxi = old_maxi - this->mini;
						for (int i = 0; i <= pos_old_maxi; i++) {
							newElems[i] = this->elems[i];
						}
						for (int i = pos_old_maxi+1; i < pos_new_maxi; i++)                //am adaugat +1
							newElems[i] = 0;
						for (int i = pos_new_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;

						this->elems = newElems;


					}

					else {    //we need resizing
						auto* newElems = new TElem[this->capacity * 4];
						this->capacity *= 4;

						int pos_old_maxi = old_maxi - this->mini;
						for (int i = 0; i <= pos_old_maxi; i++) {
							newElems[i] = this->elems[i];
						}
						for (int i = pos_old_maxi + 1; i < pos_new_maxi; i++)
							newElems[i] = 0;
						newElems[pos_new_maxi] = 1;
						for (int i = pos_new_maxi + 1; i < this->capacity; i++)
							newElems[i] = 0;

						this->elems = newElems;


					}
				}
					else {//mini changed
						int to_add = old_min - this->mini;

						Pnode newNode = new NodSLL();
						newNode->info.first = e;
						newNode->info.second = e - this->mini;  //=0
						newNode->next = nullptr;


						this->tail->next = newNode;
						this->tail = newNode;

						int new_pose_old_mini = old_min - this->mini;
						int how_many_to_add = this->maxi - this->mini + 1;
						int new_pos_maxi = this->maxi - this->mini;


						if (how_many_to_add < this->capacity && new_pos_maxi < this->capacity) {  //we don't have to resize
							auto* newElems = new TElem[this->capacity];

								int new_pos_mini = old_min - e;
								//int new_pos_maxi = this->maxi - e;
								newElems[0] = 1;
								for (int i = 1; i < new_pos_mini; i++)
									newElems[i] = 0;
								int index = 0;
								for (int i = new_pos_mini; i <= new_pos_maxi; i++)
								{
									newElems[i] = this->elems[index];
									index++;
								}
								for (int i = new_pos_maxi + 1; i < this->capacity; i++)
									newElems[i] = 0;
								this->elems = newElems;

								                                                 
									Pnode current = this->head;          //change .second (the position in the elems[])
									while (current->next != nullptr) {
										current->info.second += to_add;
										current = current->next;
									}
									//current = current->next;               //*****************************************
									current->info.second = 0;
								
							

						}
						else {//wee need resizing and mini changed
							auto* newElems = new TElem[this->capacity * 4];
							this->capacity *= 4;


							int new_pos_mini = old_min - this->mini;
							int new_pos_maxi = this->maxi - this->mini;
							newElems[0] = 1;
							for (int i = 1; i < new_pos_mini; i++)
								newElems[i] = 0;
							int index = 0;
							for (int i = new_pos_mini; i <= new_pos_maxi; i++)
							{
								newElems[i] = this->elems[index];
								index++;
							}
							for (int i = new_pos_maxi + 1; i < this->capacity; i++)
								newElems[i] = 0;

							this->elems = newElems;



							Pnode current = this->head;     //change .second (the position in the elems[])
							while (current->next != nullptr) {
								current->info.second += to_add;
								current = current->next;
							}
							current->info.second = 0;

						}



					}
			}

			}
			else {
				Pnode newNode = new NodSLL();
				newNode->info.first = e;
				newNode->info.second = e - this->mini;
				Pnode nod_before_current = new NodSLL;
				nod_before_current = this->head;
				while (nod_before_current->next != currentNode)
					nod_before_current = nod_before_current->next;

				nod_before_current->next = newNode;
				newNode->next = currentNode;
				

				int pos_new_node = e - this->mini;   //add it in the elems
				this->elems[pos_new_node] = 1;
				
			}
			this->unique++;
			}
		
	}

	this->length++;

	
}
//BC: theta(1) - when the list is empty
//WC: theta(2*cpacity)- when we have to resize the array and add something at its beginning => total complexity is O(2*capacity)



bool SortedBag::remove(TComp e) {
	if (!search(e))    //if it doesn't exist
		return false;
	else {
		if (this->elems[e - this->mini] > 1)
		{
			this->elems[e - this->mini]--;

		}
		else {//occurence=1

			bool mini_removed = false;
			bool maxi_removed = false;
			if (e == this->mini)
				mini_removed = true;
			else
				if (e == this->maxi)
					maxi_removed = true;


			if (this->head->info.first == e)   //if it is the head


			{
				if (this->tail == nullptr || this->head->next==nullptr) {           //if we need to empty it
					this->head = nullptr;
					for (int i = 0; i < this->capacity; i++)
						this->elems[i] = 0;
				}
				else



					if (this->head->next == this->tail ) {       //if we remain with just one element

						
							this->elems[0] = this->elems[this->head->next->info.second];
							for (int i = 1; i < this->capacity; i++)
								this->elems[i] = 0;

							this->head = this->tail;
							this->head->next = nullptr;
							this->maxi = this->head->info.first;
							this->mini = this->head->info.first;
						

					}
					else {

						//this->head = this->head->next;
						//this->mini = this->head->info.first;

						if (mini_removed) {
							int old_mini = this->head->info.first;
							this->head = this->head->next;
							int new_mini = this->head->info.first;
							int to_sub = new_mini - old_mini;
							auto* newElems = new TElem[this->capacity];
							int index = 0;
							for (int i = this->head->info.second; i < this->capacity; i++)
							{
								newElems[index] = this->elems[i];
								index++;
							}
							for (int i = index; i < this->capacity; i++)
								newElems[i] = 0;


							//newElems[0] = this->nrOccurrences(this->head->info.first);
							this->elems = newElems;
							this->mini = this->head->info.first;

							Pnode currentNode = this->head;
							while (currentNode != nullptr) {
								currentNode->info.second -= to_sub;
								currentNode = currentNode->next;
							}

						}
						else {//maxi removed and head removed
							this->elems[this->head->info.second] = 0;
							this->head = this->head->next;
							this->maxi = this->head->info.first;

						}

					}

			

			}
			else
				if (this->tail->info.first == e)  //we need to remove the tail
				{

					if (mini_removed) {
						int old_mini = this->tail->info.first;
						Pnode findTail = this->head;

						while (findTail->next != this->tail)
							findTail = findTail->next;
						this->tail = findTail;
						this->tail->next = nullptr;

						int new_mini = this->tail->info.first;
						int to_sub = new_mini - old_mini;
						auto* newElems = new TElem[this->capacity];
						int index = 0;
						for (int i = this->tail->info.second; i < this->capacity; i++)
						{
							newElems[index] = this->elems[i];
							index++;
						}
						for (int i = index; i < this->capacity; i++)
							newElems[i] = 0;


						//newElems[0] = this->nrOccurrences(this->head->info.first);
						this->elems = newElems;
						this->mini = this->tail->info.first;

					}
					else {//tail and maxi removed
						this->elems[this->tail->info.second] = 0;
						Pnode newTail = this->head;
						while (newTail->next != this->tail)
							newTail = newTail->next;
						this->tail = newTail;
						this->tail->next = nullptr;
						this->maxi = this->tail->info.first;

					}

				}






				else {  //an element of occurence one in the middle of the list
					this->elems[e - this->mini] = 0;
					Pnode currentNode = this->head;
					while (currentNode->next->info.first != e) {
						currentNode = currentNode->next;
					}
					currentNode->next = currentNode->next->next;
						
				}

			


		}
		this->length--;
		return true;

		
	}
	
	
}
//BC: theta(1) - when we just have to empty the list
//WC: theta(capacity)- when we have to remove the minimum from the array, we have to change every element in the array and also every second element in every pair of the list
//=>total complexity is O(capacity)

bool SortedBag::search(TComp elem) const{

//cout << "Show in search" << endl;
//for (int i = 0; i < this->capacity; i++)
//	cout << this->elems[i] << endl;
//cout<< "Ens show in search" << endl;
	if (elem < mini || elem>maxi)
		return false;
	else
		if (this->elems[elem - this->mini] == 0)          //if it's not in the array
			return false;
	return true;
}
//theta(1) 


int SortedBag::nrOccurrences(TComp elem) const {
	if (!this->search(elem))
		return 0;
	else
		return this->elems[elem - this->mini];
}
//theta(1)



int SortedBag::size() const {
	/*int size = 0;
	for (int i = 0; i < this->length; i++)
		size += this->elems[i];
	return size;*/
	return this->length;
}
//theta(1)


bool SortedBag::isEmpty() const {
	/*if (this->head == nullptr)
		return true;
	else
		return false;*/
	if (this->length == 0)
		return true;
	else
		return false;

}
//theta(1)


SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}
//theta(1)


void SortedBag::show_elems() {
	cout <<endl<< "Elements are" << endl;
	for (int i = 0; i < this->capacity; i++)
		cout << this->elems[i] << " ";
	cout << endl << "End show elems"<<endl;

}
//theta(nrOfElems)

void SortedBag::show_list() {
	cout <<endl<< "List is" << endl;
	Pnode currentNode = this->head;
	while (currentNode != nullptr) {
		cout << currentNode->info.first << " ";
		currentNode = currentNode->next;
	}
	cout << endl << "End show list"<<endl;
}
//theta(nrOfUniqueElems)


TComp SortedBag::leastFrequent() const
{
	int min = -1;
	int pos = -1;
	for (int i = 0; i < this->capacity; i++)
	{
		if (min == -1 && this->elems[i] != 0)
		{
			min = this->elems[i];
			pos = i;
		}
		else
			if (this->elems[i] != 0 && this->elems[i] < min)
			{
				min = this->elems[i];
				pos = i;
			}
	}

	if (min == -1)
		return NULL_TCOMP;
	if (min != -1)
		return this->mini + pos;
}
//theta(capacity)


//bool relation(TComp r1, TComp r2) {
//	return r1 >= r2;
//}
//
//
//
//void testNewFunct() {
//	SortedBag sb(relation);
//	sb.add(5);
//	sb.add(5);
//	sb.add(6);
//	sb.add(6);
//	sb.add(7);
//	sb.add(7);
//	sb.add(8);
//	assert(sb.leastFrequent() == 8);
//
//
//}

SortedBag::~SortedBag() {
	delete[] this->elems;
}
//theta(1)
