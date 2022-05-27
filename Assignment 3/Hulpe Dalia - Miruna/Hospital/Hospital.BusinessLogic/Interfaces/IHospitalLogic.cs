using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IHospitalLogic
    {
        Task AddNewHospitalAsync(HospitalModel hospital);

        Task<List<HospitalModel>> GetAllAsync();

        Task<HospitalModel> FindByIdAsync(Guid id);

        void UpdateHospital(HospitalModel newHospitalModel);

        Task<HospitalModel> FindFullAsync(Guid id);

        Task AddDoctorsAsync(Guid doctorId, Guid hospitalId);

        Task DeleteHospitalAsync(Guid id);
    }
}
