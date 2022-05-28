using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IAppointmentLogic
    {
        Task AddNewAppointmentAsync(AppointmentModel appointment);

        Task<List<AppointmentModel>> GetAllAsync();

        Task DeleteAppointment(Guid id);

        Task<AppointmentModel> FindByIdAsync(Guid id);
      
        void UpdateAppointment(AppointmentModel newHospitalModel);

        void CreatePdf(string pdfName, string feedback);
    }
}
