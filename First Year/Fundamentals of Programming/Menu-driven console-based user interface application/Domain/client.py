from src.domain.validatorException import ValidatorException


class Client:
    def __init__(self, id, name):
        self._id = id
        self._name = name

    @property
    def id(self):
        return self._id

    @property
    def name(self):
        return self._name

    @id.setter
    def id(self, new_id):
        self._id=new_id

    @name.setter
    def name(self, new_name):
        self._name=new_name

    def __str__(self):
        return "Id= " + str(self._id) + ", Name= " + str(self._name)

    def __repr__(self):
        return str(self)


class ClientValidator:
    def validate(self, client):
        if isinstance(client, Client) is False:
            raise TypeError("Not a Client")
        _errors=[]
        if int(client.id)<0:
            _errors.append("Id is not valid")
        if len(client.name)<3:
            _errors.append("Name is not valid")

        if len(_errors)>0:
            raise ValidatorException(_errors)
