using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class DoctorViewModel
    {
        public Guid Id { get; set; }

        [Required(ErrorMessage = "Email required")]
        [EmailAddress(ErrorMessage = "Invalid Email Address")]
        public string Email { get; set; }

        [Display(Name = "User name")]
        [Required(ErrorMessage = "User name required")]
        public string UserName { get; set; }

        [Required(ErrorMessage = "Password required")]
        public string Password { get; set; }

        public List<DepartmentViewModel> Departments { get; set; }

        public DepartmentViewModel Department { get; set; }

        public List<AppointmentViewModel> Appointments { get; set; }
    }
}
