namespace Laboratory2
{
    internal class Program
    {
        static void Main(string[] args)
        {

            
            
            string userInput = "'; DROP TABLE Users; --";

            
            //Emails
            string[] emails = {
            "test@example.com",
            "testexample.com",
            "test@examplecom",
            "test@example.com.net"
            };

            for (int i = 0; i < emails.Length; i++)
            {
                Console.WriteLine($"Email {i + 1}: {emails[i]}");

                if (Validator.IsValidEmail(emails[i]))
                    Console.WriteLine("Email is valid.");
                else
                    Console.WriteLine("Email is invalid.");

                
            }

            Console.WriteLine();

            //Phone numbers:
            string[] phoneNumbers = {
            "+1 (555) 123-4567",
            "0757317444",
            "(555) 123-4567",
            "-4567"
            };

            for (int i = 0; i < phoneNumbers.Length; i++)
            {
                Console.WriteLine($"Phone number {i + 1}: {phoneNumbers[i]}");

                if (Validator.IsValidPhoneNumber(phoneNumbers[i]))
                    Console.WriteLine("Phone number is valid.");
                else
                    Console.WriteLine("Phone number is invalid.");

                
            }

            Console.WriteLine();

            //Dates:
            string[] dates = {
            "2024-03-20",
            "2024-30-20",
            "200.03.20",
            "2024.03.20",
            "2024.03.40",
            "1999/03/05"
            };

            for (int i = 0; i < dates.Length; i++)
            {
                Console.WriteLine($"Date {i + 1}: {dates[i]}");

                if (Validator.IsValidDate(dates[i]))
                    Console.WriteLine("Date is valid.");
                else
                    Console.WriteLine("Date is invalid.");

                
            }

            Console.WriteLine();


            

            string[] unsanitizedInputs = {
            "'; DROP TABLE Users; --",
            "create table tabel1 values (1, '1');"
            
            };

            for (int i = 0; i < unsanitizedInputs.Length; i++)
            {
                Console.WriteLine("Unsanitiez input: " + unsanitizedInputs[i]);

                string sanitizedInput = Validator.SanitizeInput(unsanitizedInputs[i]);
                Console.WriteLine("Sanitized input: " + sanitizedInput);


            }
        }
    }
}
