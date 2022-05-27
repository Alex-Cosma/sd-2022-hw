using System;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.BusinessLogic.Models.Settings;
using Microsoft.Extensions.Logging;

namespace Hospital.BusinessLogic.Implementations
{
    public class EmailSenderLogic : IEmailSenderLogic
    {
        private readonly EmailSettings _emailSettings;
        private readonly ILogger<EmailSenderLogic> _logger;


        public EmailSenderLogic(EmailSettings emailSettings, ILogger<EmailSenderLogic> logger)
        {
            _emailSettings = emailSettings;
            _logger = logger;
        }

        public async Task<bool> SendEmailAsync(ContactModel contactModelClient, EmailRecipientModel emailRecipientModel = null)
        {
            try
            {
                MailMessage mailMessage = CreateMailMessage(contactModelClient, emailRecipientModel);
                SmtpClient smtp = CreateSmtpClient(contactModelClient);

                await smtp.SendMailAsync(mailMessage);
                _logger.LogInformation("An email was sent at: {time}", DateTimeOffset.UtcNow);
            }
            catch (Exception ex)
            {
                return false;
            }

            return true;
        }

        #region Private

        private MailMessage CreateMailMessage(ContactModel contactModelClient, EmailRecipientModel emailRecipientModel = null)
        {
            MailMessage mailMessage;

            if (emailRecipientModel != null)
            {
                mailMessage = new MailMessage(_emailSettings.ContactGmail, emailRecipientModel.Recipient, contactModelClient.Subject, contactModelClient.Message);

                if (emailRecipientModel.CC != null)
                {
                    mailMessage.CC.Add(emailRecipientModel.CC);
                }

                mailMessage.ReplyToList.Add(new MailAddress(_emailSettings.Recipient));
                mailMessage.IsBodyHtml = emailRecipientModel.IsBodyHtml;
            }
            else
            {
                mailMessage = new MailMessage(_emailSettings.ContactGmail, _emailSettings.Recipient, contactModelClient.Subject, contactModelClient.Message);
                mailMessage.CC.Add(_emailSettings.CC);
                mailMessage.ReplyToList.Add(new MailAddress(contactModelClient.Email));
                mailMessage.IsBodyHtml = false;
            }

            return mailMessage;
        }

        private SmtpClient CreateSmtpClient(ContactModel contactModelClient)
        {
            SmtpClient smtp = new SmtpClient();

            NetworkCredential NetworkCred = new NetworkCredential(_emailSettings.ContactGmail, _emailSettings.PasswordGmail);

            smtp.UseDefaultCredentials = _emailSettings.SMTPSettings.UseDefaultCredentials;
            smtp.Host = _emailSettings.SMTPSettings.Host;
            smtp.EnableSsl = _emailSettings.SMTPSettings.EnableSsl;
            smtp.Credentials = NetworkCred;
            smtp.Port = _emailSettings.SMTPSettings.Port;

            return smtp;
        }

        #endregion
    }
}
