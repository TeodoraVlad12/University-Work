
using System.Globalization;
using System.Text.RegularExpressions;


namespace Laboratory2
{
    internal class Validator
    {
        public static bool IsValidEmail(string inputEmail)
        {
            if (string.IsNullOrWhiteSpace(inputEmail))
                return false;

            
            string patternEmail = @"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$";

            
            return Regex.IsMatch(inputEmail, patternEmail);
        }

        
        public static bool IsValidPhoneNumber(string inputPhoneNumber)
        {
            if (string.IsNullOrWhiteSpace(inputPhoneNumber))
                return false;

            
            string digitsOnlyPhoneNumber = Regex.Replace(inputPhoneNumber, @"[^\d\+]", "");

            
            return Regex.IsMatch(digitsOnlyPhoneNumber, @"^\+?[0-9]{7,15}$");

        }


        //yyyy.mm.dd
        //separators: . - /
        //leading 0 is only one digit!
        public static bool IsValidDate(string inputDate)
        {
            if (string.IsNullOrWhiteSpace(inputDate))
                return false;

            string pattern = @"^(?:(?:19|20)\d{2})[-\/\.](?:0[1-9]|1[0-2])[-\/\.](?:0[1-9]|[12]\d|3[01])$";

            if (!Regex.IsMatch(inputDate, pattern))
                return false;

            // Remove separators before parsing
            inputDate = inputDate.Replace(".", "-").Replace("/", "-");

            DateTime parsedDate;
            if (!DateTime.TryParseExact(inputDate, "yyyy-MM-dd", CultureInfo.InvariantCulture, DateTimeStyles.None, out parsedDate))
                return false;

            // Check if parsed date matches the input date
            return parsedDate.ToString("yyyy-MM-dd") == inputDate;
        }





        public static string SanitizeInput(string input)
        {
            if (string.IsNullOrWhiteSpace(input))
                return input;

            
            return Regex.Replace(input, @"[-;""'()|{}<>]", "");
        }
    }
}




//^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$
