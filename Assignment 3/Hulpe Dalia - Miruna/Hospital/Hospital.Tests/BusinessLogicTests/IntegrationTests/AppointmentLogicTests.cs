using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using PdfSharp.Drawing;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.IntegrationTests
{
    [TestClass]
    public class AppointmentLogicTests
    {
        public readonly IAppointmentLogic _appointmentLogic;

        private HospitalModel hospitalModel;
        private UserModel userPacientModel;
        private UserModel userDoctorModel;
        private DepartmentModel departmentModel;
        private AppointmentModel appointmentModel;
        private DepartmentHospitalModel departmentHospitalModel;

        public AppointmentLogicTests(IAppointmentLogic appointmentLogic)
        {
            _appointmentLogic = appointmentLogic;
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
        public async Task AddNewAppointmentAsyncTest()
        {
            await _appointmentLogic.AddNewAppointmentAsync(appointmentModel);
            var result = await _appointmentLogic.FindByIdAsync(appointmentModel.Id);

            Assert.IsNotNull(result);
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var result = await _appointmentLogic.GetAllAsync();

            Assert.IsTrue(result.Count() == 1);
        }

        [TestMethod]
        public async Task DeleteAppointmentTest()
        {
           await _appointmentLogic.DeleteAppointment(appointmentModel.Id);
            var result = await _appointmentLogic.FindByIdAsync(appointmentModel.Id);

            Assert.IsNull(result);
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            await _appointmentLogic.AddNewAppointmentAsync(appointmentModel);
            var result = await _appointmentLogic.FindByIdAsync(appointmentModel.Id);

            Assert.IsNotNull(result);

        }

        [TestMethod]
        public void UpdateAppointmentTest()
        {
            var oldValue = appointmentModel.DateTime;

            appointmentModel.DateTime = DateTime.Now;

            _appointmentLogic.UpdateAppointment(appointmentModel);

            Assert.IsFalse(appointmentModel.DateTime == oldValue);
        }
    }
}
