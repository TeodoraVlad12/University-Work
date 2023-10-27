from datetime import date
import datetime
from src.domain.validatorException import ValidatorException


class Rental:
    def __init__(self, rental_id, returned_date):
        self._rental_id=rental_id
        self._returned_date=returned_date

    @property
    def rental_id(self):
        return self._rental_id

    @property
    def returned_date(self):
        return self._returned_date

    def __len__(self):
        return (self._returned_date-_rented_date).days + 1

    def __repr__(self):
        return str(self)

    def __str__(self):
        return "Rental: " + str(self._rental_id) + "\nBook: " + str(self._book_id) + "\nClient:  " + str(self._client_id) + "\nPeriod: " + str(self._rented_date) + "to " + str(self._returned_date)


class RentalValidator:
    def validate(self, rental):
        if isinstance(rental, Rental) is False:
            raise TypeError("Not a Rental")
        _errors=[]
        mydate=datetime.datetime(2015,1,1)
        if rental.rented_date < mydate:
            _errors.append("Rental starts in past")
        #if len(rental)<1:
            #_errors.append("Rental must pe at least 1 day")


        if len(_errors)>0:
            raise ValidatorException(_errors)
