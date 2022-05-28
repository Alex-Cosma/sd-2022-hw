using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Repository.Implementations
{
    [TestClass]
    public class HospitalRepositoryTests
    {
        private readonly IHospitalRepository _hospitalRepository;

        private HospitalEntity hospitalEntity;
        private UserEntity userPacientEntity;
        private UserEntity userDoctorEntity;
        private DepartmentEntiy departmentEntity;
        private AppointmentEntity appointmentEntity;
        private DepartmentHospitalEntity departmentHospitalEntity;

        public HospitalRepositoryTests(IHospitalRepository hospitalRepository)
        {
            _hospitalRepository = hospitalRepository;
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
            await _hospitalRepository.AddAsync(hospitalEntity);

            var result = await _hospitalRepository.FindByIdAsync(hospitalEntity.Id);

            Assert.AreEqual(result.Name, hospitalEntity.Name);
        }

        [TestMethod]
        public async Task UpdateTest()
        {
            hospitalEntity.Name = "NameHospital2";

            _hospitalRepository.Update(hospitalEntity);

            var result = await _hospitalRepository.FindByIdAsync(hospitalEntity.Id);

            Assert.AreEqual(result.Name, hospitalEntity.Name);
        }

        [TestMethod]
        public async Task FindFullAsyncTest()
        {
            var result = await _hospitalRepository.FindFullAsync(hospitalEntity.Id);

            Assert.AreEqual(result.Doctors.ElementAt(0).FirstName, userDoctorEntity.FirstName);
        }

        [TestMethod]
        public async Task DeleteAsyncTest()
        {
            await _hospitalRepository.DeleteAsync(hospitalEntity.Id);

            var result = await _hospitalRepository.FindFullAsync(hospitalEntity.Id);

            Assert.IsNull(result);
        }

        [TestMethod]
        public async Task AddAsyncTest()
        {
            await _hospitalRepository.AddAsync(hospitalEntity);

            var result = await _hospitalRepository.FindFullAsync(hospitalEntity.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAsyncTest(Guid id)
        {
            var result = await _hospitalRepository.GetAsync(hospitalEntity.Id);

            Assert.AreEqual(result.Name, hospitalEntity.Name);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _hospitalRepository.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }
    }
}
