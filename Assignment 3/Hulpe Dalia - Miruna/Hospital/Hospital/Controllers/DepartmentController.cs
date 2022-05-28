using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Controllers
{
    public class DepartmentController : Controller
    {
        public readonly UserManager<UserEntity> _userManager;
        public readonly IMapper _mapper;
        private readonly IDepartmentLogic _departmentLogic;

        public DepartmentController(UserManager<UserEntity> userManager, IMapper mapper, IDepartmentLogic departmentLogic)
        {
            _userManager = userManager;
            _mapper = mapper;
            _departmentLogic = departmentLogic;
        }

        [HttpGet]
        public async Task<IActionResult> AddDepartment()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> AddDepartment(DepartmentViewModel departmentViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(departmentViewModel);
            }

            var departmentModel = _mapper.Map<DepartmentModel>(departmentViewModel);

            await _departmentLogic.AddNewDepartmentAsync(departmentModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> UpdateDepartment(Guid id)
        {
            var departmentEditViewModel = new DepartmentEditViewModel();

            var departmentModel = await _departmentLogic.FindByIdAsync(id);

            departmentEditViewModel.Id = departmentModel.Id;
            departmentEditViewModel.Name = departmentModel.Name;

            return View(departmentEditViewModel);
        }

        [HttpPost]
        public IActionResult UpdateDepartment(DepartmentEditViewModel departmentEditViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(departmentEditViewModel);
            }

            var departmentModel = new DepartmentModel()
            {
                Id = departmentEditViewModel.Id,
                Name = departmentEditViewModel.Name
            };

            _departmentLogic.UpdateDepartment(departmentModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> DeleteDepartment(Guid id)
        {
            await _departmentLogic.DeleteDepartmentAsync(id);

            return RedirectToAction("Admin", "Accounts");
        }
    }
}
