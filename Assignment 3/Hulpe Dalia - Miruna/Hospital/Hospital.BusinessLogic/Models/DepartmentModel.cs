using System;
using System.Collections.Generic;

namespace Hospital.BusinessLogic.Models
{
    public class DepartmentModel
    {
        public Guid Id { set; get; }

        public string Name { set; get; }

        public ICollection<DepartmentHospitalModel> DepartmentHospital { set; get; }

        public ICollection<UserModel> Doctors { set; get; }
    }
}