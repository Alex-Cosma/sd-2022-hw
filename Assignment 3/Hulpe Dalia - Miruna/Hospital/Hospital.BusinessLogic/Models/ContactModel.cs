namespace Hospital.BusinessLogic.Models
{
    public class ContactModel
    {
        public string Subject { get; set; }
        public string Email { get; set; }
        public string Message { get; set; }

        public void ComposeEmail (string message)
        {
            Message = $"{message} \n {Message}";
        }
    }
}
