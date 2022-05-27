using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Interfaces;
using Hospital.Controllers;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;

namespace Hospital.Tests.ControllersTests
{
    [TestClass]
    public class AccountsControllerTests
    {
        public readonly AccountsController _accountsController;

        public AccountsControllerTests(AccountsController accountsController)
        {
            _accountsController = accountsController;
        }

        [TestMethod]
        public async Task AdminGetTest()
        {
            //var controller = new Mock<AccountsController>();

            //IHttpActionResult actionResult = controller.AdminGetTest();

            //Assert.IsInstanceOfType(actionResult, typeof(OkResult));
        }

        [TestMethod]
        public async Task DeleteUserGetTest()
        {
            
        }

        [TestMethod]
        public async Task UpdateUserGetTest()
        {
           
        }

        [TestMethod]
        public async Task UpdateUserPostTest()
        {
            
        }

        [TestMethod]
        public void AddUserGetTest()
        {
           
        }

        [TestMethod]
        public async Task AddUserPostTest()
        {
           
        }

        [TestMethod]
        public async Task DoctorGetTest()
        {
            
        }

        [TestMethod]
        public async Task PacientGetTest()
        {
            
        }
    }
}
