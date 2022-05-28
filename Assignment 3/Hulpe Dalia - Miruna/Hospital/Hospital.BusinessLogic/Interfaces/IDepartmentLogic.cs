using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IDepartmentLogic
    {
        Task AddNewDepartmentAsync(DepartmentModel department);

        Task<List<DepartmentModel>> GetAllAsync();

        Task<DepartmentModel> FindByIdAsync(Guid id);

        void UpdateDepartment(DepartmentModel newDepartmentModel);

        Task DeleteDepartmentAsync(Guid id);
    }
}
