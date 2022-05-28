using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Controllers;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Tests.ControllersTests
{
    [TestClass]
    public class DepartmentControllerTests 
    {
        public readonly DepartmentController _departmentController;

        public DepartmentControllerTests(DepartmentController departmentController)
        {
            _departmentController = departmentController;
        }

        [TestMethod]
        public async Task AddDepartmentGetTest()
        {
           
        }

        [TestMethod]
        public async Task AddDepartmentPostTest()
        {
           
        }

        [TestMethod]
        public async Task UpdateDepartmentGetTest()
        {
           
        }

        [TestMethod]
        public void UpdateDepartmentPostTest()
        {
          
        }

        [TestMethod]
        public async Task DeleteDepartmentGetTest()
        {
           
        }
    }
}
