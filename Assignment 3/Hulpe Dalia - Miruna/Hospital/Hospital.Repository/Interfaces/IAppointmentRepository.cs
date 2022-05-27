using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Hospital.Repository.Data;
using Hospital.Repository.Entitys;

namespace Hospital.Repository.Interfaces
{
    public interface IAppointmentRepository : IRepository<AppointmentEntity>
    {
        Task<AppointmentEntity> FindByClientIdAsync(Guid id);
        
        Task<AppointmentEntity> FindByDoctorIdAsync(Guid id);

        AppointmentEntity Update(AppointmentEntity entity);

        Task<List<AppointmentEntity>> GetAllFullAsync();

        Task<AppointmentEntity> FindByIdFullAsync(Guid id);
    }
}
