using System;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class UserViewModel
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

        public DepartmentViewModel Department { set; get; }

        public HospitalViewModel Hospital { set; get; }
    }
}
