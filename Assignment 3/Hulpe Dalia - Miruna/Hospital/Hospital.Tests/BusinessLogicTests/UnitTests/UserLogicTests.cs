using AutoMapper;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
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
    public class UserLogicTests
    {
        public UserLogicTests()
        {
        }

        [TestMethod]
        public async Task FindByNameAsyncTest()
        {
            var userLogicMock = new Mock<IUserLogic>();
        }

        [TestMethod]
        public async Task ChangeDepartmentAsyncTest()
        {
            var userLogicMock = new Mock<IUserLogic>();
        }

        [TestMethod]
        public async Task GetAllFullAsyncTest()
        {
            var userLogicMock = new Mock<IUserLogic>();
        }
    }
}
