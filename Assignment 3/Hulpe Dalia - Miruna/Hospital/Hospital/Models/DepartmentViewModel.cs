using Hospital.BusinessLogic.Models;
using Hospital.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class DepartmentViewModel
    {
        public Guid Id { set; get; }

        [Required]
        public string Name { set; get; }

        public ICollection<DepartmentHospitalViewModel> DepartmentHospital { set; get; }

        public ICollection<UserViewModel> Doctors { set; get; }
    }
}