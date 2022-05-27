using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Interfaces;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Controllers
{
    public class AccountsController : Controller
    {
        public readonly UserManager<UserEntity> _userManager;
        public readonly IDepartmentLogic _departmentLogic;
        public readonly IHospitalLogic _hospitalLogic;
        public readonly IAppointmentLogic _appointmentLogic;
        public readonly IMapper _mapper;

        public AccountsController(UserManager<UserEntity> userManager, IDepartmentLogic departmentLogic, IHospitalLogic hospitalLogic, IMapper mapper, IAppointmentLogic appointmentLogic)
        {
            _userManager = userManager;
            _departmentLogic = departmentLogic;
            _hospitalLogic = hospitalLogic;
            _mapper = mapper;
            _appointmentLogic = appointmentLogic;
        }

        [HttpGet]
        public async Task<IActionResult> Admin()
        {
            var users = _userManager.Users.ToList();

            var currentUserMail = User.Identity.Name;

            var currentUser = await _userManager.FindByEmailAsync(currentUserMail);
            var hospitalModels = await _hospitalLogic.GetAllAsync();
            var departmentModels = await _departmentLogic.GetAllAsync();

            users.Remove(currentUser);

            var adminViewModel = new AdminViewModel();
            adminViewModel.Users = users;
            adminViewModel.Departments = departmentModels.Select(d => _mapper.Map<DepartmentViewModel>(d)).ToList();
            adminViewModel.Hospitals = hospitalModels.Select(h => _mapper.Map<HospitalViewModel>(h)).ToList();

            return View(adminViewModel);
        }

        [HttpGet]
        public async Task<IActionResult> DeleteUser(string email)
        {
            var user = await _userManager.FindByEmailAsync(email);
            await _userManager.DeleteAsync(user);

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public async Task<IActionResult> UpdateUser(string email)
        {
            var user = await _userManager.FindByEmailAsync(email);
            var updateUser = new UserViewModel();
            updateUser.Email = email;
            updateUser.UserName = user.UserName;

            return View(updateUser);
        }

        [HttpPost]
        public async Task<IActionResult> UpdateUser(UserViewModel updateUser)
        {
            var user = await _userManager.FindByEmailAsync(updateUser.Email);
            user.UserName = updateUser.UserName;
            await _userManager.UpdateAsync(user);

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public IActionResult AddUser()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> AddUser(UserViewModel userData)
        {
            var user = new UserEntity { UserName = userData.UserName, Email = userData.Email };
            var result = await _userManager.CreateAsync(user, userData.Password);
            await _userManager.AddToRoleAsync(user, RoleConstants.Doctor);

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public async Task<IActionResult> Doctor()
        {
            var appointments = await _appointmentLogic.GetAllAsync();

            var doctorViewModel = new DoctorViewModel()
            {
                Appointments = appointments.Select(d => _mapper.Map<AppointmentViewModel>(d)).Where(a=> a.Doctor.Email.Equals(User.Identity.Name)).ToList()
            };

            return View(doctorViewModel);
        }

        [HttpGet]
        public async Task<IActionResult> Pacient()
        {
            var appointments = await _appointmentLogic.GetAllAsync();

            var pacientViewModel = new PacientViewModel()
            {
                Appointments = appointments.Select(d => _mapper.Map<AppointmentViewModel>(d)).ToList()
            };

            return View(pacientViewModel);
        }
    }
}
