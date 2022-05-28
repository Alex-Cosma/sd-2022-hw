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
    public class DoctorControllerTests
    {
        public readonly DoctorController _doctorController;

        public DoctorControllerTests(DoctorController doctorController)
        {
            _doctorController = doctorController;
        }

        [TestMethod]
        public async Task EditDepartmentGetTest()
        {
           
        }

        [TestMethod]
        public async Task ChangeDepartmentGetTest()
        {
           
        }

        [TestMethod]
        public async Task AddFeedbackGetTest()
        {
           
        }

        [TestMethod]
        public void AddFeedbackPostTest()
        {
           
        }
    }
}
