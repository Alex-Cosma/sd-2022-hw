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
    public class AppointmentRepositoryTests
    {
        private readonly IAppointmentRepository _appointmentRepository;

        private HospitalEntity hospitalEntity;
        private UserEntity userPacientEntity;
        private UserEntity userDoctorEntity;
        private DepartmentEntiy departmentEntity;
        private AppointmentEntity appointmentEntity;
        private DepartmentHospitalEntity departmentHospitalEntity;

        public AppointmentRepositoryTests(IAppointmentRepository appointmentRepository)
        {
            _appointmentRepository = appointmentRepository;
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
        public async Task FindByIdFullAsyncTest()
        {
            await _appointmentRepository.AddAsync(appointmentEntity);

            var result = await _appointmentRepository.FindByIdFullAsync(appointmentEntity.Id);

            Assert.AreEqual(result.Doctor.FirstName, userDoctorEntity.FirstName);
        }

        [TestMethod]
        public async Task UpdateTest()
        {
            appointmentEntity.DateTime = DateTime.Now;

            _appointmentRepository.Update(appointmentEntity);

            var result = await _appointmentRepository.FindByIdFullAsync(appointmentEntity.Id);

            Assert.AreEqual(result.DateTime, appointmentEntity.DateTime);
        }

        [TestMethod]
        public async Task GetAllFullAsyncTest()
        {
            var result = await _appointmentRepository.GetAllFullAsync();

            Assert.IsTrue(result.Count() == 1);
        }

        [TestMethod]
        public async Task DeleteAsyncTest()
        {
            await _appointmentRepository.DeleteAsync(appointmentEntity.Id);

            var result = await _appointmentRepository.FindByIdFullAsync(appointmentEntity.Id);

            Assert.IsNull(result);
        }

        [TestMethod]
        public async Task AddAsyncTest()
        {
            await _appointmentRepository.AddAsync(appointmentEntity);

            var result = await _appointmentRepository.FindByIdFullAsync(appointmentEntity.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAsyncTest(Guid id)
        {
            var result = await _appointmentRepository.GetAsync(appointmentEntity.Id);

            Assert.AreEqual(result.DateTime, appointmentEntity.DateTime);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _appointmentRepository.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }
    }
}
