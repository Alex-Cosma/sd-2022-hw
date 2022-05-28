using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.IntegrationTests
{
    [TestClass]
    public class DepartmentLogicTests
    {
        public readonly IDepartmentLogic _departmentLogic;

        private HospitalModel hospitalModel;
        private UserModel userPacientModel;
        private UserModel userDoctorModel;
        private DepartmentModel departmentModel;
        private AppointmentModel appointmentModel;
        private DepartmentHospitalModel departmentHospitalModel;

        public DepartmentLogicTests(IDepartmentLogic departmentLogic)
        {
            _departmentLogic = departmentLogic;
            InitializeEntity();
        }

        private void InitializeEntity()
        {
            hospitalModel = new HospitalModel()
            {
                Id = new Guid(),
                Address = "Address",
                Name = "NameHospital"
            };

            userPacientModel = new UserModel()
            {
                Id = new Guid(),
                FirstName = "Name Pacient"
            };

            userDoctorModel = new UserModel()
            {
                Id = new Guid(),
                FirstName = "Name Doctor"
            };

            departmentModel = new DepartmentModel()
            {
                Id = new Guid(),
                Name = "DepartmentName"
            };

            departmentHospitalModel = new DepartmentHospitalModel()
            {
                DepartmentId = departmentModel.Id,
                HospitalId = hospitalModel.Id
            };

            hospitalModel.Doctors = new List<UserModel>()
            {
                userDoctorModel
            };

            hospitalModel.DepartmentHospital = new List<DepartmentHospitalModel>()
            {
                departmentHospitalModel
            };

            userDoctorModel.Department = departmentModel;
            userDoctorModel.Hospital = hospitalModel;

            departmentModel.Doctors = new List<UserModel>()
            {
                userDoctorModel
            };

            departmentModel.DepartmentHospital = new List<DepartmentHospitalModel>()
            {
                departmentHospitalModel
            };

            appointmentModel = new AppointmentModel()
            {
                Id = new Guid(),
                DateTime = DateTime.Now,
                Client = userPacientModel,
                Doctor = userDoctorModel,
                Hospital = hospitalModel
            };
        }

        [TestMethod]
        public async Task AddNewDepartmentAsyncTest()
        {
            await _departmentLogic.AddNewDepartmentAsync(departmentModel);
            var result = _departmentLogic.FindByIdAsync(departmentModel.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _departmentLogic.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }

        [TestMethod]
        public void UpdateDepartmentTest()
        {
            departmentModel.Name = "NewName";

            _departmentLogic.UpdateDepartment(departmentModel);

            var result = _departmentLogic.FindByIdAsync(departmentModel.Id);

            Assert.AreEqual(departmentModel.Name, "NewName");
        }

        [TestMethod]
        public async Task DeleteDepartmentAsyncTest()
        {
            await _departmentLogic.DeleteDepartmentAsync(departmentModel.Id);

            var result = _departmentLogic.FindByIdAsync(departmentModel.Id);

            Assert.IsNull(result);
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            await _departmentLogic.AddNewDepartmentAsync(departmentModel);
            var result = _departmentLogic.FindByIdAsync(departmentModel.Id);

            Assert.IsNotNull(result);
        }
    }
}
