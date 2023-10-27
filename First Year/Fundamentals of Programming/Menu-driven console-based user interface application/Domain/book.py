from src.domain.validatorException import ValidatorException


class Book:
    def __init__(self, id, title, author,status):
        self._id = id
        self._title = title
        self._author = author
        self._status = status


    @property
    def status(self):
        return self._status


    #Get:
    @property
    def id(self):
        return self._id

    @property
    def title(self):
        return self._title

    @property
    def author(self):
        return self._author

    @id.setter
    def id(self, new_value):
        self._id=new_value

    @title.setter
    def title(self,new_title):
        self._title=new_title

    @author.setter
    def author(self,new_author):
        self._author=new_author

    @status.setter
    def status(self, new_status):
        self._status= new_status

    def __str__(self):
        return "Id: " + str(self._id) + ", Title: " + self._title + ", Author: " + self._author + ", Status: " + self._status

    def __repr__(self):
        return str(self)



class BookValidator:
    def validate(self, book):
        if isinstance(book, Book) is False:
            raise TypeError("Not a Book")
        _errors=[]
        if int(book.id)<0:
            _errors.append("Id is not valid")
        if len(book.title)<3:
            _errors.append("Ttile is not valid")
        if len(book.author)<3:
            _errors.append("Author is not valid")

        if len(_errors)!=0:
            raise ValidatorException(_errors)
