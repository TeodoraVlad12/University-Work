import re

def IsIdentifier(elem):
    return re.match(r'^[a-z]([a-zA-Z]|[0-9])*$', elem) is not None


def IsConstant(elem):
    return re.match(r'^(-?[1-9]+[0-9]*|0)$', elem) is not None

class Scanner:

    def __init__(self):
        self.ReservedWords = []
        self.Separators = []
        self.Operators = []

    def GetReservedWords(self):
        return self.ReservedWords

    def GetSeparators(self):
        return self.Separators

    def GetOperators(self):
        return self.Operators

    def GetAllTokens(self):
        return self.ReservedWords + self.Separators + self.Operators

    def ReadTokens(self):
        with open('token.in', 'r', encoding='utf-8-sig') as file:
            # Assuming the first 17 lines are operators
            for i in range(17):
                self.Operators.append(file.readline().strip())
            # Next 12 lines are separators
            for i in range(12):
                separator = file.readline().strip()
                if separator == 'space':
                    self.Separators.append(" ")
                else:
                    self.Separators.append(separator)
            # Remaining lines are reserved words
            for line in file:
                self.ReservedWords.append(line.strip())

    def IsOperator(self, elem):
        return elem in self.Operators

    def IsSeparator(self, elem):
        return elem in self.Separators

    def GetTokensFromLine(self, line):
        token = ''
        tokens = []
        i = 0

        while i < len(line):
            char = line[i]

            if self.IsOperator(char) or self.IsSeparator(char):
                if token:
                    if token in self.ReservedWords:
                        tokens.append(token)
                    else:
                        tokens.append(token)
                    token = ''


                op = char
                while i + 1 < len(line) and self.IsOperator(op + line[i + 1]):
                    i += 1
                    op += line[i]
                tokens.append(op) if self.IsOperator(op) else tokens.append(char)
                i += 1
                continue

            token += char
            i += 1

        if token:
            if token in self.ReservedWords:
                tokens.append(token)
            else:
                tokens.append(token)

        return tokens