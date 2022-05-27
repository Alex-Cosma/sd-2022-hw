using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class HospitalEditViewModel
    {
        public Guid Id { set; get; }

        [Required]
        public string Address { set; get; }

        [Required]
        public string Name { set; get; }

        [Display( Name = "All departments")]
        public List<DepartmentViewModel> AllDepartments { get; set; }

        [Display(Name = "Containing departments")]
        public List<DepartmentViewModel> ContainingDepartments { get; set; }

        [Display(Name = "All doctors")]
        public List<UserViewModel> AllDoctors { get; set; }

        [Display(Name = "Containing doctors")]
        public List<UserViewModel> ContainingDoctors { get; set; }
    }
}
