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
    public class AppointmentRepository : EfCoreRepository<AppointmentEntity, ApplicationDbContext>, IAppointmentRepository
    {
        private readonly DbSet<AppointmentEntity> _dbSet;

        private readonly ApplicationDbContext _context;

        public AppointmentRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<AppointmentEntity>();

            _context = context;
        }

        public async Task<AppointmentEntity> FindByClientIdAsync(Guid id)
        {
            var appointment = await _dbSet.Where(d => d.Client.Id.Equals(id)).FirstOrDefaultAsync();

            return appointment;
        }

        public async Task<AppointmentEntity> FindByDoctorIdAsync(Guid id)
        {
            var appointment = await _dbSet.Where(d => d.Doctor.Id.Equals(id)).FirstOrDefaultAsync();

            return appointment;
        }

        public AppointmentEntity Update(AppointmentEntity entity)
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

        public async Task<List<AppointmentEntity>> GetAllFullAsync()
        {
            return await _context.Set<AppointmentEntity>()
                .Include(a => a.Hospital)
                .Include(a => a.Doctor)
                .Include(a=> a.Client)
                .ToListAsync();
        }

        public async Task<AppointmentEntity> FindByIdFullAsync(Guid id)
        {
            var appointment = await _dbSet
                .Where(b => b.Id.Equals(id))
                .Include(a => a.Hospital)
                .Include(a => a.Doctor)
                .Include(a => a.Client)
                .FirstOrDefaultAsync();

            return appointment;
        }
    }
}
