namespace Hospital.BusinessLogic.Models.Settings
{
    public class EmailSettings
    {
        public string ContactGmail { get; set; }
        public string PasswordGmail { get; set; }
        public string Recipient { get; set; }
        public string CC { get; set; }
        public SmtpSettings SMTPSettings { get; set; }
    }
}
