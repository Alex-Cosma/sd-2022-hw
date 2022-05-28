using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.IntegrationTests
{
    [TestClass]
    public class UserLogicTests
    {
        public readonly IUserLogic _userLogic;

        private HospitalModel hospitalModel;
        private UserModel userPacientModel;
        private UserModel userDoctorModel;
        private DepartmentModel departmentModel;
        private DepartmentModel departmentTwoModel;
        private AppointmentModel appointmentModel;
        private DepartmentHospitalModel departmentHospitalModel;

        public UserLogicTests(IUserLogic userLogic)
        {
            _userLogic = userLogic;
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

            departmentModel = new DepartmentModel()
            {
                Id = new Guid(),
                Name = "DepartmentTwoName"
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
        public async Task FindByNameAsyncTest()
        {
            var result = await _userLogic.FindByNameAsync(userDoctorModel.FirstName);

            Assert.AreEqual(result.Id, userDoctorModel.Id);
        }

        [TestMethod]
        public async Task ChangeDepartmentAsyncTest()
        {
            await _userLogic.ChangeDepartmentAsync(departmentTwoModel.Id, userDoctorModel.Id);

            var result = await _userLogic.FindByNameAsync(userDoctorModel.FirstName);

            Assert.AreEqual(result.Department.Id, departmentTwoModel.Id);
        }

        [TestMethod]
        public async Task GetAllFullAsyncTest()
        {
            var result = await _userLogic.GetAllFullAsync();

            Assert.IsTrue(result.Count() == 2);
        }
    }
}
