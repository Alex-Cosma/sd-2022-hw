using Hospital.BusinessLogic.Models;
using Hospital.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class DepartmentEditViewModel
    {
        public Guid Id { set; get; }

        public string Name { set; get; }

        public ICollection<DepartmentHospitalViewModel> DepartmentHospital { set; get; }

        public ICollection<UserViewModel> Doctors { set; get; }
    }
}