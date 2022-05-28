using AutoMapper;
using Hospital.BusinessLogic.Constants;
using Hospital.BusinessLogic.Implementations;
using Hospital.BusinessLogic.Interfaces;
using Hospital.BusinessLogic.Models;
using Hospital.Models;
using Hospital.Repository.Entitys;
using Hospital.Repository.Interfaces;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Hospital.Controllers
{
    public class PacientController : Controller
    {
        public readonly UserManager<UserEntity> _userManager;
        public readonly IDepartmentLogic _departmentLogic;
        public readonly IHospitalLogic _hospitalLogic;
        public readonly IAppointmentLogic _appointmentLogic;
        public readonly IUserLogic _userLogic;
        public readonly IMapper _mapper;
        private readonly IEmailSenderLogic _emailSender;

        public PacientController(UserManager<UserEntity> userManager, IDepartmentLogic departmentLogic, IHospitalLogic hospitalLogic, IMapper mapper, IAppointmentLogic appointmentLogic, IUserLogic userLogic, IEmailSenderLogic emailSender)
        {
            _userManager = userManager;
            _departmentLogic = departmentLogic;
            _hospitalLogic = hospitalLogic;
            _mapper = mapper;
            _appointmentLogic = appointmentLogic;
            _userLogic = userLogic;
            _emailSender = emailSender;
        }

        [HttpGet]
        public async Task<IActionResult> AddAppointment()
        {
            var allUsersModels = await _userLogic.GetAllFullAsync();
            var allUsers = allUsersModels.Select(u => _mapper.Map<UserViewModel>(u)).ToList();
            var allDoctors = new List<UserViewModel>();

            foreach (var user in allUsers)
            {
                var userIdentity = await _userManager.FindByIdAsync(user.Id.ToString());
                var role = await _userManager.GetRolesAsync(userIdentity);

                if (role.ElementAt(0) == RoleConstants.Doctor)
                {
                    var hospital =await _hospitalLogic.FindByIdAsync(user.Hospital.Id);
                    var department = await _departmentLogic.FindByIdAsync(user.Department.Id);

                    var newDoctor = new UserViewModel()
                    {
                        Id = user.Id,
                        Email = user.Email,
                        Department = _mapper.Map<DepartmentViewModel>(department),
                        Hospital = _mapper.Map<HospitalViewModel>(hospital)

                    };

                    allDoctors.Add(newDoctor);
                }
            }

            var appointment = new AppointmentAddViewModel();

            appointment.Doctors = new SelectList(allDoctors, "Id", "Email");
            appointment.DateTime = DateTime.Now;
            appointment.Details = allDoctors;

            return View(appointment);
        }

        [HttpPost]
        public async Task<IActionResult> AddAppointment(AppointmentAddViewModel appointment)
        {
            var currentUser = await _userLogic.FindByNameAsync(User.Identity.Name);
            var appointmentViewModel = new AppointmentViewModel()
            {
                DoctorId = appointment.Doctor.Id,
                Client = _mapper.Map<UserViewModel>(currentUser),
                DateTime = appointment.DateTime
            };

            var appointmentModel = _mapper.Map<AppointmentModel>(appointmentViewModel);

            await _appointmentLogic.AddNewAppointmentAsync(appointmentModel);

            var contactModel = new ContactModel()
            {
                Subject = "New appointment",
                Email = currentUser.Email,
                Message = ""
            };

            var emailRecipientModel = new EmailRecipientModel()
            {
                Recipient = currentUser.Email
            };

            contactModel.ComposeEmail($"You have a new appointment on {appointmentModel.DateTime.Date}.");

            bool isEmailSent = await _emailSender.SendEmailAsync(contactModel, emailRecipientModel);

            return RedirectToAction("Pacient","Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> DeleteAppointment(Guid id)
        {
            await _appointmentLogic.DeleteAppointment(id);

            return RedirectToAction("Pacient", "Accounts");
        }

        [HttpGet]
        public ActionResult DownloadFeedback(string text, DateTime dateTime)
        {
            _appointmentLogic.CreatePdf("wwwroot/pdfs/Feedback.pdf",text);

            string filePath = "~/pdfs/Feedback.pdf";
            Response.Headers.Add("Content-Disposition", "inline; filename=Feedback.pdf");
            return File(filePath, "application/pdf");
        }
    }
}
