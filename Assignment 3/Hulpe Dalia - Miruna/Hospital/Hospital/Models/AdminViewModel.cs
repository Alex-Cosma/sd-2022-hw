using Hospital.Repository.Entitys;
using Microsoft.AspNetCore.Identity;
using System.Collections.Generic;

namespace Hospital.Models
{
    public class AdminViewModel
    {
        public List<UserEntity> Users { get; set; }

        public List<HospitalViewModel> Hospitals { get; set; }

        public List<DepartmentViewModel> Departments { get; set; }
    }
}
