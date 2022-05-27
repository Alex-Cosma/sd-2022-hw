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
    public class DoctorController : Controller
    {
        public readonly UserManager<UserEntity> _userManager;
        public readonly IDepartmentLogic _departmentLogic;
        public readonly IHospitalLogic _hospitalLogic;
        public readonly IAppointmentLogic _appointmentLogic;
        public readonly IUserLogic _userLogic;
        public readonly IMapper _mapper;

        public DoctorController(UserManager<UserEntity> userManager, IDepartmentLogic departmentLogic, IHospitalLogic hospitalLogic, IMapper mapper, IAppointmentLogic appointmentLogic, IUserLogic userLogic)
        {
            _userManager = userManager;
            _departmentLogic = departmentLogic;
            _hospitalLogic = hospitalLogic;
            _appointmentLogic = appointmentLogic;
            _mapper = mapper;
            _userLogic = userLogic;
        }

        [HttpGet]
        public async Task<IActionResult> EditDepartment()
        {
            var departmentModels = await _departmentLogic.GetAllAsync();        
            var departments = departmentModels.Select(d => _mapper.Map<DepartmentViewModel>(d)).ToList();

            var doctor = await _userLogic.FindByNameAsync(User.Identity.Name);

            var doctorViewModel = new DoctorViewModel()
            {
                Id = doctor.Id,
                Email = doctor.Email,
                Department = _mapper.Map<DepartmentViewModel>(doctor.Department),
                Departments = departments
            };

            return View(doctorViewModel);
        }

        [HttpGet]
        public async Task<IActionResult> ChangeDepartment(Guid departmentId, Guid doctorId)
        {
            await _userLogic.ChangeDepartmentAsync(departmentId, doctorId);

            return RedirectToAction(nameof(EditDepartment));
        }

        [HttpGet]
        public async Task<IActionResult> AddFeedback(Guid id)
        {
            var appointmentModel = await _appointmentLogic.FindByIdAsync(id);

            var appointmentViewModel = _mapper.Map<AppointmentViewModel>(appointmentModel);

            return View(appointmentViewModel);
        }

        [HttpPost]
        public IActionResult AddFeedback(AppointmentViewModel appointmentViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(appointmentViewModel);
            }

            var appointmentModel = _mapper.Map<AppointmentModel>(appointmentViewModel);

            _appointmentLogic.UpdateAppointment(appointmentModel);

            return RedirectToAction("Doctor", "Accounts");
        }
    }
}
