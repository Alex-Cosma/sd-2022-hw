using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace Hospital.Repository.Implementations
{
    public class UserRepository : EfCoreRepository<UserEntity, ApplicationDbContext>, IUserRepository
    {
        private readonly DbSet<UserEntity> _dbSet;

        private readonly ApplicationDbContext _context;

        public UserRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<UserEntity>();
            _context = context;
        }

        public async Task<UserEntity> FindByIdAsync(Guid id)
        {
            var user = await _dbSet
                .Include(p => p.Department)
                .Where(u => u.Id.Equals(id)).FirstOrDefaultAsync();

            return user;
        }

        public async Task<UserEntity> FindByNameAsync(string name)
        {
            var user = await _dbSet
                .Include(p => p.Department)
                .Where(u => u.UserName.Equals(name)).FirstOrDefaultAsync();

            return user;
        }

        public UserEntity Update(UserEntity entity)
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

        public async Task<List<UserEntity>> GetAllFullAsync()
        {
            return await _context.Set<UserEntity>()
                .Include(u=>u.Hospital)
                .Include(u=>u.Department)
                .ToListAsync();
        }


        public async Task<UserEntity> FindFullByIdAsync(Guid? id)
        {
            var user = await _dbSet
                .Include(p => p.Department)
                .Include(u=> u.Hospital)
                .Where(u => u.Id.Equals(id)).FirstOrDefaultAsync();

            return user;
        }
    }
}
