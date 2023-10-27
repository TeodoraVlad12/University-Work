from src.domain.rental import Rental
from src.domain.validatorException import ValidatorException
from src.repository.repositoryException import RepositoryException
from src.services import clientService, rentalService
from src.services.bookService import BookService
from src.services.clientService import ClientService
from src.services.rentalService import RentalService
import datetime

class UI:
    def __init__(self, serviceBook: BookService, serviceClient: ClientService, serviceRental: RentalService):
        self._serviceBook=serviceBook
        self._serviceClient=serviceClient
        self._serviceRental=serviceRental

    def menu(self):
        print("1. Display all books\n"
              "2. Display all clients\n"
              "3. Add a book\n"
              "4. Add a client\n"
              "5. Remove a book by author\n"
              "6. Remove a client by name\n"
              "7. Update a book's title by author\n"
              "8. Update a client's name by name\n"
              "9. Rent a book\n"
              "10. Return a book\n"
              "11. Search for clients\n"
              "12. Search for books\n"
              "13. Show most rented books\n"
              "14. Show most active clients\n"
              "15. Most rented authors")
        while True:
            print()
            command=input("Enter command: ")
            command=command.strip()

            if command=="1":
                myBooks=self._serviceBook.get_all_books()
                for i in range(len(myBooks)):
                    print(myBooks[i])

            elif command == "2":
                myClients = self._serviceClient.get_all_clients()
                for i in range(len(myClients)):
                    print(myClients[i])

            elif command== "3":
                try:
                    id=int(input("Enter id: "))
                    title=input("Enter title: ")
                    author=input("Enter author: ")
                    status="available"
                    self._serviceBook.add_book(id, title, author,status)
                except RepositoryException as re:
                    print("Try again-> " + str(re))
                except ValidatorException as ve:
                    print("Try again-> "+ str(ve))
                except ValueError as ve:
                    print("Try again-> " + str(ve))

            elif command=="4":
                try:
                    id=int(input("Enter id: "))
                    name=input("Enter name: ")
                    self._serviceClient.add_client(id,name)
                except RepositoryException as re:
                    print("Try again-> " + str(re))
                except ValidatorException as ve:
                    print("Try again-> "+ str(ve))
                except ValueError as ve:
                    print("Try again-> " + str(ve))

            elif command=="5":
                author=input("Enter author: ")
                self._serviceBook.delete_book_by_author(author)

            elif command=="6":
                name=input("Enter name: ")
                self._serviceClient.delete_client_by_name(name)

            elif command=="7":
                title=input("Enter title: ")
                new_author=input("Enter new author: ")
                self._serviceBook.update_a_book(title, new_author)

            elif command=="8":
                name=input("Enter name: ")
                new_name=input("Enter new name: ")
                self._serviceClient.update_a_client(name, new_name)

            elif command=="9":
                try:
                    rental_id=int(input("Enter rental id: "))
                    book_id=int(input("Enter book id: "))
                    client_id=int(input("Enter client id: "))
                    year=int(input("Current year: "))
                    month=int(input("Current month: "))
                    day=int(input("Current day: "))
                    # year=int(datetime.datetime.today().year)
                    # month=int(datetime.datetime.today().month)
                    # day=int(datetime.datetime.today().day)
                    rented_date=datetime.datetime(year,month,day)
                    #returned_date=datetime.datetime.now()
                    returned_date=datetime.datetime(2222, 2, 22)
                    self._serviceRental.add(rental_id,book_id,client_id,rented_date,returned_date)
                    self._serviceBook.update_a_book_status(book_id,"rented")
                except RepositoryException as re:
                    print("Try again-> " + str(re))
                except ValidatorException as ve:
                    print("Try again-> " + str(ve))
                except ValueError as ve:
                    print("Try again-> " + str(ve))

            elif command=="10":
                try:
                    rental_id=int(input("Enter rental id: "))
                    year = int(datetime.datetime.today().year)
                    month = int(datetime.datetime.today().month)
                    day = int(datetime.datetime.today().day)
                    returned_date = datetime.datetime(year, month, day)
                    self._serviceRental.return_book(rental_id,returned_date)
                    self._serviceBook.update_a_book_status(self._serviceRental.return_book_id_by_rental_id(rental_id), "available")
                except ValueError as ve:
                    print("Try again-> " + str(ve))

            elif command=="11":
                something=input("Search for: ")
                something=something.strip()
                clients=self._serviceClient.search(something)
                if len(clients)==0:
                    print("No clients found!")
                    return
                for client in clients:
                    print(client)

            elif command=="12":
                something=input("Search for: ")
                something=something.strip()
                books=self._serviceBook.search(something)
                if len(books)==0:
                    print("No books found!")
                    return
                for book in books:
                    print(book)

            elif command=="13":
                results=self._serviceRental.most_rented_books()
                for dto in results:
                    print(dto)

            elif command=="14":
                results=self._serviceRental.most_active_clients()
                for dto in results:
                    print(dto)

            elif command=="15":
                results=self._serviceRental.most_rented_authors()
                for dto in results:
                    print(dto)






            else:
                print("Invalid command")
