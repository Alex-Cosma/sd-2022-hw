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
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Hospital.Repository.Implementations
{
    [TestClass]
    public class UserRepositoryTests
    {
        private readonly IUserRepository _userRepository;

        private HospitalEntity hospitalEntity;
        private UserEntity userPacientEntity;
        private UserEntity userDoctorEntity;
        private DepartmentEntiy departmentEntity;
        private AppointmentEntity appointmentEntity;
        private DepartmentHospitalEntity departmentHospitalEntity;

        public UserRepositoryTests(IUserRepository userRepository)
        {
            _userRepository = userRepository;
            InitializeEntity();
        }

        private void InitializeEntity()
        {
            hospitalEntity = new HospitalEntity()
            {
                Id = new Guid(),
                Address = "Address",
                Name = "NameHospital"
            };

            userPacientEntity = new UserEntity()
            {
                Id = new Guid(),
                FirstName = "Name Pacient"
            };

            userDoctorEntity = new UserEntity()
            {
                Id = new Guid(),
                FirstName = "Name Doctor"
            };

            departmentEntity = new DepartmentEntiy()
            {
                Id = new Guid(),
                Name = "DepartmentName"
            };

            departmentHospitalEntity = new DepartmentHospitalEntity()
            {
                DepartmentId = departmentEntity.Id,
                HospitalId = hospitalEntity.Id
            };

            hospitalEntity.Doctors = new List<UserEntity>()
            {
                userDoctorEntity
            };

            hospitalEntity.DepartmentHospital = new List<DepartmentHospitalEntity>()
            {
                departmentHospitalEntity
            };

            userDoctorEntity.Department = departmentEntity;
            userDoctorEntity.Hospital = hospitalEntity;

            departmentEntity.Doctors = new List<UserEntity>()
            {
                userDoctorEntity
            };

            departmentEntity.DepartmentHospital = new List<DepartmentHospitalEntity>()
            {
                departmentHospitalEntity
            };

            appointmentEntity = new AppointmentEntity()
            {
                Id = new Guid(),
                DateTime = DateTime.Now,
                Client = userPacientEntity,
                Doctor = userDoctorEntity,
                Hospital = hospitalEntity
            };
        }

        [TestMethod]
        public async Task findByIdAsyncTest()
        {
            await _userRepository.AddAsync(userPacientEntity);
            await _userRepository.AddAsync(userDoctorEntity);

            var result = await _userRepository.FindByIdAsync(userDoctorEntity.Id);

            Assert.AreEqual(result.FirstName, userDoctorEntity.FirstName);
        }
        [TestMethod]
        public async Task FindByNameAsync()
        {
            var result = await _userRepository.FindByNameAsync(userDoctorEntity.FirstName);

            Assert.AreEqual(result.Id, userDoctorEntity.Id);
        }

        [TestMethod]
        public async Task UpdateTest()
        {
            userDoctorEntity.FirstName = "NameDoctor2";

            _userRepository.Update(userDoctorEntity);

            var result = await _userRepository.FindByIdAsync(userDoctorEntity.Id);

            Assert.AreEqual(result.FirstName, "NameDoctor2");
        }

        [TestMethod]
        public async Task DeleteAsyncTest()
        {
            await _userRepository.DeleteAsync(userDoctorEntity.Id);

            var result = await _userRepository.GetAllAsync();

            Assert.IsTrue(result.Count == 1);
        }

        [TestMethod]
        public async Task AddAsyncTest()
        {
            await _userRepository.AddAsync(userDoctorEntity);

            var result = await _userRepository.FindByIdAsync(userDoctorEntity.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAsyncTest()
        {
            var result = await _userRepository.GetAsync(userDoctorEntity.Id);

            Assert.AreEqual(result.FirstName, userDoctorEntity.FirstName);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _userRepository.GetAllAsync();

            Assert.IsTrue(result.Count() == 2);
        }
    }
}
