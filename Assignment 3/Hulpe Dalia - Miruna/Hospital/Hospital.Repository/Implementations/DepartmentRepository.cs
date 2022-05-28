using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Repository.Implementations
{
    public class DepartmentRepository : EfCoreRepository<DepartmentEntiy, ApplicationDbContext>, IDepartmentRepository
    {
        private readonly DbSet<DepartmentEntiy> _dbSet;

        private readonly ApplicationDbContext _context;

        public DepartmentRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<DepartmentEntiy>();

            _context = context;
        }

        public async Task<DepartmentEntiy> FindByIdAsync(Guid id)
        {
            var department = await _dbSet.Where(d => d.Id.Equals(id)).FirstOrDefaultAsync();

            return department;
        }

        public DepartmentEntiy Update(DepartmentEntiy entity)
        {
            var local = _dbSet
                .Local
                .FirstOrDefault(d => d.Id.Equals(entity.Id));

            if (local != null)
            {
                _context.Entry(local).State = EntityState.Detached;
            }

            _context.Entry(entity).State = EntityState.Modified;

            _context.SaveChanges();

            return entity;
        }
    }
}
