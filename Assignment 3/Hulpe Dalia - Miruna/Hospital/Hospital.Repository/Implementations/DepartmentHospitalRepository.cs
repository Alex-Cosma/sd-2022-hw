using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace Hospital.Repository.Implementations
{
    public class DepartmentHospitalRepository : EfCoreRepository<DepartmentHospitalEntity, ApplicationDbContext>, IDepartmentHospitalRepository
    {
        private readonly DbSet<DepartmentHospitalEntity> _dbSet;

        public DepartmentHospitalRepository(ApplicationDbContext context): base(context)
        {
            _dbSet = context.Set<DepartmentHospitalEntity>();
        }
    }
}
