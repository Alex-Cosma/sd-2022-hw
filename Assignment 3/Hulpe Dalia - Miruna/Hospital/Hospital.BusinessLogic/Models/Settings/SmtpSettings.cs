namespace Hospital.BusinessLogic.Models.Settings
{
    public class SmtpSettings
    {
        public string Host { get; set; }
        public bool EnableSsl { get; set; }
        public bool UseDefaultCredentials { get; set; }
        public int Port { get; set; }
    }
}
