using Hospital.BusinessLogic.Models;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IEmailSenderLogic
    {
        Task<bool> SendEmailAsync(ContactModel contactModelClient, EmailRecipientModel emailRecipientModel = null);
    }
}
