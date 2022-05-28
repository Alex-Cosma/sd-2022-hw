using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.UnitTests
{
    [TestClass]
    public class HospitalLogicTests
    {
        public HospitalLogicTests()
        {

        }

        [TestMethod]
        public async Task AddNewHospitalAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public void UpdateHospitalTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public async Task FindFullAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public async Task AddDoctorsAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }

        [TestMethod]
        public async Task DeleteHospitalAsyncTest()
        {
            var hosppitalLogicMock = new Mock<IHospitalLogic>();
        }
    }
}
