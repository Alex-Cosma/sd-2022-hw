using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.BusinessLogicTest.UnitTests
{
    [TestClass]
    public class DepartmentLogicTests
    {
        public DepartmentLogicTests()
        {
        }

        [TestMethod]
        public async Task AddNewDepartmentAsyncTest()
        {
            var departmentLogicMock = new Mock<IDepartmentLogic>();
        }

        [TestMethod]
        public async Task GetAllAsyncTest()
        {
            var departmentLogicMock = new Mock<IDepartmentLogic>();
        }

        [TestMethod]
        public async Task FindByIdAsyncTest()
        {
            var departmentLogicMock = new Mock<IDepartmentLogic>();
        }

        [TestMethod]
        public void UpdateDepartmentTest()
        {
            var departmentLogicMock = new Mock<IDepartmentLogic>();
        }

        [TestMethod]
        public async Task DeleteDepartmentAsyncTest()
        {
            var departmentLogicMock = new Mock<IDepartmentLogic>();
        }
    }
}
