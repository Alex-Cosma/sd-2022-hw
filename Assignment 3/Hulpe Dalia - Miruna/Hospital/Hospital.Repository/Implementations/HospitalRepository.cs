using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Repository.Implementations
{
    public class HospitalRepository : EfCoreRepository<HospitalEntity, ApplicationDbContext>, IHospitalRepository
    {
        private readonly DbSet<HospitalEntity> _dbSet;

        private readonly ApplicationDbContext _context;

        public HospitalRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<HospitalEntity>();

            _context = context;
        }

        public async Task<HospitalEntity> FindByIdAsync(Guid id)
        {
            var hospital = await _dbSet.Where(b => b.Id.Equals(id)).FirstOrDefaultAsync();

            return hospital;
        }

        public HospitalEntity Update(HospitalEntity entity)
        {
            var local = _dbSet
                .Local
                .FirstOrDefault(h => h.Id.Equals(entity.Id));

            if (local != null)
            {
                _context.Entry(local).State = EntityState.Detached;
            }

            _context.Entry(entity).State = EntityState.Modified;

            _context.SaveChanges();

            return entity;
        }

        public async Task<HospitalEntity> FindFullAsync(Guid id)
        {
            var entity =  await _dbSet
                .Include(h=> h.DepartmentHospital)
                .Include(h => h.Doctors)
                .Where(h=> h.Id.Equals(id))
                .FirstOrDefaultAsync();

            return entity;
        }

        public async Task<HospitalEntity> DeleteWithContraintsAsync(Guid id)
        {
            var entity = await _dbSet
                .Include(h => h.DepartmentHospital)
                .Include(h => h.Doctors)
                .Where(h => h.Id.Equals(id))
                .FirstOrDefaultAsync();

            if (entity == null)
            {
                return entity;
            }

            _context.Set<HospitalEntity>().Remove(entity);
            await SaveChangesAsync();

            return entity;
        }
    }
}
