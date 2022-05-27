using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.BusinessLogic.Implementations
{
    public class UserLogic : IUserLogic
    {
        public readonly IMapper _mapper;
        public readonly IUserRepository _userRepository;
        public readonly IDepartmentRepository _departmentRepository;

        public UserLogic( IMapper mapper, IUserRepository userRepository, IDepartmentRepository departmentRepository)
        {
            _mapper = mapper;
            _userRepository = userRepository;
            _departmentRepository = departmentRepository;
        }

        public async Task<UserModel> FindByNameAsync(string name)
        {
            var userEntity = await _userRepository.FindByNameAsync(name);

            var userModel = _mapper.Map<UserModel>(userEntity);

            return userModel;
        }

        public async Task ChangeDepartmentAsync(Guid departmentId, Guid doctorId)
        {
            var doctorEntity = await _userRepository.FindByIdAsync(doctorId);

            var departmentEntity = await _departmentRepository.FindByIdAsync(departmentId);

            doctorEntity.Department = departmentEntity;
            doctorEntity.DepartmentId = departmentEntity.Id;

            _userRepository.Update(doctorEntity);
        }

        public async Task<List<UserModel>> GetAllFullAsync()
        {
            var usersEntity = await _userRepository.GetAllFullAsync();

            var users = usersEntity.Select(u => _mapper.Map<UserModel>(u)).ToList();

            return users;
        }
    }
}
