using Hospital.Repository.Data;
using Hospital.Repository.Entitys;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.Repository.Interfaces
{
    public interface IHospitalRepository : IRepository<HospitalEntity>
    {
        Task<HospitalEntity> FindByIdAsync(Guid id);

        HospitalEntity Update(HospitalEntity entity);

        Task<HospitalEntity> FindFullAsync(Guid id);
    }
}
