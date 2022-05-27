using Hospital.Repository.Data;
using Hospital.Repository.Entitys;
using System;
using System.Threading.Tasks;

namespace Hospital.Repository.Interfaces
{
    public interface IDepartmentRepository : IRepository<DepartmentEntiy>
    {
        Task<DepartmentEntiy> FindByIdAsync(Guid id);

        DepartmentEntiy Update(DepartmentEntiy entity);
    }
}
