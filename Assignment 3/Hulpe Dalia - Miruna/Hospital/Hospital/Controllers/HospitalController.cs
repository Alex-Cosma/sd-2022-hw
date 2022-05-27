using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Controllers
{
    public class HospitalController : Controller
    {
        public readonly UserManager<UserEntity> _userManager;
        public readonly IMapper _mapper;
        private readonly IHospitalLogic _hospitalLogic;
        private readonly IDepartmentHospitalLogic _departmenthospitalLogic;
        private readonly IDepartmentLogic _departmentLogic;

        public HospitalController(UserManager<UserEntity> userManager, IMapper mapper, IHospitalLogic hospitalLogic, IDepartmentHospitalLogic departmenthospitalLogic, IDepartmentLogic departmentLogic)
        {
            _userManager = userManager;
            _mapper = mapper;
            _hospitalLogic = hospitalLogic;
            _departmenthospitalLogic = departmenthospitalLogic;
            _departmentLogic = departmentLogic;
        }

        [HttpGet]
        public async Task<IActionResult> AddHospital()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> AddHospital(HospitalViewModel hospitalViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(hospitalViewModel);
            }

            var hospitalModel = _mapper.Map<HospitalModel>(hospitalViewModel);

            await _hospitalLogic.AddNewHospitalAsync(hospitalModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> UpdateHospital(Guid id)
        {
            var hospitalEditViewModel = new HospitalEditViewModel();

            var hospitalModel = await _hospitalLogic.FindFullAsync(id);

            hospitalEditViewModel.Id = hospitalModel.Id;
            hospitalEditViewModel.Name = hospitalModel.Name;
            hospitalEditViewModel.Address = hospitalModel.Address;

            var allDepartments = await _departmentLogic.GetAllAsync();
            var allUsers = _userManager.Users.ToList();
            var allDoctors = new List<UserViewModel>();

            foreach (var user in allUsers)
            {
                var userIdentity = await _userManager.FindByIdAsync(user.Id.ToString());
                var role = await _userManager.GetRolesAsync(userIdentity);

                if (role.ElementAt(0) == RoleConstants.Doctor)
                {
                    var newDoctor = new UserViewModel()
                    {
                        Id = user.Id,
                        Email = user.Email
                    };

                    allDoctors.Add(newDoctor);
                }
            }

            hospitalEditViewModel.AllDepartments = allDepartments.Select(d => _mapper.Map<DepartmentViewModel>(d)).ToList();
            hospitalEditViewModel.ContainingDepartments = hospitalModel.Departments.Select(d => _mapper.Map<DepartmentViewModel>(d)).ToList();

            hospitalEditViewModel.AllDoctors = allDoctors.Select(d => _mapper.Map<UserViewModel>(d)).ToList();
            hospitalEditViewModel.ContainingDoctors = hospitalModel.Doctors.Select(d => _mapper.Map<UserViewModel>(d)).ToList();

            if (hospitalEditViewModel.AllDepartments == null)
            {
                hospitalEditViewModel.AllDepartments = new List<DepartmentViewModel>();
            }

            if (hospitalEditViewModel.ContainingDepartments == null)
            {
                hospitalEditViewModel.ContainingDepartments = new List<DepartmentViewModel>();
            }

            if (hospitalEditViewModel.AllDoctors == null)
            {
                hospitalEditViewModel.AllDoctors = new List<UserViewModel>();
            }

            foreach (DepartmentViewModel department in hospitalEditViewModel.ContainingDepartments)
            {
                if (hospitalEditViewModel.AllDepartments.Where(d=> d.Id == department.Id).Count() != 0)
                {
                    var itemToRemove = hospitalEditViewModel.AllDepartments.SingleOrDefault(r => r.Id == department.Id);
                    hospitalEditViewModel.AllDepartments.Remove(itemToRemove);
                }
            }

            foreach (var doctor in hospitalEditViewModel.ContainingDoctors)
            {
                if (hospitalEditViewModel.AllDoctors.Where(d => d.Id == doctor.Id).Count() != 0)
                {
                    var itemToRemove = hospitalEditViewModel.AllDoctors.SingleOrDefault(r => r.Id == doctor.Id);
                    hospitalEditViewModel.AllDoctors.Remove(itemToRemove);
                }
            }

            return View(hospitalEditViewModel);
        }

        [HttpPost]
        public IActionResult UpdateHospital(HospitalEditViewModel hospitalEditViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(hospitalEditViewModel);
            }

            var hospitalModel = new HospitalModel()
            {
                Id = hospitalEditViewModel.Id,
                Name = hospitalEditViewModel.Name,
                Address = hospitalEditViewModel.Address
            };

            _hospitalLogic.UpdateHospital(hospitalModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> AddHospitalDepartments(Guid idD, Guid idH)
        {
            await _departmenthospitalLogic.AddNewDepartmentHospitalAsync(idD, idH);

            return RedirectToAction(nameof(UpdateHospital), new { id = idH });
        }

        [HttpGet]
        public async Task<IActionResult> AddDoctors(Guid idD, Guid idH)
        {
            await _hospitalLogic.AddDoctorsAsync(idD, idH);

            return RedirectToAction(nameof(UpdateHospital), new { id = idH });
        }

        [HttpGet]
        public async Task<IActionResult> DeleteHospital(Guid id)
        {
            await _hospitalLogic.DeleteHospitalAsync(id);

            return RedirectToAction("Admin", "Accounts");
        }
    }
}
