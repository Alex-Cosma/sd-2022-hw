using Hospital.Repository.Data;
using Hospital.Repository.Entitys;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Hospital.Repository.Interfaces
{
    public interface IUserRepository : IRepository<UserEntity>
    {
        Task<UserEntity> FindByIdAsync(Guid id);

        Task<UserEntity> FindFullByIdAsync(Guid? id);

        Task<UserEntity> FindByNameAsync(string name);

        UserEntity Update(UserEntity entity);

        Task<List<UserEntity>> GetAllFullAsync();
    }
}
