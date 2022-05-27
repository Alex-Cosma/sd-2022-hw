using System;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Interfaces
{
    public interface IDepartmentHospitalLogic
    {
        Task AddNewDepartmentHospitalAsync(Guid departmentId, Guid hospitalId);
    }
}
