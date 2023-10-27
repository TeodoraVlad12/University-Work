import random
import timeit


def bubble_sort (list):
    #Sorts the numbers from the smallest to the greatest using the bubble method.
    #Input: list - integers, step - integer
    #Output: the sorted list

    print("\n")
    nr = 0
    sortat=False
    while sortat == False:
        sortat = True
        for i in range(0,len(list)-1):
            if list[i] > list[i+1]: #if the element from the left is greater than the one from the right, they must swap
                aux = list[i]
                list[i] = list[i+1]
                list [i+1] = aux
                sortat = False
    #print("The final list: " + str(list))
    #print("\n")
    """
    Best case: the list is already sorted so the complexity is O(n), n being the number of comparisons equal to the number of elements-1.
    Worst case: the list is reverse sorted, complexity is O(n^2), the total number of swaps = the total number of comparisons = (n*(n+1))/2 because:
    - at pass 1 the number of comparisons = n-1
    - at pass 2 the number of comparisons = n-2
    .
    .
    - at pass 1 the number of comparisons = 1
    """
    


def shell_sort(list):
    # Sorts the numbers from the smallest to the greatest using the shell sort.
    # Input: list - integers, step - integer
    # Output: the sorted list

    print("\n")
    n = len(list)
    gap = n // 2  #we choose the biggest possible gap
    while gap > 0:
        for i in range  (gap, n):
            aux = list[i]
            j = i
            while j >= gap and list[j-gap] > aux: #if the element from the left is greater than the one from the right, between them having a numebr=gap elements, they must swap
                list[j] = list[j - gap]
                j -= gap
            list[j] = aux
        gap //= 2  #the gap should get smaller and smaller until it is equal to zero
    #print("The final list: " + str(list))
    #print("\n")
    """
    Best case: the list is already sorted and the total count of comparisons of each interval is equal to the length of the list
                complexity is O(n*logn)
    Worst case: every comparison is followed by a swap, complexity is O(n^2)
    """


def generate_numbers(n):                #Average case
    #Generates a list of n random numbers between 0 and 100
    #Input: n - integer
    #Output: the generated list - integers

    list=[]
    for i in range (0,n):
        list.append(random.randint(0,100))
    return list
    #print("Your list: " + str(list))
    #print("\n")
    #return list


def generate_nearly_sorted_list(nr):   #Best case
    # Generates a list of nr random numbers, nearly sorted in increasing order
    # Input: nr - integer
    # Output: the generated list - integers

    n = random.randint(100, 200)
    list = []
    list.append(n)
    for i in range(nr - 1):
        list.append(list[i] + random.randint(1, 5))
    aux=list[1]
    list[1]=list[nr-2]
    list[nr-2]=aux
    #print(list)
    return list



def generate_decreasing_list(nr):      #Worst case
    # Generates a list of nr random numbers sorted in decreasing order
    # Input: nr - integer
    # Output: the generated list - integers

    n = random.randint(1000, 2000)
    list = []
    list.append(n)
    for i in range(nr - 1):
        list.append(list[i] - random.randint(1, 5))
    # print (list)
    return list


def time():
    #Times the two sorting algorithms for the same list
    #Input: list of random numbers
    #The necessary time for bubble sort and for shell sort

    TEST_CODE = '''
list=generate_numbers(100)
bubble_sort(list) '''

    times_bubble = timeit.timeit(globals=globals(),
                          stmt=TEST_CODE,
                          number=1)
    #print (times_bubble)
    #print(tabulate([[times, 24],tablefmt='orgtbl')

    test_CODE = '''
list=generate_numbers(100)
shell_sort(list) '''

    times_shell = timeit.timeit(globals=globals(),
                          stmt=test_CODE,
                          number=1)
    print (times_shell,'  ', times_bubble)



def main():
    generate_nearly_sorted_list(10)
    list= []

    while True:
        print("1. Generate numbers")
        print("2. Sort the list using bubble sort")
        print("3. Sort the list using shell sort")
        print("4.Show best case time")
        print("5.Show average case time")
        print("6.Show worst case time")
        print("7. Exit program")

        option = input("Your option: ")

        if option == "1":
            n=int(input("How many numbers? "))
            list=generate_numbers(n)


        elif option == "2":
            step = int(input("Enter step: "))
            bubble_sort(list, step)

        elif option == "3":
            step = int(input("Enter step: "))
            shell_sort(list, step)


        elif option == "4":
                 nr=4
                 for i in range (0,5):
                     generate_nearly_sorted_list(nr)
                     time()
                     nr *= 2



        elif option == "5":
            nr = 4
            for i in range(0, 5):
                generate_numbers(nr)
                time()
                nr *= 2


        elif option == "6":
            nr = 4
            for i in range(0, 5):
                generate_decreasing_list(nr)
                time()
                nr *= 2


        elif option == "7":
            print("Goodbye")
            break

        else:
            print("Bad option")


main ()
