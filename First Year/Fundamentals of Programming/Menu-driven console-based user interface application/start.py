from src.domain.book import BookValidator
from src.domain.client import ClientValidator
from src.domain.rental import RentalValidator
from src.repository.bookRepo import BookRepo
from src.repository.clientRepo import ClientRepo
from src.repository.rentalRepo import RentalRepo
from src.services import bookService, clientService, rentalService
from src.services.bookService import BookService
from src.services.clientService import ClientService
from src.services.rentalService import RentalService
from src.ui import ui
from src.ui.ui import UI

bookRepo=BookRepo()
bookValidator=BookValidator()
clientRepo=ClientRepo()
clientValidator=ClientValidator()
rentalRepo=RentalRepo()
rentalValidator=RentalValidator()
serviceBook=BookService(bookRepo, bookValidator)
serviceClient=ClientService(clientRepo, clientValidator)
serviceRental=RentalService(rentalRepo, bookRepo, clientRepo, rentalValidator, )
serviceBook.generate_books(10)
serviceClient.generate_clients(10)

myUI=UI(serviceBook, serviceClient, serviceRental)
myUI.menu()
