using System;
using System.Collections.Generic;

namespace Hospital.BusinessLogic.Models
{
    public class HospitalModel
    {
        public Guid Id { set; get; }

        public string Address { set; get; }

        public string Name { set; get; }

        public IEnumerable<DepartmentModel> Departments { set; get; }

        public ICollection<DepartmentHospitalModel> DepartmentHospital { set; get; }

        public IEnumerable<UserModel> Doctors { set; get; }
    }
}
