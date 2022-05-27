using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.IntegrationTests
{
    [TestClass]
    public class HospitalLogicTests
    {
        public readonly IHospitalLogic _hospitalLogic;

        private HospitalModel hospitalModel;
        private UserModel userPacientModel;
        private UserModel userDoctorModel;
        private DepartmentModel departmentModel;
        private AppointmentModel appointmentModel;
        private DepartmentHospitalModel departmentHospitalModel;

        public HospitalLogicTests(IHospitalLogic hospitalLogic)
        {
            _hospitalLogic = hospitalLogic;
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
        public async Task AddNewHospitalAsyncTest ()
        {
            await _hospitalLogic.AddNewHospitalAsync(hospitalModel);

            var result = _hospitalLogic.FindByIdAsync(hospitalModel.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _hospitalLogic.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            await _hospitalLogic.AddNewHospitalAsync(hospitalModel);

            var result = await _hospitalLogic.FindByIdAsync(hospitalModel.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public void UpdateHospitalTest()
        {
            hospitalModel.Address = "New Address";
            _hospitalLogic.UpdateHospital(hospitalModel);

            Assert.AreEqual(hospitalModel.Address, "New Address");
        }

        [TestMethod]
        public async Task FindFullAsyncTest()
        {
            await _hospitalLogic.AddNewHospitalAsync(hospitalModel);

            var result = await _hospitalLogic.FindFullAsync(hospitalModel.Id);

            Assert.AreEqual(result.Doctors.ElementAt(0).FirstName, userDoctorModel.FirstName);
        }

        [TestMethod]
        public async Task AddDoctorsAsyncTest()
        {
            await _hospitalLogic.AddDoctorsAsync(userPacientModel.Id, hospitalModel.Id);

            var result = await _hospitalLogic.FindFullAsync(hospitalModel.Id);

            Assert.IsTrue(result.Doctors.Count() == 2);
        }

        [TestMethod]
        public async Task DeleteHospitalAsyncTest()
        {
            await _hospitalLogic.DeleteHospitalAsync(hospitalModel.Id);

            var result = await _hospitalLogic.FindFullAsync(hospitalModel.Id);

            Assert.IsNull(result);
        }
    }
}
