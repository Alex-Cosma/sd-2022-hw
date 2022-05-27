using Hospital.Data;
using Hospital.Repository.Data.EFCore;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.EntityFrameworkCore;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Repository.Implementations
{
    [TestClass]
    public class DepartmentRepositoryTests
    {
        private readonly IDepartmentRepository _departmentRepository;

        private HospitalEntity hospitalEntity;
        private UserEntity userPacientEntity;
        private UserEntity userDoctorEntity;
        private DepartmentEntiy departmentEntity;
        private AppointmentEntity appointmentEntity;
        private DepartmentHospitalEntity departmentHospitalEntity;

        public DepartmentRepositoryTests(IDepartmentRepository departmentRepository)
        {
            _departmentRepository = departmentRepository;
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
            await _departmentRepository.AddAsync(departmentEntity);

            var result = await _departmentRepository.FindByIdAsync(departmentEntity.Id);

            Assert.AreEqual(result.Name, departmentEntity.Name);
        }

        [TestMethod]
        public async Task UpdateTest()
        {
            departmentEntity.Name = "NameDepartment2";

            _departmentRepository.Update(departmentEntity);

            var result = await _departmentRepository.FindByIdAsync(departmentEntity.Id);

            Assert.AreEqual(result.Name, departmentEntity.Name);
        }

        [TestMethod]
        public async Task DeleteAsyncTest()
        {
            await _departmentRepository.DeleteAsync(departmentEntity.Id);

            var result = await _departmentRepository.FindByIdAsync(departmentEntity.Id);

            Assert.IsNull(result);
        }

        [TestMethod]
        public async Task AddAsyncTest()
        {
            await _departmentRepository.AddAsync(departmentEntity);

            var result = await _departmentRepository.FindByIdAsync(departmentEntity.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAsyncTest(Guid id)
        {
            var result = await _departmentRepository.GetAsync(departmentEntity.Id);

            Assert.AreEqual(result.Name, departmentEntity.Name);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _departmentRepository.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }
    }
}
