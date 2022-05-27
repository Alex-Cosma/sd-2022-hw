using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using PdfSharp.Drawing;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.UnitTests
{
    [TestClass]
    public class AppointmentLogicTests
    {
        public AppointmentLogicTests()
        {

        }

        [TestMethod]
        public async Task AddNewAppointmentAsyncTest()
        {
            //Task.Run(async () =>
            //{
            //    var appointmentMock = new Mock<AppointmentModel>();
            //    var appointmentLogicMock = new Mock<IAppointmentLogic>();

            //    await appointmentLogicMock.Setup(p => p.AddNewAppointmentAsync(appointmentMock.Object)).Returns(async () =>
            //    {
            //        await Task.Yield()
            //    });

            //}).GetAwaiter().GetResult();
            
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var appointmentLogicMock = new Mock<IAppointmentLogic>();
        }

        [TestMethod]
        public async Task DeleteAppointmentTest()
        {
            var appointmentLogicMock = new Mock<IAppointmentLogic>();
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            var appointmentLogicMock = new Mock<IAppointmentLogic>();
        }

        [TestMethod]
        public void UpdateAppointmentTest()
        {
            var appointmentLogicMock = new Mock<IAppointmentLogic>();
        }
    }
}
