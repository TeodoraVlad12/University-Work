#include "dynamicArray.h"
#include <stdlib.h>
#include <assert.h>

DynamicArray* createDynamicArray(int capacity, destroyer destroyFunction)
{
	DynamicArray* array = malloc(sizeof(DynamicArray));
	array->length = 0;
	array->capacity = capacity;
	array->elements = malloc(sizeof(TElem) * array->capacity);
	array->destroyFunction = destroyFunction;
	return array;
}

void destroyDynamicArray(DynamicArray* arr)
{
	int i;
	for (i = 0; i < arr->length; i++) {
		//destroyEstate(arr->elements[i]);
		arr->destroyFunction(arr->elements[i]);
	}

	free(arr->elements);
	free(arr);

}

int getLength(DynamicArray* arr)
{
	return arr->length;
}

TElem* get(DynamicArray* arr, int pos)
{
	return arr->elements[pos];
}

void resize(DynamicArray* arr) 
{
	arr->capacity *= 2;
	TElem* aux = malloc(arr->capacity * sizeof(TElem));
	for (int i = 0; i < arr->length; i++) {
		aux[i] = arr->elements[i];
	}

	free(arr->elements);
	arr->elements = aux;

}

void add(DynamicArray* arr, TElem e)
{
	if (arr->capacity == arr->length) {
		resize(arr);
	}
	arr->elements[arr->length++] = e;
}

void removeElement(DynamicArray* arr,int poz)
{
	int i;
	free(arr->elements[poz]);
	for (i = poz; i < arr->length; i++) {
		arr->elements[i] = arr->elements[i + 1];
	}
	arr->length--;

}

void testDynamicArray() {
	DynamicArray* arr = createDynamicArray(2, destroyEstate);
	assert(arr->length == 0);
	assert(arr->capacity == 2);

	Estate* e1 = createEstate("house", "mioritei/20", 200, 10000);
	Estate* e2 = createEstate("apartment", "mioritei/21", 80, 5000);

	add(arr, e1);
	add(arr, e2);
	assert(arr->length == 2);
	assert(arr->capacity == 2);

	Estate* e3 = createEstate("penthouse", "mioritei/22", 800, 100000);
	add(arr, e3);
	assert(arr->length == 3);
	assert(arr->capacity == 4);

	assert(getPrice(get(arr, 0)) == 10000);

	destroyDynamicArray(arr);

}
