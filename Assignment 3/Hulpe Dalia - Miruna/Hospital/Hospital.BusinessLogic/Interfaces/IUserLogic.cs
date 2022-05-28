using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IUserLogic
    {
        Task<UserModel> FindByNameAsync(string Name);

        Task ChangeDepartmentAsync(Guid departmentId, Guid doctorId);

        Task<List<UserModel>> GetAllFullAsync();
    }
}
