#include "validator.h"

ValidationException::ValidationException( std::string &_message): message(_message) {}

const char* ValidationException::what() const noexcept {
	return message.c_str();
}

TrenchValidator::TrenchValidator() = default;


bool TrenchValidator::validateString(const std::string& input)
{
    for (char i : input)
        if (isdigit(i) != false)
            return false;
    return true;
}

void TrenchValidator::validateSizeString(const std::string& size)
{
    std::string errors;
    if (size.empty())
        errors += std::string("The size is empty!");
    if (size.find_first_not_of("0123456789") != std::string::npos)
        errors += std::string("The size has non-digit characters!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validateSize(int size)
{
    std::string errors;
    if (size < 0)
        errors += std::string("Size cannot be negative!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validateColour(const std::string& colour)
{
    std::string errors;
    if (!validateString(colour))
        errors += std::string("The colour contains digits!");
    if (colour.length() == 0)
        errors += std::string("The colour is empty!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validatePriceString(const std::string& price)
{
    std::string errors;
    if (price.empty())
        errors += std::string("The price is empty!");
    if (price.find_first_not_of("0123456789") != std::string::npos)
        errors += std::string("The price has non-digit characters!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validatePrice(int price)
{
    std::string errors;
    if (price < 0)
        errors += std::string("Price cannot be negative!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validateQuantityString(const std::string& quantity)
{
    std::string errors;
    if (quantity.empty())
        errors += std::string("The quantity is empty!");
    if (quantity.find_first_not_of("0123456789") != std::string::npos)
        errors += std::string("The quantity has non-digit characters!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validateQuantity(int quantity)
{
    std::string errors;
    if (quantity < 0)
        errors += std::string("Quantity cannot be negative!");
    if (!errors.empty())
        throw ValidationException(errors);
}

void TrenchValidator::validatePhotoLink(const std::string& photograph)
{
    std::string errors;
    if (photograph.length() == 0)
        errors += std::string("The link input is empty!");
    if (photograph.find("www") == std::string::npos)
        errors += std::string("The link is not a valid one!");
    if (!errors.empty())
        throw ValidationException(errors);
}

TrenchValidator::~TrenchValidator() = default;
